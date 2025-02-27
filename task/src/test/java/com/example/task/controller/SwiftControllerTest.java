package com.example.task.controller;

import com.example.task.model.dto.*;
import com.example.task.service.SwiftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class SwiftControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SwiftService swiftService;

    @InjectMocks
    private SwiftController swiftController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(swiftController).build(); // Ręczna konfiguracja
    }

    @Test
    void testGetSwiftCodeByIdEndsWithXXX() throws Exception {
        HeadquarterDTO headquarterDTO = new HeadquarterDTO("address", "name", "PL",
                "Poland", true, "ABCDEFGHXXX", null);

        when(swiftService.getHeadquarterById("ABCDEFGHXXX"))
                .thenReturn(headquarterDTO);

        mockMvc.perform(get("/v1/swift-codes/ABCDEFGHXXX"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(headquarterDTO)));
    }

    @Test
    void testGetSwiftCodeByIdNotEndsWithXXX() throws Exception {
        BranchDTO branchDTO = new BranchDTO("address", "name", "CO",
                "COUNTRY", false, "ABCDEFGHIJK");
        when(swiftService.getBranchById("ABCDEFGHIJK"))
                .thenReturn(branchDTO);

        mockMvc.perform(get("/v1/swift-codes/ABCDEFGHIJK"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(branchDTO)));
    }

    @Test
    void testGetAllCodesByCountry() throws Exception {
        BankDTO bankDTO1 = new BankDTO("address1", "name1", "CO", false, "ABCDEFGHIJK");
        BankDTO bankDTO2 = new BankDTO("address2", "name2", "CO", false, "ABCDEFGHIJL");
        CountryBanksDTO countryBanksDTO = new CountryBanksDTO("CO", "COUNTRY", List.of(bankDTO1, bankDTO2));

        when(swiftService.getAllCodesByCountry("CO"))
                .thenReturn(countryBanksDTO);

        mockMvc.perform(get("/v1/swift-codes/country/CO"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(countryBanksDTO)));
    }

    @Test
    void testGetAllCodesByCountryThatDoesNotExists() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/ABCDEFGHIJK"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testGetSwiftCodeByIdNotFound() throws Exception {
        mockMvc.perform(get("/v1/swift-codes/UNKNOWN"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }


    @Test
    void testAddNewBank() throws Exception {
        NewBankDTO newBankDTO = new NewBankDTO("address", "name", "CO", "COUNTRY", false, "ABCDEFGHIJK");
        when(swiftService.addNewBank(any(NewBankDTO.class)))
                .thenReturn(new MessageDTO("Bank added"));

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBankDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Bank added"));
    }

    @Test
    void testDeleteBank() throws Exception {
        when(swiftService.deleteBank("ABCDEFGHIJK"))
                .thenReturn(new MessageDTO("Bank deleted"));

        mockMvc.perform(delete("/v1/swift-codes/ABCDEFGHIJK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Bank deleted"));
    }
}
