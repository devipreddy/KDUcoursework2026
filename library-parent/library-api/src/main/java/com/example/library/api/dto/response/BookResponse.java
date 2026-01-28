package com.example.library.api.dto.response;

import com.example.library.api.enums.BookStatus;

import java.time.Instant;
import java.util.UUID;

/**
 * API response representing a book.
 *
 * <p>This DTO is intentionally decoupled from the domain entity.</p>
 */
public class BookResponse {

    /** Unique identifier of the book */
    private UUID id;

    /** Title of the book */
    private String title;

    /** Current lifecycle status of the book */
    private BookStatus status;

    /** Timestamp when the book was created */
    private Instant createdAt;

    /** Timestamp of the last update */
    private Instant updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
