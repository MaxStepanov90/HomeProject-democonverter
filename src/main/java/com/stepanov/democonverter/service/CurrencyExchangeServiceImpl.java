package com.stepanov.democonverter.service;

import com.stepanov.democonverter.data.DataBaseInit;
import com.stepanov.democonverter.dto.CurrencyExchangeDto;
import com.stepanov.democonverter.entities.Currency;
import com.stepanov.democonverter.entities.CurrencyExchange;
import com.stepanov.democonverter.mapper.CurrencyExchangeMapper;
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
    public List<CurrencyExchangeDto> getHistoryForCurrentUser(String login) {
        List<CurrencyExchange> currencyExchangeDtoList = currencyExchangeRepository.findByUserLogin(login);
        return CurrencyExchangeMapper.CURRENCY_EXCHANGE_MAPPER.fromCurrencyExchangeList(currencyExchangeDtoList);
    }

    @Override
    public CurrencyExchangeDto createCurrencyExchange(String sourceName, String targetName, double sourceCount, String login) {

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
            newCurrencyExchange = getNewCurrencyExchange(sourceName, targetName, sourceCount, login);
            currencyExchangeRepository.save(newCurrencyExchange);
        }
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
                .user(userRepository.findByLogin(login))
                .build();
    }

    @Override
    public double getTargetCount(Currency firstCurrency, Currency secondCurrency, double sourceCount) {

        double convertCurs = ((firstCurrency.getNominal()) * (firstCurrency.getValue()) /
                ((secondCurrency.getNominal()) * (secondCurrency.getValue())));
        return Math.round((sourceCount * convertCurs) * 100) / 100.0d;
    }
}

