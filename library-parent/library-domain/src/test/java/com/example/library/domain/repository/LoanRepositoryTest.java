package com.example.library.domain.repository;

import com.example.library.domain.config.DomainTestApplication;
import com.example.library.domain.entity.Book;
import com.example.library.domain.entity.Loan;
import com.example.library.domain.entity.User;
import com.example.library.domain.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = DomainTestApplication.class)
class LoanRepositoryTest {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void activeLoanConstraintWorks() {
        Book book = bookRepository.save(new Book("Test Book"));
        User user = userRepository.save(new User("user1", "pwd", Role.MEMBER));

        loanRepository.save(new Loan(book, user));

        assertThat(
                loanRepository.findActiveLoanForBook(book)
        ).isPresent();
    }
}
