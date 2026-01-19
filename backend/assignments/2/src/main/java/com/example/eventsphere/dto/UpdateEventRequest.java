package com.example.eventsphere.dto;

public class UpdateEventRequest {
    private Long ticketCount;

    public UpdateEventRequest() {
    }

    public UpdateEventRequest(Long ticketCount) {
        this.ticketCount = ticketCount;
    }

    public Long getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Long ticketCount) {
        this.ticketCount = ticketCount;
    }
}
