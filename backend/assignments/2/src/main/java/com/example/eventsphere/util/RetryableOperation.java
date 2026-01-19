package com.example.eventsphere.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@FunctionalInterface
public interface RetryableOperation<T> {

    T execute() throws Exception;

    Logger log = LoggerFactory.getLogger(RetryableOperation.class);

    static <T> T executeWithRetry(
            RetryableOperation<T> operation,
            int maxRetries,
            long initialDelayMs) {

        long delay = initialDelayMs;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                return operation.execute();
            } catch (ObjectOptimisticLockingFailureException ex) {

                if (attempt == maxRetries) {
                    log.error("Retry limit reached after {} attempts", maxRetries);
                    throw ex;
                }

                log.warn(
                        "Optimistic lock failure (attempt {} of {}), retrying in {} ms",
                        attempt, maxRetries, delay
                );

                pause(delay);
                delay = (long) (delay * 1.5);

            } catch (Exception ex) {
                throw new RuntimeException(
                        "Unexpected failure during retry", ex
                );
            }
        }

        throw new IllegalStateException("Retry loop exited unexpectedly");
    }

    private static void pause(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Retry was interrupted", e);
        }
    }
}
