package com.example.task.service;

import com.example.task.exception.BankNotFoundException;
import com.example.task.exception.CountryNotFoundException;
import com.example.task.exception.InvalidBankDataException;
import com.example.task.model.Bank;
import com.example.task.model.Country;
import com.example.task.model.dto.*;
import com.example.task.repository.BankRepository;
import com.example.task.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwiftService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private CountryRepository countryRepository;

    public HeadquarterDTO getHeadquarterById(String swiftCode) {
        Bank bank = bankRepository.findById(swiftCode.toUpperCase())
                .orElseThrow(() -> new BankNotFoundException(swiftCode));

        List<Bank> branches = bankRepository.findAllBySwiftCodeStartingWith(
                swiftCode.substring(0, swiftCode.length()-3).toUpperCase());

        List<BankDTO> branchesDTO = branches
                .stream()
                .filter(b -> !b.isHeadquarter())
                .map(branch -> new BankDTO(
                    branch.getAddress(),
                    branch.getBankName(),
                    branch.getCountry().getCountryISO2(),
                    branch.isHeadquarter(),
                    branch.getSwiftCode()
        )).toList();

        return  new HeadquarterDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountry().getCountryISO2(),
                bank.getCountry().getCountryName(),
                bank.isHeadquarter(),
                bank.getSwiftCode(),
                branchesDTO
        );
    }

    public BranchDTO getBranchById(String swiftCode) {
        Bank bank = bankRepository.findById(swiftCode.toUpperCase())
                .orElseThrow(() -> new BankNotFoundException(swiftCode));

        return new BranchDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountry().getCountryISO2(),
                bank.getCountry().getCountryName(),
                bank.isHeadquarter(),
                bank.getSwiftCode()
        );
    }

    public CountryBanksDTO getAllCodesByCountry(String countryISO2code) {
        Country country = countryRepository.getCountryByCountryISO2(countryISO2code.toUpperCase())
                .orElseThrow(() -> new CountryNotFoundException(countryISO2code));

        List<Bank> banks = bankRepository.findAllByCountry(country);
        List<BankDTO> banksDTOS = banks.stream().map(bank -> new BankDTO(
                bank.getAddress(),
                bank.getBankName(),
                bank.getCountry().getCountryISO2(),
                bank.isHeadquarter(),
                bank.getSwiftCode()
        )).toList();

        return new CountryBanksDTO(
                country.getCountryISO2(),
                country.getCountryName(),
                banksDTOS
        );
    }

    public MessageDTO addNewBank(NewBankDTO newBank) {
        if(newBank.swiftCode() == null)
            throw new InvalidBankDataException("Swift code cannot be empty");
        if(newBank.swiftCode().length() != 11)
            throw new InvalidBankDataException("Swift code must be 11 characters long");
        if(newBank.countryISO2() == null)
            throw new InvalidBankDataException("Country ISO2 code cannot be empty");
        if(newBank.countryISO2().length() != 2)
            throw new InvalidBankDataException("Country ISO2 code must be 2 characters long");
        if(newBank.countryName() == null || newBank.countryName().isBlank())
            throw new InvalidBankDataException("Country name cannot be empty");
        if(newBank.bankName() == null || newBank.bankName().isBlank())
            throw new InvalidBankDataException("Bank name cannot be empty");
        if(bankRepository.findById(newBank.swiftCode()).isPresent())
            throw new InvalidBankDataException("Swift code already exists");

        Country country = countryRepository.getCountryByCountryISO2(newBank.countryISO2())
                .orElseGet(() -> {
                    Country newCountry = new Country(newBank.countryISO2().toUpperCase(), newBank.countryName().toUpperCase());
                    saveCountry(newCountry);
                    return newCountry;
                });

        Bank bank = new Bank(
                newBank.swiftCode().toUpperCase(),
                country,
                newBank.address(),
                newBank.bankName(),
                newBank.isHeadquarter()
        );

        saveBank(bank);
        return new MessageDTO("Bank added successfully");
    }

    private void saveBank(Bank newBank) {
        bankRepository.save(newBank);
    }

    private void saveCountry(Country newCountry) {
        countryRepository.save(newCountry);
    }

    public MessageDTO deleteBank(String swiftCode) {
        bankRepository.findById(swiftCode.toUpperCase())
                .orElseThrow(() -> new BankNotFoundException(swiftCode));

        bankRepository.deleteById(swiftCode);
        return new MessageDTO("Bank deleted successfully");
    }
}
