package com.example.task.exception;

public class InvalidBankDataException extends RuntimeException {
    public InvalidBankDataException(String message) {
        super(message);
    }
}
