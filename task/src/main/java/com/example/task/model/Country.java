package com.example.task.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Country {
    @Id
    private String countryISO2;
    private String countryName;

    public Country(String countryISO2, String countryName) {
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
    }

    public Country() {
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
