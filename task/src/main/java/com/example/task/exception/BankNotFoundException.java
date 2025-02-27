package com.example.task.exception;

public class BankNotFoundException extends RuntimeException {
    public BankNotFoundException(String swiftCode) {
      super("Bank with SWIFT code: " + swiftCode + " not found");
    }
}
