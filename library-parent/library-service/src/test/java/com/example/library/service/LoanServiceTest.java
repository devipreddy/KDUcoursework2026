package com.example.library.service;

import com.example.library.domain.entity.Book;
import com.example.library.domain.entity.User;
import com.example.library.domain.enums.BookStatus;
import com.example.library.domain.enums.Role;
import com.example.library.domain.repository.BookRepository;
import com.example.library.domain.repository.UserRepository;
import com.example.library.service.exception.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.library.service.config.ServiceTestApplication;
import static org.assertj.core.api.Assertions.*;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ServiceTestApplication.class)
@ActiveProfiles("test")
class LoanServiceTest {

    @Autowired LoanService loanService;
    @Autowired BookRepository bookRepository;
    @Autowired UserRepository userRepository;

    @Test
    void cannotBorrowUnavailableBook() {
        Book book = bookRepository.save(new Book("Test"));
        User user = userRepository.save(
                new User("u1", "pwd", Role.MEMBER)
        );

        assertThatThrownBy(() ->
                loanService.borrow(book.getId(), user.getUsername()))
                .isInstanceOf(ConflictException.class);
    }
}
