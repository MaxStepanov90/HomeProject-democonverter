package com.stepanov.democonverter.service;

import com.stepanov.democonverter.dto.CurrencyExchangeDto;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface CurrencyExchangeService {

    CurrencyExchangeDto createCurrencyExchange(String sourceName, String targetName, double sourceCount, String login)
            throws SAXException, ParserConfigurationException, ParseException, IOException;

    List<CurrencyExchangeDto> getHistoryForCurrentUser(String login);
}
