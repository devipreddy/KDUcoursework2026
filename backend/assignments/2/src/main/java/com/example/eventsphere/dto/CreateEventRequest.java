package com.example.eventsphere.dto;

public class CreateEventRequest {
    private String name;
    private String description;
    private String location;
    private String date;
    private Long ticketCount;

    public CreateEventRequest() {
    }

    public CreateEventRequest(String name, String description, String location, String date, Long ticketCount) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.ticketCount = ticketCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Long ticketCount) {
        this.ticketCount = ticketCount;
    }
}
