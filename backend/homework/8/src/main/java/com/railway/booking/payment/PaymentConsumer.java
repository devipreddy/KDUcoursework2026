package com.railway.booking.payment;

import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.messaging.BookingQueue;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private final BookingQueue bkQueue;
    private final ProcessedTransactionStore store;

    public PaymentConsumer(BookingQueue bkQueue,
                           ProcessedTransactionStore store) {
        this.bkQueue = bkQueue;
        this.store = store;
    }

    @PostConstruct
    public void start() {
        new Thread(() -> {
            while (true) {

                TicketBookedEvent event = null;

                try {

                    event = bkQueue.paymentQueue.take();

                    if (store.alreadyProcessed(event.transactionId())) {
                        System.out.println(
                                "Transaction " + event.transactionId()
                                + " already processed. Ignoring duplicate."
                        );
                        continue; 
                    }

                    processPayment(event);

                    System.out.println(
                            "Payment successful for transaction "
                            + event.transactionId()
                    );

                } catch (Exception ex) {

                    System.err.println(
                            "Payment failed, re-queueing transaction "
                            + (event != null ? event.transactionId() : "unknown")
                    );

                    if (event != null) {
                        bkQueue.paymentQueue.offer(event);
                    }
                }
            }
        }, "payment-consumer").start();
    }

    private void processPayment(TicketBookedEvent event) {
        if (event.age() < 0) {
            throw new RuntimeException("Payment gateway failure");
        }

    }
}
