package com.example.library.service;

import com.example.library.domain.entity.Book;
import com.example.library.domain.enums.BookStatus;
import com.example.library.domain.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service responsible for read-only analytical operations on the library domain.
 *
 * <p>This service provides aggregated, system-wide insights and must never
 * mutate domain state. All methods are executed in a read-only transaction
 * to avoid unnecessary persistence context overhead.</p>
 *
 * <p>Typical use cases include operational dashboards, audits, and reporting.</p>
 */
@Service
@Transactional(readOnly = true)
public class AnalyticsService {

    private final BookRepository bookRepository;

    /**
     * Constructs the analytics service.
     *
     * @param bookRepository repository used to access book data
     */
    public AnalyticsService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Computes the number of books grouped by their current status.
     *
     * <p>The aggregation is performed in-memory using Java Streams.
     * An {@link java.util.EnumMap} is used for optimal performance and
     * memory efficiency.</p>
     *
     * @return map of {@link BookStatus} to count of books in that status
     */
    public Map<BookStatus, Long> auditBooksByStatus() {
        return bookRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Book::getStatus,
                        () -> new EnumMap<>(BookStatus.class),
                        Collectors.counting()
                ));
    }
}

