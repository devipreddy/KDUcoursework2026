package com.example.library.domain.entity;

import com.example.library.domain.enums.BookStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "books")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    protected Book() {}

    public Book(String title) {
        if (title == null || title.length() < 2) {
            throw new IllegalArgumentException("Title must be at least 2 characters");
        }
        this.title = title;
        this.status = BookStatus.PROCESSING;
    }

    public void markAvailable() {
        this.status = BookStatus.AVAILABLE;
    }

    public void checkOut() {
        if (status != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not available");
        }
        this.status = BookStatus.CHECKED_OUT;
    }

    public void markReturned() {
        this.status = BookStatus.AVAILABLE;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public BookStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Long getVersion() { return version; }
}
