package com.example.task.repository;

import com.example.task.model.Bank;
import com.example.task.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {
    List<Bank> findAllBySwiftCodeContains(String swiftCode);
    List<Bank> findAllByCountry(Country country);
}
