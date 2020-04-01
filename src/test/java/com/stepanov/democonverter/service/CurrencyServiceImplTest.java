package com.stepanov.democonverter.service;

import com.stepanov.democonverter.entities.Currency;
import com.stepanov.democonverter.mapper.CurrencyMapper;
import com.stepanov.democonverter.repositories.CurrencyRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;
    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Test
    public void getCurrencyByCharCode_Should_Return_Currency() {
        Currency newCurrency = new Currency();
        newCurrency.setCharCode("EUR");
        when(currencyRepository.findByCharCode("EUR")).thenReturn(newCurrency);
        Currency currencyFromDb = currencyService.getCurrencyByCharCode("EUR");
        assertNotNull(currencyFromDb);
        assertEquals(currencyFromDb.getCharCode(), "EUR");
        verify(currencyRepository, times(1)).findByCharCode("EUR");
    }

    @Test
    public void getCurrencyByCharCode_TestCaptor() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        currencyService.getCurrencyByCharCode("EUR");
        verify(currencyRepository).findByCharCode(captor.capture());
        String argument = captor.getValue();
        assertEquals(argument, "EUR");
    }

    @Test
    public void getCurrencyByCharCode_Should_Return_Null_Currency() {
        when(currencyRepository.findByCharCode("XXX")).thenReturn(null);
        assertNull(currencyService.getCurrencyByCharCode("XXX"));
        verify(currencyRepository, times(1)).findByCharCode("XXX");

    }

    @Test(expected = Exception.class)
    public void getCurrencyByCharCode_Should_Throw_Exception() {
        when(currencyRepository.findByCharCode(anyString())).thenThrow(Exception.class);
        currencyService.getCurrencyByCharCode("USD");
        verify(currencyRepository, times(1)).findByCharCode("XXX");
    }

    @Test
    public void getCurrencies_Contain() {
        Currency newCurrency = new Currency();
        currencyRepository.save(newCurrency);
        List<Currency> currencies = CurrencyMapper.CURRENCY_MAPPER.fromCurrencyDtoList(currencyService.getCurrencies());
        assertThat(currencies.contains(newCurrency));
        verify(currencyRepository, times(1)).findAll();
    }
}