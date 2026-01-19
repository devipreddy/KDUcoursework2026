package com.example.eventsphere.dto;

public class BookingResponse {
    private Long id;
    private Long eventId;
    private String eventName;
    private Integer numberOfTickets;
    private String status;

    public BookingResponse() {
    }

    public BookingResponse(Long id, Long eventId, String eventName, Integer numberOfTickets, String status) {
        this.id = id;
        this.eventId = eventId;
        this.eventName = eventName;
        this.numberOfTickets = numberOfTickets;
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

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
