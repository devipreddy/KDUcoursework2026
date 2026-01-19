package com.example.eventsphere.dto;

public class CreateReservationRequest {
    private Long eventId;
    private Integer ticketCount;

    public CreateReservationRequest() {
    }

    public CreateReservationRequest(Long eventId, Integer ticketCount) {
        this.eventId = eventId;
        this.ticketCount = ticketCount;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Integer ticketCount) {
        this.ticketCount = ticketCount;
    }
}
