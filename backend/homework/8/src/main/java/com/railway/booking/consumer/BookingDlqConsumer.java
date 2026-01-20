package com.railway.booking.consumer;

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
public class BookingDlqConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(BookingDlqConsumer.class);

    private final BookingQueue bookingQueue;
    private final ExecutorService executor =
            Executors.newSingleThreadExecutor(r ->
                    new Thread(r, "dlq-consumer"));

    public BookingDlqConsumer(BookingQueue bookingQueue) {
        this.bookingQueue = bookingQueue;
    }

    @PostConstruct
    public void start() {
        executor.submit(this::consumeDlq);
    }

    private void consumeDlq() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TicketBookedEvent event =
                        bookingQueue.deadLetterQueue.take();

                log.warn(
                    "DLQ REVIEW → bookingId={} age={}",
                    event.bookingId(),
                    event.age()
                );

                // Optional: route to metrics / audit store
                // dlqAnalyzer.analyze(event);

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); // restore interrupt
                log.info("DLQ consumer interrupted, shutting down");
            } catch (Exception ex) {
                log.error("Unexpected error while processing DLQ", ex);
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down DLQ consumer");
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
