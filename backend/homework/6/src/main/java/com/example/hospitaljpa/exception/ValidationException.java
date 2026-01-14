package com.example.hospitaljpa.exception;


public class ValidationException extends RuntimeException {

    private final String errorCode;

    public ValidationException(String message) {
        super(message);
        this.errorCode = "VALIDATION_FAILED";
    }

    public String getErrorCode() {
        return errorCode;
    }
}
