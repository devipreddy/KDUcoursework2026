package com.railway.booking.retry;

import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.messaging.BookingQueue;
import org.springframework.stereotype.Component;
import java.util.concurrent.BlockingQueue;
@Component
public class RetryHandler{

    private static final int MAX_RETRIES = 3;

    private static final long RETRY_DELAY_MS = 500;

    private final BookingQueue bkQueue;

    public RetryHandler(BookingQueue bkQueue){
        this.bkQueue = bkQueue;
    }

    public void handleFailure(
        TicketBookedEvent event,
        int attempt,
        BlockingQueue<TicketBookedEvent> sourceQueue)
    {
        try{
            if(attempt<MAX_RETRIES){
                Thread.sleep(RETRY_DELAY_MS);
                sourceQueue.offer(event);
            } else{
                bkQueue.deadLetterQueue.offer(event);
                System.out.println("Moved to DLQ" + event.bookingId());

            }
        } catch (InterruptedException i){}

    }

}
