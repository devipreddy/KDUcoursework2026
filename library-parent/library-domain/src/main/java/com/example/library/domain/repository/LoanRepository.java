package com.example.library.domain.repository;

import com.example.library.domain.entity.Book;
import com.example.library.domain.entity.Loan;
import com.example.library.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    /**
     * Core business rule:
     * Only one active loan per book.
     */
    // @Query("""
    //     select l
    //     from Loan l
    //     where l.book = :book
    //       and l.returnedAt is null
    // """)
    // Optional<Loan> findActiveLoanForBook(Book book);

    /**
     * Fetch loan history for a user.
     */
    List<Loan> findByBorrower(User borrower);

    /**
     * Fetch active loans for a user.
     */
    @Query("""
        select l
        from Loan l
        where l.borrower = :borrower
          and l.returnedAt is null
    """)
    List<Loan> findActiveLoansForBorrower(User borrower);

    @Query("""
        select l
        from Loan l
        where l.book = :book
          and l.returnedAt is null
    """)
    Optional<Loan> findActiveLoanForBook(@Param("book") Book book);
}
