package com.stepanov.democonverter.util;

import com.stepanov.democonverter.data.DataBaseInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

@Slf4j
@Component
public class DateUtils {

    public void CheckDateOfLoadCurrency(LocalDate currencyLoadDate) {

        if (beforeToday(currencyLoadDate)) {
            try {
                DataBaseInit dataBaseInit = new DataBaseInit();
                dataBaseInit.postConstruct();
                log.info("CurrencyLoadDate,{} is before: {}", currencyLoadDate, LocalDate.now());
            } catch (IOException | SAXException | ParserConfigurationException | ParseException e) {
                e.printStackTrace();
                log.error("Error parsing date", e);
            }
        }
        log.info("CurrencyLoadDate: {} is equal LocalDate.now(): {}",currencyLoadDate,LocalDate.now());
    }

    public boolean beforeToday(LocalDate currencyLoadDate) {

        return currencyLoadDate.isBefore(LocalDate.now());
    }

}
