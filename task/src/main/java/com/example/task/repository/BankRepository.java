package com.example.task.repository;

import com.example.task.model.Bank;
import com.example.task.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {
    List<Bank> findAllBySwiftCodeStartingWith(String substring);
    List<Bank> findAllByCountry(Country country);
}
