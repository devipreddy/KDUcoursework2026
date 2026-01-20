package com.railway.booking.consumers;

import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.idempotency.ProcessedMessageStore;
import com.railway.booking.messaging.BookingQueue;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(NotificationConsumer.class);

    private final BookingQueue bookingQueue;
    private final ProcessedMessageStore processedStore;

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor(r ->
                    new Thread(r, "notification-consumer"));

    public NotificationConsumer(
            BookingQueue bookingQueue,
            ProcessedMessageStore processedStore
    ) {
        this.bookingQueue = bookingQueue;
        this.processedStore = processedStore;
    }

    @PostConstruct
    public void start() {
        executor.submit(this::consume);
    }

    private void consume() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TicketBookedEvent event =
                        bookingQueue.notificationQueue.take();

                if (processedStore.isDuplicate(event.eventId())) {
                    log.debug(
                        "Duplicate notification skipped for event {}",
                        event.eventId()
                    );
                    continue;
                }

                sendNotification(event);

                processedStore.markProcessed(event.eventId());

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                log.info("NotificationConsumer interrupted, stopping");
            } catch (Exception ex) {
                log.error("Unexpected notification processing error", ex);
            }
        }
    }

    private void sendNotification(TicketBookedEvent event) {
        log.info(
            "Notification → SMS sent for booking {}",
            event.bookingId()
        );
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down NotificationConsumer");
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
