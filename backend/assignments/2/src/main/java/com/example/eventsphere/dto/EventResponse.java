package com.example.eventsphere.dto;

public class EventResponse {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String date;
    private Long availableTickets;

    public EventResponse() {
    }

    public EventResponse(Long id, String name, String description, String location, String date, Long availableTickets) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.availableTickets = availableTickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(Long availableTickets) {
        this.availableTickets = availableTickets;
    }
}
