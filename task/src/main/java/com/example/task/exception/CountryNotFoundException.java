package com.example.task.exception;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String countryISO2) {
      super("Country with CountryISO2 code: " + countryISO2 + " not found");
    }
}
