package com.example.task.model.dto;

public record BranchDTO(String address,
                        String bankName,
                        String countryISO2,
                        String countryName,
                        boolean isHeadquarter,
                        String swiftCode) {
}

