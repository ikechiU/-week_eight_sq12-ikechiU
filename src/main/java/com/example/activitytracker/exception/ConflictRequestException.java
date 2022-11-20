package com.example.activitytracker.exception;

public class ConflictRequestException extends RuntimeException {
    public ConflictRequestException(String errorMessage) {
        super(errorMessage);
    }
}
