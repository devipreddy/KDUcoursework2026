package com.example.eventsphere.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String location;
    private String date;

    @Column(nullable = false)
    private Long availableTickets;

    @Version
    private Long version;

    public Event() {
    }

    public Event(String name, String description, String location, String date, Long availableTickets) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.availableTickets = availableTickets;
    }

    public Long getId() {
        return id;
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
        if (availableTickets < 0) {
            throw new IllegalArgumentException("Available tickets cannot be negative");
        }
        this.availableTickets = availableTickets;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}   