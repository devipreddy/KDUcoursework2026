package com.railway.booking.consumers;

import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.idempotency.ProcessedMessageStore;
import com.railway.booking.messaging.BookingQueue;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.railway.booking.retry.RetryHandler;
import java.util.HashMap;
import java.util.Map;

@Component
public class InventoryConsumer{

    private final BookingQueue bkQueue;
    private final ProcessedMessageStore store;
    private final RetryHandler retryHandler;

    private final Map< String, Integer> retryCount = new HashMap<>();

    public InventoryConsumer(BookingQueue bkQueue, ProcessedMessageStore store, RetryHandler retryHandler){

        this.bkQueue = bkQueue;
        this.store = store;
        this.retryHandler = retryHandler;
    }

    @PostConstruct
    public void start(){
        new Thread(() -> {
            while(true){
                try{
                    TicketBookedEvent ticket = bkQueue.inventoryQueue.take();

                    if (ticket.age() < 0){
                        throw new IllegalArgumentException("Invalid Age");
                    }
                    if (store.isDuplicate(ticket.eventId())){
                        continue;
                    }

                    retryCount.remove(ticket.bookingId());

                    System.out.println("Inventory" + "Seat occupied" + ticket.seatNumber());

                }
                catch(IllegalArgumentException i){

                    TicketBookedEvent failed = null;
                    int attempt = 0;

                    if (i instanceof IllegalArgumentException){

                        failed = bkQueue.inventoryQueue.peek();
                        attempt = retryCount.getOrDefault(failed.bookingId(),0) + 1;

                        retryCount.put(failed.bookingId(), attempt);

                        retryHandler.handleFailure(failed, attempt, bkQueue.inventoryQueue);



                    }

                } catch(InterruptedException ex){}

            }

        }, "inventory - consumer").start();
    }

}