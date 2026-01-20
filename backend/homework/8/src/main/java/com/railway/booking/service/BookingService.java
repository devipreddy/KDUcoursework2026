package com.railway.booking.service;

import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.messaging.BookingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class BookingService {

    private static final Logger log =
            LoggerFactory.getLogger(BookingService.class);

    private final BookingQueue bookingQueue;
    private final SeatService seatService;

    public BookingService(
            BookingQueue bookingQueue,
            SeatService seatService
    ) {
        this.bookingQueue = bookingQueue;
        this.seatService = seatService;
    }

    public TicketBookedEvent bookTicket(int age) {

        validate(age);

        TicketBookedEvent event = createEvent(age);

        publish(event);

        log.info(
            "Booking created with bookingId={} seat={}",
            event.bookingId(),
            event.seatNumber()
        );

        return event;
    }

    private void validate(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }

    private TicketBookedEvent createEvent(int age) {
        return new TicketBookedEvent(
                UUID.randomUUID(),              // eventId
                UUID.randomUUID().toString(),   // bookingId
                seatService.getNextSeat(),
                age,
                UUID.randomUUID().toString(),   // transactionId
                Instant.now()
        );
    }

    private void publish(TicketBookedEvent event) {
        bookingQueue.inventoryQueue.offer(event);
        bookingQueue.notificationQueue.offer(event);
        bookingQueue.paymentQueue.offer(event);
    }
}
