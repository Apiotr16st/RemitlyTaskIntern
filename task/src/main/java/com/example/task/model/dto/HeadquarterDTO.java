package com.example.task.model.dto;

import java.util.List;

public record HeadquarterDTO(String address,
                             String bankName,
                             String countryISO2,
                             String countryName,
                             boolean isHeadquarter,
                             String swiftCode,
                             List<BankDTO> branches){
}
