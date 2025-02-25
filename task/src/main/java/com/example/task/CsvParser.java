package com.example.task;

import com.example.task.model.Bank;
import com.example.task.model.Country;
import com.example.task.repository.BankRepository;
import com.example.task.repository.CountryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;

@Configuration
public class CsvParser {
    @Bean
    CommandLineRunner commandLineRunner(CountryRepository countryRepository,
                                        BankRepository bankRepository) {
        return args -> {
            if (countryRepository.count() == 0) { // check if the database is empty
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("SWIFT_CODES.csv");

                if (inputStream == null) {
                    throw new FileNotFoundException("File not found");
                }

                try (Reader reader = new InputStreamReader(inputStream)) {
                    Iterable<CSVRecord> records = CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .withQuote('"')
                            .parse(reader);
                    for (CSVRecord record : records) {
                        if(countryRepository.findById(record.get(0)).isEmpty()){
                            Country country = new Country(
                                    record.get(0),
                                    record.get(6)
                            );
                            countryRepository.save(country);
                        }

                        String swiftCode = record.get(1);

                        Bank bank = new Bank(
                                swiftCode,
                                countryRepository.findById(record.get(0)).get(),
                                record.get(3),
                                record.get(4),
                                swiftCode.startsWith("XXX", swiftCode.length() - 3)
                        );
                        bankRepository.save(bank);
                    }
                }
            }
        };
    }
}
