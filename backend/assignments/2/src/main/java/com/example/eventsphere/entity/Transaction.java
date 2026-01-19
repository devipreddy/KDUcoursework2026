package com.example.eventsphere.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String transactionId;

    @ManyToOne
    private Booking booking;

    @ManyToOne
    private TicketUser user;
    
    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    public enum TransactionStatus {
        SUCCESS,
        FAILED,
        CANCELLED
    }

    public Transaction() {
        this.transactionId = UUID.randomUUID().toString();
        this.transactionDate = LocalDateTime.now();
        this.status = TransactionStatus.SUCCESS;
    }

    public Transaction(Booking booking, TicketUser user, Double amount) {
        this();
        this.booking = booking;
        this.user = user;
        this.amount = amount;
    }


    public Long getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Booking getBooking() {
        return booking;
    }

    public TicketUser getUser() {
        return user;
    }

    public Double getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void setUser(TicketUser user) {
        this.user = user;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
