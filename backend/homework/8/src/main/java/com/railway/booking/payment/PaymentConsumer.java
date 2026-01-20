package com.railway.booking.payment;

import com.railway.booking.domain.TicketBookedEvent;
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
public class PaymentConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(PaymentConsumer.class);

    private final BookingQueue bookingQueue;
    private final ProcessedTransactionStore processedStore;

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor(r ->
                    new Thread(r, "payment-consumer"));

    public PaymentConsumer(
            BookingQueue bookingQueue,
            ProcessedTransactionStore processedStore
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
            TicketBookedEvent event = null;
            try {
                event = bookingQueue.paymentQueue.take();

                if (processedStore.alreadyProcessed(event.transactionId())) {
                    log.debug(
                        "Duplicate transaction ignored: {}",
                        event.transactionId()
                    );
                    continue;
                }

                processPayment(event);

                processedStore.markProcessed(event.transactionId());

                log.info(
                    "Payment successful for transaction {}",
                    event.transactionId()
                );

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                log.info("PaymentConsumer interrupted, shutting down");
            } catch (Exception ex) {
                handleFailure(event, ex);
            }
        }
    }

    private void processPayment(TicketBookedEvent event) {
        if (event.age() < 0) {
            throw new IllegalStateException(
                    "Payment gateway rejected transaction "
                    + event.transactionId()
            );
        }
        // call external gateway here
    }

    private void handleFailure(
            TicketBookedEvent event,
            Exception ex
    ) {
        if (event == null) {
            log.error("Payment failure before event was read", ex);
            return;
        }

        log.error(
            "Payment failed for transaction {}, re-queuing",
            event.transactionId(),
            ex
        );

        bookingQueue.paymentQueue.offer(event);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down PaymentConsumer");
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
