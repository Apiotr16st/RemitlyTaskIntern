package com.example.task.service;

import com.example.task.exception.BankNotFoundException;
import com.example.task.exception.CountryNotFoundException;
import com.example.task.exception.InvalidBankDataException;
import com.example.task.model.Bank;
import com.example.task.model.Country;
import com.example.task.model.dto.*;
import com.example.task.repository.BankRepository;
import com.example.task.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SwiftServiceTest {
    @Mock
    private BankRepository bankRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private SwiftService swiftService;

    private Bank bank;
    private Country country;

    @BeforeEach
    public void setUp() {
        country = new Country("CO","COUNTRY");
        bank = new Bank("ABCDEFGHXXX", country,"name", "address",true);

        lenient().when(bankRepository.findById("ABCDEFGHXXX")).thenReturn(Optional.of(bank));
        lenient().when(countryRepository.getCountryByCountryISO2("CO")).thenReturn(Optional.of(country));
        lenient().when(countryRepository.getCountryByCountryISO2("XX")).thenReturn(Optional.empty());
    }

    @Test
    public void testGetHeadquarterById() {
        Bank branch1 = new Bank("ABCDEFGHKKK", country,"name1", "address1",false);
        Bank branch2 = new Bank("ABCDEFGHKKJ", country,"name2", "address2",false);


        when(bankRepository.findAllBySwiftCodeStartingWith("ABCDEFGH")).thenReturn(List.of(branch1, branch2));

        HeadquarterDTO expected = new HeadquarterDTO("address", "name",
                "CO", "COUNTRY", true, "ABCDEFGHXXX",
                List.of(new BankDTO("address1", "name1", "CO",false, "ABCDEFGHKKK"),
                        new BankDTO("address2", "name2", "CO",false, "ABCDEFGHKKJ"))
        );

        assertEquals(expected, swiftService.getHeadquarterById("ABCDEFGHXXX"));
        assertThrowsExactly(BankNotFoundException.class, () -> swiftService.getBranchById("ABCDEFGXXX"));
    }

    @Test
    public void testGetBranchById() {
        Bank branch3 = new Bank("YZZZZZZZZZZ", country,"name2", "address2",false);

        when(bankRepository.findById("YZZZZZZZZZZ")).thenReturn(Optional.of(branch3));

        BranchDTO expectedBranch = new BranchDTO("address2", "name2", "CO",
                "COUNTRY", false, "YZZZZZZZZZZ");

        assertEquals(expectedBranch, swiftService.getBranchById("YZZZZZZZZZZ"));
        assertThrowsExactly(BankNotFoundException.class, () -> swiftService.getHeadquarterById("YZZZZZZZXXX"));
    }

    @Test
    public void testGetAllCodesByCountry() {
        Bank bank1 = new Bank("ABCDEFGHXXX", country,"name", "address",true);
        Bank bank2 = new Bank("ABCDEFGHKKK", country,"name1", "address1",false);
        Bank bank3 = new Bank("ABCDEFGHKKJ", country,"name2", "address2",false);

        when(bankRepository.findAllByCountry(country)).thenReturn(List.of(bank1, bank2, bank3));

        CountryBanksDTO expectedCountry = new CountryBanksDTO("CO", "COUNTRY",
                List.of(new BankDTO("address", "name", "CO", true, "ABCDEFGHXXX"),
                        new BankDTO("address1", "name1", "CO", false, "ABCDEFGHKKK"),
                        new BankDTO("address2", "name2", "CO", false, "ABCDEFGHKKJ"))
        );

        assertEquals(expectedCountry, swiftService.getAllCodesByCountry("CO"));
        assertThrowsExactly(CountryNotFoundException.class, () -> swiftService.getAllCodesByCountry("XX"));
    }

    @Test
    public void testAddNewBank() {
        NewBankDTO newBankOK = new NewBankDTO("address","name","CO", "COUNTRY",true,"ABABABABXXX");
        NewBankDTO newBankFailBankName = new NewBankDTO("address",null,"CO", "COUNTRY",true,"ABABABABXXX");
        NewBankDTO newBankFailISO2 = new NewBankDTO("address","name",null, "COUNTRY",true,"ABABABABXXX");
        NewBankDTO newBankFailCountryName = new NewBankDTO("address","name","CO", null,true,"ABABABABXXX");
        NewBankDTO newBankFailSwiftCode = new NewBankDTO("address","name","CO", "COUNTRY",true,null);
        NewBankDTO newBankFailSwiftCodeExists = new NewBankDTO("address","name","CO", "COUNTRY",true,"ABCDEFGHXXX");
        NewBankDTO newBankFailEmptyISO = new NewBankDTO("address","name","", "COUNTRY",true,"ABABABABXXX");
        NewBankDTO newBankFailEmptySwiftCode = new NewBankDTO("address","name","CO", "COUNTRY",true,"");
        NewBankDTO newBankFailEmptyCountryName = new NewBankDTO("address","name","CO", "",true,"ABABABABXXX");

        when(bankRepository.findById("ABABABABXXX")).thenReturn(Optional.empty());

        assertEquals(new MessageDTO("Bank added successfully"), swiftService.addNewBank(newBankOK));
        assertThrowsExactly(InvalidBankDataException.class, () -> swiftService.addNewBank(newBankFailBankName));
        assertThrowsExactly(InvalidBankDataException.class, () -> swiftService.addNewBank(newBankFailISO2));
        assertThrowsExactly(InvalidBankDataException.class, () -> swiftService.addNewBank(newBankFailCountryName));
        assertThrowsExactly(InvalidBankDataException.class, () -> swiftService.addNewBank(newBankFailSwiftCode));
        assertThrowsExactly(InvalidBankDataException.class, () -> swiftService.addNewBank(newBankFailSwiftCodeExists));
        assertThrowsExactly(InvalidBankDataException.class, () -> swiftService.addNewBank(newBankFailEmptyISO));
        assertThrowsExactly(InvalidBankDataException.class, () -> swiftService.addNewBank(newBankFailEmptySwiftCode));
        assertThrowsExactly(InvalidBankDataException.class, () -> swiftService.addNewBank(newBankFailEmptyCountryName));
    }

    @Test
    public void testDeleteBank() {
        assertEquals(new MessageDTO("Bank deleted successfully"), swiftService.deleteBank("ABCDEFGHXXX"));
        assertThrowsExactly(BankNotFoundException.class, () -> swiftService.deleteBank("YZZZZZZZXXX"));
    }
}
