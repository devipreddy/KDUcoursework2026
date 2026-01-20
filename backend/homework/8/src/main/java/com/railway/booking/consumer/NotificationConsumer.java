package com.railway.booking.consumers;

import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.idempotency.ProcessedMessageStore;
import com.railway.booking.messaging.BookingQueue;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer{

    private final BookingQueue bkQueue;
    private final ProcessedMessageStore store;

    public NotificationConsumer(BookingQueue bkQueue, ProcessedMessageStore store){

        this.bkQueue = bkQueue;
        this.store = store;
    }

    @PostConstruct
    public void start(){
        new Thread(() -> {
            while(true){
                try{
                    TicketBookedEvent ticket = bkQueue.notificationQueue.take();
                    if (store.isDuplicate(ticket.eventId())){
                        continue;
                    }

                    System.out.println("Notification -> SMS Sent for booking" + ticket.eventId());

                }
                catch(InterruptedException i){

                }

            }

        }, "notification - consumer").start();
    }

}