package com.example.library.service;

import com.example.library.domain.entity.Book;
import com.example.library.domain.enums.BookStatus;
import com.example.library.domain.repository.BookRepository;
import com.example.library.service.exception.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.library.service.config.ServiceTestApplication;
import com.example.library.service.config.ServiceTestApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = ServiceTestApplication.class)
@ActiveProfiles("test")
class BookServiceTest {

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Test
    void cannotCatalogNonProcessingBook() {
        Book book = bookRepository.save(new Book("Test Book"));

        // simulate illegal transition via service, not entity
        bookService.catalog(book.getId()); // first catalog → PROCESSING → AVAILABLE

        assertThatThrownBy(() ->
                bookService.catalog(book.getId())
        ).isInstanceOf(ConflictException.class);
    }

}
