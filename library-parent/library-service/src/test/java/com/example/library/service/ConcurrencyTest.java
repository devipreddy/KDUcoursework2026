package com.example.library.service;

import com.example.library.domain.entity.Book;
import com.example.library.domain.repository.BookRepository;
import com.example.library.service.config.ServiceTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = ServiceTestApplication.class)
@ActiveProfiles("test")
class ConcurrencyTest {

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Test
    void optimisticLockConflictResultsInException() throws Exception {
        Book book = bookRepository.save(new Book("Concurrent"));

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Void> task = () -> {
            bookService.catalog(book.getId());
            return null;
        };

        Future<Void> f1 = executor.submit(task);
        Future<Void> f2 = executor.submit(task);

        int optimisticFailures = 0;

        for (Future<Void> future : new Future[]{f1, f2}) {
            try {
                future.get();
            } catch (ExecutionException e) {
                if (e.getCause() instanceof ObjectOptimisticLockingFailureException) {
                    optimisticFailures++;
                } else {
                    throw e;
                }
            }
        }

        executor.shutdownNow();

        assertThat(optimisticFailures)
            .as("Exactly one optimistic lock conflict should occur")
            .isEqualTo(1);
    }
}
