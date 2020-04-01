package com.stepanov.democonverter.service;

import com.stepanov.democonverter.dto.CurrencyExchangeDto;
import com.stepanov.democonverter.entities.Currency;
import com.stepanov.democonverter.entities.CurrencyExchange;
import com.stepanov.democonverter.mapper.CurrencyExchangeMapper;
import com.stepanov.democonverter.repositories.CurrencyExchangeRepository;
import com.stepanov.democonverter.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyServiceImpl currencyService;
    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final UserServiceImpl userService;
    private DateUtils dateUtils;

    @Autowired
    public CurrencyExchangeServiceImpl(CurrencyServiceImpl currencyService, CurrencyExchangeRepository currencyExchangeRepository,
                                       UserServiceImpl userService, DateUtils dateUtils) {
        this.currencyService = currencyService;
        this.currencyExchangeRepository = currencyExchangeRepository;
        this.userService = userService;
        this.dateUtils = dateUtils;
    }

    @Override
    public List<CurrencyExchangeDto> getHistoryForCurrentUser(String login) {
        List<CurrencyExchange> currencyExchangeList = currencyExchangeRepository.findByUserLogin(login);
        log.info("Get HistoryForCurrencyUser by UserLogin: {}", login);
        return CurrencyExchangeMapper.CURRENCY_EXCHANGE_MAPPER.fromCurrencyExchangeList(currencyExchangeList);
    }

    @Override
    public CurrencyExchangeDto createCurrencyExchange(String sourceName, String targetName, double sourceCount, String login) {
        dateUtils.checkDateOfLoadCurrency(currencyService.getLoadDateOfCurrency(sourceName));
        CurrencyExchange newCurrencyExchange = getNewCurrencyExchange(sourceName, targetName, sourceCount, login);
        log.info("newCurrencyExchange: {}", newCurrencyExchange);
        currencyExchangeRepository.save(newCurrencyExchange);
        return CurrencyExchangeMapper.CURRENCY_EXCHANGE_MAPPER.fromCurrencyExchange(newCurrencyExchange);
    }

    @Override
    public CurrencyExchange getNewCurrencyExchange(String sourceName, String targetName, double sourceCount, String login) {
        Currency firstCurrency = currencyService.getCurrencyByCharCode(sourceName);
        Currency secondCurrency = currencyService.getCurrencyByCharCode(targetName);
        return CurrencyExchange.builder()
                .sourceCharCode(firstCurrency.getCharCode())
                .targetCharCode(secondCurrency.getCharCode())
                .sourceName(firstCurrency.getName())
                .targetName(secondCurrency.getName())
                .sourceCount(sourceCount)
                .targetCount(getTargetCount(firstCurrency, secondCurrency, sourceCount))
                .creationDate(LocalDate.now())
                .user(userService.findByLogin(login))
                .build();
    }

    @Override
    public double getTargetCount(Currency firstCurrency, Currency secondCurrency, double sourceCount) {

        double convertCurs = ((firstCurrency.getNominal()) * (firstCurrency.getValue()) /
                ((secondCurrency.getNominal()) * (secondCurrency.getValue())));
        log.info("ConvertCurs: {}", convertCurs);
        return Math.round((sourceCount * convertCurs) * 100) / 100.0d;
    }
}

