package com.example.task.exception;

public class ConflictException extends RuntimeException{
    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}
