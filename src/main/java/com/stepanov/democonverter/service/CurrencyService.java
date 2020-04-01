package com.stepanov.democonverter.service;

import com.stepanov.democonverter.entities.Currency;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyService {

    Currency getCurrencyByCharCode(String charCode);
    LocalDate getLoadDateOfCurrency(String sourceName);
    List<Currency> getCurrencies();
}
