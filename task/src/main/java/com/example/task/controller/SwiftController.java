package com.example.task.controller;

import com.example.task.model.dto.NewBankDTO;
import com.example.task.model.dto.CountryBanksDTO;
import com.example.task.model.dto.MessageDTO;
import com.example.task.service.SwiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftController {
    @Autowired
    private SwiftService swiftService;

    @GetMapping("/{swift_code}")
    public Object getSWIFTCodeById(@PathVariable String swift_code){
        if(swift_code.startsWith("XXX", swift_code.length()-3))
            return swiftService.getHeadquarterById(swift_code);
        else
            return swiftService.getBranchById(swift_code);
    }

    @GetMapping("/country/{countryISO2code}")
    public CountryBanksDTO getAllCodes(@PathVariable String countryISO2code){
        return swiftService.getAllCodesByCountry(countryISO2code);
    }

    @PostMapping
    public MessageDTO addNewSwift(@RequestBody NewBankDTO newBankDTO){
        return swiftService.addNewBank(newBankDTO);
    }

    @DeleteMapping("/{swift_code}")
    public MessageDTO deleteBank(@PathVariable String swift_code){
        return swiftService.deleteBank(swift_code);
    }
}
