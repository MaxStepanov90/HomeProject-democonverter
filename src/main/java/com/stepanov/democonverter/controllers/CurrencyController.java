package com.stepanov.democonverter.controllers;

import com.stepanov.democonverter.dto.CurrencyExchangeDto;
import com.stepanov.democonverter.entities.Currency;
import com.stepanov.democonverter.entities.CurrencyExchange;
import com.stepanov.democonverter.service.CurrencyExchangeServiceImpl;
import com.stepanov.democonverter.service.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CurrencyController {

    private final CurrencyServiceImpl currencyService;
    private final CurrencyExchangeServiceImpl currencyExchangeService;

    @Autowired
    public CurrencyController(CurrencyServiceImpl currencyService, CurrencyExchangeServiceImpl currencyExchangeService) {
        this.currencyService = currencyService;
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping("/")
    public String getCurrencies(Model model, Principal user) {
        List<CurrencyExchange> currencyExchangeList = currencyExchangeService.getHistoryForCurrentUser(user.getName());
        if (!currencyExchangeList.isEmpty()) {
            model.addAttribute("historycurrencyexchange", currencyExchangeService.toCurrencyExchangeDtoList(currencyExchangeList));
        }
        List<Currency>currencyList = currencyService.getCurrencies();
        model.addAttribute("currencies", currencyService.toCurrencyDtoList(currencyList));
        return "convert";
    }

    @PostMapping("/convert")
    public String getCurrencyExchange(Model model, Principal user,
                                      @RequestParam String sourceName,
                                      @RequestParam String targetName,
                                      @RequestParam double sourceCount) {
        String userLogin = user.getName();
        CurrencyExchange currencyExchange = currencyExchangeService.createCurrencyExchange(sourceName, targetName, sourceCount, userLogin);
        List<CurrencyExchange> currencyExchangeList = currencyExchangeService.getHistoryForCurrentUser(userLogin);
        model.addAttribute("currencyexchanges", currencyExchangeService.toCurrencyExchangeDto(currencyExchange));
        model.addAttribute("historycurrencyexchange",currencyExchangeService.toCurrencyExchangeDtoList(currencyExchangeList) );
        return "result";
    }


}