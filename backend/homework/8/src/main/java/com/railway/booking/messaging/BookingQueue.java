package com.railway.booking.messaging;

import com.railway.booking.domain.TicketBookedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


@Component
public class BookingQueue {

    public final BlockingQueue<TicketBookedEvent> inventoryQueue = new LinkedBlockingQueue<>();

    public final BlockingQueue<TicketBookedEvent> notificationQueue = new LinkedBlockingQueue<>();

    public final BlockingQueue<TicketBookedEvent> deadLetterQueue = new LinkedBlockingQueue<TicketBookedEvent>(); 
    

}