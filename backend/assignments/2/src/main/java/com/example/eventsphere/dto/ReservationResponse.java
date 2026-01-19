package com.example.eventsphere.dto;

public class ReservationResponse {
    private Long id;
    private Long eventId;
    private String eventName;
    private Integer ticketCount;
    private String status;

    public ReservationResponse() {
    }

    public ReservationResponse(Long id, Long eventId, String eventName, Integer ticketCount, String status) {
        this.id = id;
        this.eventId = eventId;
        this.eventName = eventName;
        this.ticketCount = ticketCount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Integer ticketCount) {
        this.ticketCount = ticketCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
