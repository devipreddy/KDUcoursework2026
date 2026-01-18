package com.example.library.web.exception;

import com.example.library.api.error.ErrorResponse;
import com.example.library.service.exception.ConflictException;
import com.example.library.service.exception.NotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildError(
            HttpServletRequest request,
            String errorCode,
            String message,
            List<ErrorResponse.FieldError> details
    ) {
        return new ErrorResponse(
                Instant.now(),
                request.getRequestURI(),
                errorCode,
                message,
                details,
                MDC.get("correlationId")
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<ErrorResponse.FieldError> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> new ErrorResponse.FieldError(
                        f.getField(),
                        f.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity.badRequest().body(
                buildError(
                        request,
                        "VALIDATION_ERROR",
                        "Invalid request",
                        details
                )
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                buildError(
                        request,
                        "NOT_FOUND",
                        ex.getMessage(),
                        null
                )
        );
    }

    @ExceptionHandler({ConflictException.class, OptimisticLockException.class})
    public ResponseEntity<ErrorResponse> conflict(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                buildError(
                        request,
                        "CONCURRENT_MODIFICATION",
                        ex.getMessage(),
                        null
                )
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> illegalState(
            IllegalStateException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                buildError(
                        request,
                        "INVALID_STATE_TRANSITION",
                        ex.getMessage(),
                        null
                )
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDenied(
            AccessDeniedException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                buildError(
                        request,
                        "FORBIDDEN",
                        "Access denied",
                        null
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> generic(
            Exception ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                buildError(
                        request,
                        "INTERNAL_ERROR",
                        "Unexpected error",
                        null
                )
        );
    }
}
