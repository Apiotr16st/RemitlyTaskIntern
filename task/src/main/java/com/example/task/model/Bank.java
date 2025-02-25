package com.example.task.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Bank {
    @Id
    private String swiftCode;
    @ManyToOne
    private Country country;
    private String bankName;
    private String address;
    private boolean isHeadquarter;

    public Bank(String swiftCode, Country country, String bankName, String address, boolean isHeadquarter) {
        this.swiftCode = swiftCode;
        this.country = country;
        this.bankName = bankName;
        this.address = address;
        this.isHeadquarter = isHeadquarter;
    }

    public Bank() {
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
