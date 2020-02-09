package com.stepanov.democonverter.service;

import com.stepanov.democonverter.data.DataBaseInit;
import com.stepanov.democonverter.entities.Currency;
import com.stepanov.democonverter.entities.CurrencyExchange;
import com.stepanov.democonverter.repositories.CurrencyExchangeRepository;
import com.stepanov.democonverter.repositories.UserRepository;
import com.stepanov.democonverter.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyServiceImpl currencyService;
    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final UserRepository userRepository;

    @Autowired
    public CurrencyExchangeServiceImpl(CurrencyServiceImpl currencyService, CurrencyExchangeRepository currencyExchangeRepository,
                                       UserRepository userRepository) {
        this.currencyService = currencyService;
        this.currencyExchangeRepository = currencyExchangeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CurrencyExchange> getHistoryForCurrentUser(String login) {
        return currencyExchangeRepository.findByUserLogin(login);
    }

    @Override
    public CurrencyExchange createCurrencyExchange(String sourceName, String targetName, double sourceCount, String login) {

        CurrencyExchange newCurrencyExchange = new CurrencyExchange();
        LocalDate currencyLoadDate = (currencyService.getCurrencyByCharCode(sourceName)).getLoadDate();

        DataBaseInit dataBaseInit = new DataBaseInit();
        if (Utils.afterToday(currencyLoadDate)) {
            try {
                dataBaseInit.postConstruct();
            } catch (IOException | SAXException | ParserConfigurationException | ParseException e) {
                e.printStackTrace();
            }

        } else {
            Currency firstCurrency = currencyService.getCurrencyByCharCode(sourceName);
            Currency secondCurrency = currencyService.getCurrencyByCharCode(targetName);

            double convertCurs = ((firstCurrency.getNominal()) * (firstCurrency.getValue()) / ((secondCurrency.getNominal()) * (secondCurrency.getValue())));
            double targetCount = Math.round((convertCurs * sourceCount) * 100) / 100.0d;

            newCurrencyExchange.setSourceCharCode(firstCurrency.getCharCode());
            newCurrencyExchange.setTargetCharCode(secondCurrency.getCharCode());
            newCurrencyExchange.setSourceName(firstCurrency.getName());
            newCurrencyExchange.setTargetName(secondCurrency.getName());
            newCurrencyExchange.setSourceCount(sourceCount);
            newCurrencyExchange.setTargetCount(targetCount);
            newCurrencyExchange.setCreationDate(LocalDate.now());
            newCurrencyExchange.setUser(userRepository.findByLogin(login));

            currencyExchangeRepository.save(newCurrencyExchange);
        }
        return newCurrencyExchange;
    }
}

