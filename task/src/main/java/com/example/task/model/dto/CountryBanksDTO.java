package com.example.task.model.dto;

import java.util.List;

public record CountryBanksDTO(String countryISO2,
                              String countryName,
                              List<BankDTO> swiftCodes) {
}
