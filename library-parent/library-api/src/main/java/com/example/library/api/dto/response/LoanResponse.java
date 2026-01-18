package com.example.library.api.dto.response;

import java.time.Instant;
import java.util.UUID;

/**
 * API response representing a loan transaction.
 */
public class LoanResponse {

    /** Unique loan identifier */
    private UUID id;

    /** Identifier of the borrowed book */
    private UUID bookId;

    /** Identifier of the borrowing user */
    private UUID borrowerId;

    /** Timestamp when the book was borrowed */
    private Instant borrowedAt;

    /** Timestamp when the book was returned (nullable) */
    private Instant returnedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getBookId() { return bookId; }
    public void setBookId(UUID bookId) { this.bookId = bookId; }

    public UUID getBorrowerId() { return borrowerId; }
    public void setBorrowerId(UUID borrowerId) { this.borrowerId = borrowerId; }

    public Instant getBorrowedAt() { return borrowedAt; }
    public void setBorrowedAt(Instant borrowedAt) { this.borrowedAt = borrowedAt; }

    public Instant getReturnedAt() { return returnedAt; }
    public void setReturnedAt(Instant returnedAt) { this.returnedAt = returnedAt; }
}
