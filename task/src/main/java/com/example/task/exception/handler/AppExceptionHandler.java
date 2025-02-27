package com.example.task.exception.handler;

import com.example.task.exception.BankNotFoundException;
import com.example.task.exception.CountryNotFoundException;
import com.example.task.exception.InvalidBankDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(BankNotFoundException.class)
    public ResponseEntity<String> handleBankNotFound(BankNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<String> handleICountryNotFoundException(CountryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidBankDataException.class)
    public ResponseEntity<String> handleInvalidBankData(InvalidBankDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
    }
}
