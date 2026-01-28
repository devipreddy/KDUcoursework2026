package com.example.library.domain.repository;

import com.example.library.domain.entity.Book;
import com.example.library.domain.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByStatus(BookStatus status);

    /**
     * Used when checking out a book.
     * Optimistic lock is enforced via @Version.
     */
    Optional<Book> findById(UUID id);

    /**
     * OPTIONAL pessimistic locking variant (NOT default).
     * Use only if business requires hard DB locking.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findByIdForUpdate(UUID id);

    Page<Book> findByStatus(
            BookStatus status,
            Pageable pageable
    );

    Page<Book> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable
    );

    Page<Book> findByStatusAndTitleContainingIgnoreCase(
            BookStatus status,
            String title,
            Pageable pageable
    );
}
