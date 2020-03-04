package com.stepanov.democonverter.service;

import com.stepanov.democonverter.dto.CurrencyDto;
import com.stepanov.democonverter.dto.CurrencyExchangeDto;
import com.stepanov.democonverter.entities.Currency;
import com.stepanov.democonverter.entities.CurrencyExchange;
import com.stepanov.democonverter.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public CurrencyDto toCurrencyDto(Currency currency) {
        return CurrencyDto.builder()
                .Identity(currency.getIdentity())
                .charCode(currency.getCharCode())
                .name(currency.getName())
                .nominal(currency.getNominal())
                .value(currency.getValue())
                .loadDate(currency.getLoadDate())
                .build();
    }
    @Override
    public List<CurrencyDto> toCurrencyDtoList(List<Currency> currencyList) {
        return currencyList.stream().map(this::toCurrencyDto).collect(Collectors.toList());
    }
}

