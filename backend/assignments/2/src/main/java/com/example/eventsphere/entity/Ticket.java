package com.example.eventsphere.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketType;
    private double price;
    private int quantity;

    public Ticket() {
    }

    public Ticket(Long id, String ticketType, double price, int quantity) {
        this.id = id;
        this.ticketType = ticketType;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }


    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
