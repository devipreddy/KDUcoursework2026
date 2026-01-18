package com.example.library.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "loans",
    indexes = {
        @Index(
            name = "idx_active_loan_per_book",
            columnList = "book_id, returned_at"
        )
    }
)
public class Loan {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "borrower_id", nullable = false)
    private User borrower;

    @Column(nullable = false, updatable = false)
    private Instant borrowedAt;

    @Column(name = "returned_at")
    private Instant returnedAt;

    protected Loan() {}

    public Loan(Book book, User borrower) {
        this.book = book;
        this.borrower = borrower;
        this.borrowedAt = Instant.now();
    }

    public boolean isActive() {
        return returnedAt == null;
    }

    public void markReturned() {
        if (returnedAt != null) {
            throw new IllegalStateException("Loan already returned");
        }
        this.returnedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public Book getBook() { return book; }
    public User getBorrower() { return borrower; }
    public Instant getBorrowedAt() { return borrowedAt; }
    public Instant getReturnedAt() { return returnedAt; }
}
