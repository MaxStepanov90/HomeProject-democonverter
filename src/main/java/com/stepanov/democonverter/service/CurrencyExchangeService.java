package com.stepanov.democonverter.service;

import com.stepanov.democonverter.entities.CurrencyExchange;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface CurrencyExchangeService {

    CurrencyExchange createCurrencyExchange(String sourceName, String targetName, double sourceCount, String login) throws SAXException, ParserConfigurationException, ParseException, IOException;

    List<CurrencyExchange> getHistoryForCurrentUser(String login);

}
