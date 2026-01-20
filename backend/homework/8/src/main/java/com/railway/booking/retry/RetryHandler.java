package com.railway.booking.retry;

import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.messaging.BookingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
public class RetryHandler {

    private static final Logger log =
            LoggerFactory.getLogger(RetryHandler.class);

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 500;

    private final BookingQueue bookingQueue;

    public RetryHandler(BookingQueue bookingQueue) {
        this.bookingQueue = bookingQueue;
    }

    public void handleFailure(
            TicketBookedEvent event,
            int attempt,
            BlockingQueue<TicketBookedEvent> sourceQueue
    ) {
        if (attempt < MAX_RETRIES) {
            retry(event, sourceQueue, attempt);
        } else {
            moveToDlq(event);
        }
    }

    private void retry(
            TicketBookedEvent event,
            BlockingQueue<TicketBookedEvent> sourceQueue,
            int attempt
    ) {
        try {
            Thread.sleep(RETRY_DELAY_MS);
            sourceQueue.offer(event);

            log.warn(
                "Retrying booking {} (attempt {})",
                event.bookingId(),
                attempt
            );

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.info(
                "Retry interrupted for booking {}",
                event.bookingId()
            );
        }
    }

    private void moveToDlq(TicketBookedEvent event) {
        bookingQueue.deadLetterQueue.offer(event);
        log.error(
            "Moved booking {} to DLQ after max retries",
            event.bookingId()
        );
    }
}
