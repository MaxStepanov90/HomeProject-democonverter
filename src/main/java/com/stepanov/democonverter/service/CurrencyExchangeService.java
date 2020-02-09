package com.stepanov.democonverter.service;

import com.stepanov.democonverter.entities.CurrencyExchange;

import java.util.List;

public interface CurrencyExchangeService {

    CurrencyExchange createCurrencyExchange(String sourceName, String targetName, double sourceCount, String login);

    List<CurrencyExchange> getHistoryForCurrentUser(String login);

}
