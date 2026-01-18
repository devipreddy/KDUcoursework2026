package com.example.library.service;

import com.example.library.domain.entity.Book;
import com.example.library.domain.entity.Loan;
import com.example.library.domain.entity.User;
import com.example.library.domain.repository.BookRepository;
import com.example.library.domain.repository.LoanRepository;
import com.example.library.domain.repository.UserRepository;
import com.example.library.service.exception.ConflictException;
import com.example.library.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.domain.enums.BookStatus;
import jakarta.persistence.OptimisticLockException;

import java.util.UUID;

/**
 * Service responsible for managing book loans.
 *
 * <p>This service enforces borrowing and returning rules and ensures
 * that only valid borrowers can return books.</p>
 */
@Service
@Transactional
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    /**
     * Constructs the loan service.
     */
    public LoanService(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            UserRepository userRepository
    ) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    /**
     * Borrows a book for the given user.
     *
     * @param bookId identifier of the book
     * @param username username of the borrower
     * @return created loan
     */
    public Loan borrow(UUID bookId, String username) {
        try {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new NotFoundException("Book not found"));

            if (book.getStatus() != BookStatus.AVAILABLE) {
                throw new ConflictException("BOOK_NOT_AVAILABLE");
            }

            if (loanRepository.findActiveLoanForBook(book).isPresent()) {
                throw new ConflictException("BOOK_ALREADY_BORROWED");
            }

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            book.checkOut();
            return loanRepository.save(new Loan(book, user));

        } catch (OptimisticLockException e) {
            throw new ConflictException("CONCURRENT_MODIFICATION");
        }
    }

    /**
     * Returns a previously borrowed book.
     *
     * @param bookId identifier of the book
     * @param username username of the borrower
     */
    public void returnBook(UUID bookId, String username) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        Loan loan = loanRepository.findActiveLoanForBook(book)
                .orElseThrow(() -> new ConflictException("No active loan"));

        if (!loan.getBorrower().getUsername().equals(username)) {
            throw new ConflictException("Not borrower");
        }

        loan.markReturned();
        book.markReturned();
    }
}
