package com.example.task.model.dto;

public record BankDTO(String address,
                      String bankName,
                      String countryISO2,
                      boolean isHeadquarter,
                      String swiftCode) {
}
