package com.stepanov.democonverter.service;

import com.stepanov.democonverter.entities.Currency;
import com.stepanov.democonverter.entities.CurrencyExchange;
import com.stepanov.democonverter.repositories.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyServiceImpl currencyService;
    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final UserServiceImpl userService;

    @Autowired
    public CurrencyExchangeServiceImpl(CurrencyServiceImpl currencyService, CurrencyExchangeRepository currencyExchangeRepository,
                                       UserServiceImpl userService) {
        this.currencyService = currencyService;
        this.currencyExchangeRepository = currencyExchangeRepository;
        this.userService = userService;
    }


    @Override
    public List<CurrencyExchange> getHistoryForCurrentUser(String login) {
        return currencyExchangeRepository.findByUserLogin(login);

    }

    @Override
    public CurrencyExchange createCurrencyExchange(String sourceName, String targetName, double sourceCount, String login) {

        Currency firstCurrency = currencyService.getCurrencyByCharCode(sourceName);
        Currency secondCurrency = currencyService.getCurrencyByCharCode(targetName);

        String firstCharCode = firstCurrency.getCharCode();
        String secondCharCode = secondCurrency.getCharCode();
        String firstName = firstCurrency.getName();
        String secondName = secondCurrency.getName();
        double firstCurrencyValue = firstCurrency.getValue();
        int firstCurrencyNominal = firstCurrency.getNominal();
        double secondCurrencyValue = secondCurrency.getValue();
        int secondCurrencyNominal = secondCurrency.getNominal();
        LocalDate creationDate = LocalDate.now();

        double convertCurs = (firstCurrencyNominal * firstCurrencyValue) / (secondCurrencyNominal * secondCurrencyValue);
        double targetCount = Math.round((convertCurs * sourceCount) * 100) / 100.0d;

        CurrencyExchange newCurrencyExchange = new CurrencyExchange();
        newCurrencyExchange.setSourceCharCode(firstCharCode);
        newCurrencyExchange.setTargetCharCode(secondCharCode);
        newCurrencyExchange.setSourceName(firstName);
        newCurrencyExchange.setTargetName(secondName);
        newCurrencyExchange.setSourceCount(sourceCount);
        newCurrencyExchange.setTargetCount(targetCount);
        newCurrencyExchange.setCreationDate(creationDate);
        newCurrencyExchange.setUserLogin(login);

        currencyExchangeRepository.save(newCurrencyExchange);

        return newCurrencyExchange;
    }
}
