package com.railway.booking.consumers;

import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.idempotency.ProcessedMessageStore;
import com.railway.booking.messaging.BookingQueue;
import com.railway.booking.retry.RetryHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Component
public class InventoryConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(InventoryConsumer.class);

    private final BookingQueue bookingQueue;
    private final ProcessedMessageStore processedStore;
    private final RetryHandler retryHandler;

    private final Map<String, Integer> retryCount =
            new ConcurrentHashMap<>();

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor(r ->
                    new Thread(r, "inventory-consumer"));

    public InventoryConsumer(
            BookingQueue bookingQueue,
            ProcessedMessageStore processedStore,
            RetryHandler retryHandler
    ) {
        this.bookingQueue = bookingQueue;
        this.processedStore = processedStore;
        this.retryHandler = retryHandler;
    }

    @PostConstruct
    public void start() {
        executor.submit(this::consume);
    }

    private void consume() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TicketBookedEvent event =
                        bookingQueue.inventoryQueue.take();

                validate(event);

                if (processedStore.isDuplicate(event.eventId())) {
                    log.debug("Duplicate inventory event ignored: {}",
                              event.eventId());
                    continue;
                }

                occupySeat(event);

                retryCount.remove(event.bookingId());
                processedStore.markProcessed(event.eventId());

            } catch (IllegalArgumentException ex) {
                handleFailure(ex);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                log.info("InventoryConsumer interrupted, shutting down");
            } catch (Exception ex) {
                log.error("Unexpected inventory processing error", ex);
            }
        }
    }

    private void validate(TicketBookedEvent event) {
        if (event.age() < 0) {
            throw new IllegalArgumentException(
                    "Invalid age for bookingId=" + event.bookingId());
        }
    }

    private void occupySeat(TicketBookedEvent event) {
        log.info(
            "Inventory → Seat {} occupied for booking {}",
            event.seatNumber(),
            event.bookingId()
        );
    }

    private void handleFailure(IllegalArgumentException ex) {
        TicketBookedEvent failed =
                bookingQueue.inventoryQueue.peek();

        if (failed == null) {
            log.warn("Validation failed but no event available", ex);
            return;
        }

        int attempt =
                retryCount.merge(failed.bookingId(), 1, Integer::sum);

        log.warn(
            "Inventory validation failed for booking {} (attempt {})",
            failed.bookingId(),
            attempt
        );

        retryHandler.handleFailure(
                failed,
                attempt,
                bookingQueue.inventoryQueue
        );
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down InventoryConsumer");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}
