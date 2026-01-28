// package com.example.library.service;

// import com.example.library.domain.entity.Book;
// import com.example.library.domain.enums.BookStatus;
// import com.example.library.domain.repository.BookRepository;
// import jakarta.persistence.OptimisticLockException;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.List;
// import java.util.UUID;

// @Service
// @Transactional
// public class BookService {

//     private final BookRepository bookRepository;

//     public BookService(BookRepository bookRepository) {
//         this.bookRepository = bookRepository;
//     }

//     public Book createBook(String title) {
//         Book book = new Book(title);
//         return bookRepository.save(book);
//     }

//     public Book markBookAvailable(UUID bookId) {
//         Book book = bookRepository.findById(bookId)
//                 .orElseThrow(() -> new IllegalArgumentException("Book not found"));

//         book.markAvailable();
//         return book;
//     }

//     @Transactional(readOnly = true)
//     public List<Book> getAllBooks() {
//         return bookRepository.findAll();
//     }

//     @Transactional(readOnly = true)
//     public List<Book> getAvailableBooks() {
//         return bookRepository.findByStatus(BookStatus.AVAILABLE);
//     }

//     public Book checkOutBook(UUID bookId) {
//         try {
//             Book book = bookRepository.findById(bookId)
//                     .orElseThrow(() -> new IllegalArgumentException("Book not found"));
//             book.checkOut();
//             return book;
//         } catch (OptimisticLockException e) {
//             throw new IllegalStateException("Book was updated concurrently");
//         }
//     }

//     public Book returnBook(UUID bookId) {
//         Book book = bookRepository.findById(bookId)
//                 .orElseThrow(() -> new IllegalArgumentException("Book not found"));

//         book.markReturned();
//         return book;
//     }
// }
package com.example.library.service;

import com.example.library.domain.entity.Book;
import com.example.library.domain.enums.BookStatus;
import com.example.library.domain.repository.BookRepository;
import com.example.library.service.exception.ConflictException;
import com.example.library.service.exception.NotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.*;

/**
 * Core domain service responsible for managing {@link Book} lifecycle
 * and enforcing valid state transitions.
 *
 * <p>This service encapsulates all business rules related to books and ensures
 * that invalid transitions (e.g., cataloging a non-processing book) are rejected.</p>
 *
 * <p>All write operations are transactional and concurrency-safe.</p>
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Constructs the book service.
     *
     * @param bookRepository repository for book persistence
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Creates a new book in {@link BookStatus#PROCESSING} state.
     *
     * @param title title of the book
     * @return newly created book entity
     */
    @Transactional
    public Book createBook(String title) {
        return bookRepository.save(new Book(title));
    }

    /**
     * Catalogs a book by transitioning it from PROCESSING to AVAILABLE.
     *
     * @param id unique identifier of the book
     * @return updated book
     * @throws NotFoundException if the book does not exist
     * @throws ConflictException if the state transition is invalid
     */
    @Transactional
    public Book catalog(UUID id) {
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Book not found"));

            if (book.getStatus() != BookStatus.PROCESSING) {
                throw new ConflictException("INVALID_STATE_TRANSITION");
            }

            book.markAvailable();
            return book;

        } catch (OptimisticLockException e) {
            throw new ConflictException("CONCURRENT_MODIFICATION");
        }
    }

    /**
     * Checks out a book by transitioning it from AVAILABLE to CHECKED_OUT.
     *
     * @param id book identifier
     * @return updated book
     */
    @Transactional
    public Book checkOut(UUID id) {
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Book not found"));

            if (book.getStatus() != BookStatus.AVAILABLE) {
                throw new ConflictException("BOOK_NOT_AVAILABLE");
            }

            book.checkOut();
            return book;

        } catch (OptimisticLockException e) {
            throw new ConflictException("CONCURRENT_MODIFICATION");
        }
    }

    /**
     * Returns a checked-out book back to AVAILABLE state.
     *
     * @param id book identifier
     * @return updated book
     */
    @Transactional
    public Book returnBook(UUID id) {
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Book not found"));

            if (book.getStatus() != BookStatus.CHECKED_OUT) {
                throw new ConflictException("INVALID_STATE_TRANSITION");
            }

            book.markReturned();
            return book;

        } catch (OptimisticLockException e) {
            throw new ConflictException("CONCURRENT_MODIFICATION");
        }
    }

    /**
     * Retrieves all books in the system.
     *
     * @return list of books
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves titles of all AVAILABLE books sorted alphabetically.
     *
     * <p>This method demonstrates safe entity-to-DTO style mapping.</p>
     *
     * @return sorted list of book titles
     */
    @Transactional(readOnly = true)
    public List<String> getAvailableBookTitlesSorted() {
        return bookRepository.findByStatus(BookStatus.AVAILABLE)
                .stream()
                .map(Book::getTitle)
                .sorted(String::compareToIgnoreCase)
                .toList();
    }

    /**
     * Retrieves books with optional filtering and pagination.
     *
     * @param status optional book status filter
     * @param titleContains optional title substring filter
     * @param pageable pagination and sorting information
     * @return paged result of books
     */
    @Transactional(readOnly = true)
    public Page<Book> getBooks(
            BookStatus status,
            String titleContains,
            Pageable pageable
    ) {
        if (status != null && titleContains != null) {
            return bookRepository.findByStatusAndTitleContainingIgnoreCase(
                    status, titleContains, pageable);
        }
        if (status != null) {
            return bookRepository.findByStatus(status, pageable);
        }
        if (titleContains != null) {
            return bookRepository.findByTitleContainingIgnoreCase(
                    titleContains, pageable);
        }
        return bookRepository.findAll(pageable);
    }
}
