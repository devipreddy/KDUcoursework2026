package com.example.eventsphere.dto;

import java.time.LocalDateTime;

public class TransactionResponse {
    private String transactionId;
    private Long bookingId;
    private Double amount;
    private String status;
    private LocalDateTime transactionDate;

    public TransactionResponse() {
    }

    public TransactionResponse(String transactionId, Long bookingId, Double amount, String status, LocalDateTime transactionDate) {
        this.transactionId = transactionId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
