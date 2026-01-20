package com.railway.booking.consumer;

import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.messaging.BookingQueue;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class BookingDlqConsumer {

    private final BookingQueue bkQueue;

    public BookingDlqConsumer(BookingQueue bkQueue) {
        this.bkQueue = bkQueue;
    }

    @PostConstruct
    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    TicketBookedEvent bad =
                            bkQueue.deadLetterQueue.take();

                    System.err.println(
                        "DLQ REVIEW → Booking "
                        + bad.bookingId()
                        + " Age=" + bad.age()
                    );

                } catch (InterruptedException ignored) {}
            }
        }, "dlq-consumer").start();
    }
}
