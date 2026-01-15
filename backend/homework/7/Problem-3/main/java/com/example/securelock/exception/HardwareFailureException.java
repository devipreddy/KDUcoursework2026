package com.example.securelock.exception;

public class HardwareFailureException extends RuntimeException {
    public HardwareFailureException(String message) {
        super(message);
    }
}
