package com.stepanov.democonverter.controllers;

import com.stepanov.democonverter.entities.CurrencyExchange;
import com.stepanov.democonverter.service.CurrencyExchangeServiceImpl;
import com.stepanov.democonverter.service.CurrencyServiceImpl;
import com.stepanov.democonverter.service.UserServiceImpl;
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
    private final UserServiceImpl userService;

    @Autowired
    public CurrencyController(CurrencyServiceImpl currencyService, CurrencyExchangeServiceImpl currencyExchangeService,
                              UserServiceImpl userService) {
        this.currencyService = currencyService;
        this.currencyExchangeService = currencyExchangeService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getCurrencies(Model model, Principal user) {
        List<CurrencyExchange> currencyExchangeList = currencyExchangeService.getHistoryForCurrentUser(user.getName());
        if (!currencyExchangeList.isEmpty()) {
            model.addAttribute("historycurrencyexchange", currencyExchangeList);
        }
        model.addAttribute("currencies", currencyService.getCurrencies());
        return "convert";
    }

    @PostMapping("/convert")
    public String getCurrencyExchange(Model model, Principal user,
                                      @RequestParam String sourceName,
                                      @RequestParam String targetName,
                                      @RequestParam double sourceCount) {
        String userLogin = user.getName();
        CurrencyExchange currencyExchange = currencyExchangeService.createCurrencyExchange(sourceName, targetName, sourceCount, userLogin);
        model.addAttribute("currencyexchanges", currencyExchange);
        model.addAttribute("historycurrencyexchange", currencyExchangeService.getHistoryForCurrentUser(userLogin));
        return "result";
    }


}