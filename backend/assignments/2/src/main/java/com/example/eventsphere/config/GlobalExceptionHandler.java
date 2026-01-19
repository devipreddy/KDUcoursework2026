package com.example.eventsphere.config;

import com.example.eventsphere.exception.ConcurrencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Object> handleOptimisticLocking(
            ObjectOptimisticLockingFailureException ex,
            WebRequest request) {

        log.warn("Optimistic locking conflict: {}", ex.getMessage());

        return buildResponse(
                HttpStatus.CONFLICT,
                "CONCURRENCY_CONFLICT",
                "The data was modified by another user. Please try again.",
                ex.getMessage()
        );
    }

    @ExceptionHandler(ConcurrencyException.class)
    public ResponseEntity<Object> handleConcurrency(
            ConcurrencyException ex,
            WebRequest request) {

        log.warn("Concurrency error: {}", ex.getMessage());

        return buildResponse(
                HttpStatus.CONFLICT,
                "CONCURRENCY_ERROR",
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleBadRequest(
            IllegalArgumentException ex,
            WebRequest request) {

        log.warn("Validation failure: {}", ex.getMessage());

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleUnexpected(
            RuntimeException ex,
            WebRequest request) {

        log.error("Unhandled exception", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                ex.getMessage(),
                null
        );
    }

    private ResponseEntity<Object> buildResponse(
            HttpStatus status,
            String errorCode,
            String message,
            String details) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", errorCode);
        body.put("message", message);

        if (details != null) {
            body.put("details", details);
        }

        return new ResponseEntity<>(body, status);
    }
}
