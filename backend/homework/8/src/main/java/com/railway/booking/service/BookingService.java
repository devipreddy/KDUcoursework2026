package com.railway.booking.service;

import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.messaging.BookingQueue;
import org.springframework.stereotype.Service;
import com.railway.booking.service.SeatService;

import java.time.Instant;
import java.util.UUID;

@Service
public class BookingService{

    private final BookingQueue bkQueue;
    private final SeatService seatService;


    public BookingService(BookingQueue bkQueue, SeatService seatService){

        this.bkQueue = bkQueue;
        this.seatService = seatService;
    }

    public TicketBookedEvent bookTicket(int age){

        TicketBookedEvent ticket = new TicketBookedEvent(

            UUID.randomUUID(),
            UUID.randomUUID().toString(),
            seatService.getNextSeat(),
            age,
            Instant.now()

        );

        bkQueue.inventoryQueue.offer(ticket);
        bkQueue.notificationQueue.offer(ticket);

        return ticket;
    }
}