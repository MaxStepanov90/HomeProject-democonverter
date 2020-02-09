package com.stepanov.democonverter.service;

import com.stepanov.democonverter.entities.Currency;

import java.util.List;

public interface CurrencyService {

    Currency getCurrencyByCharCode(String charCode);
    List<Currency> getCurrencies();
}
