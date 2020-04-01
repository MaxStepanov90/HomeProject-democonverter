package com.stepanov.democonverter.service;

import com.stepanov.democonverter.entities.Currency;
import com.stepanov.democonverter.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency getCurrencyByCharCode(String charCode) {
        return currencyRepository.findByCharCode(charCode);
    }

    @Override
    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }
    @Override
    public LocalDate getLoadDateOfCurrency(String sourceName){
        return getCurrencyByCharCode(sourceName).getLoadDate();
    }
}

