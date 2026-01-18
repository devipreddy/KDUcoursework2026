package com.example.library.api.error;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Instant timestamp,
        String path,
        String errorCode,
        String message,
        List<FieldError> details,
        String correlationId
) {
    public record FieldError(
            String field,
            String issue
    ) {}
}
