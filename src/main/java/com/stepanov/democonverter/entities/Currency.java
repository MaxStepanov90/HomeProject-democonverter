package com.stepanov.democonverter.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(schema = "public", name = "currency")
public class Currency {

    @Id
    private String Identity;
    private String charCode;
    private String name;
    private int nominal;
    private double value;
    private LocalDate loadDate;


    public Currency() {
    }

    public Currency(String Identity, String charCode, String name, int nominal, double value, LocalDate loadDate) {

        this.Identity = Identity;
        this.name = name;
        this.charCode = charCode;
        this.nominal = nominal;
        this.value = value;
        this.loadDate = loadDate;
    }


    public String getIdentity() {
        return Identity;
    }

    public void setIdentity(String identity) {
        Identity = identity;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(LocalDate loadDate) {
        this.loadDate = loadDate;
    }
}

