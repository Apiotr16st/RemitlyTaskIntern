package com.example.task.repository;

import com.example.task.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    Optional<Country> getCountryByCountryISO2(String countryISO2code);
}
