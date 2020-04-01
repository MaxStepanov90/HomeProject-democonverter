package com.stepanov.democonverter.service;

import com.stepanov.democonverter.dto.CurrencyExchangeDto;
import com.stepanov.democonverter.entities.CurrencyExchange;
import com.stepanov.democonverter.repositories.CurrencyExchangeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
class CurrencyExchangeServiceImplTest {

    @Mock
    private CurrencyExchangeRepository currencyExchangeRepository;

    @InjectMocks
    private CurrencyExchangeServiceImpl currencyExchangeService;

    @Test
    public void getHistoryForCurrentUser_Should_Return_List_Of_CurrencyExchange() {
        CurrencyExchange currencyExchange = new CurrencyExchange();
        when(currencyExchangeRepository.findByUserLogin("Max")).thenReturn(Collections.singletonList(currencyExchange));
        List<CurrencyExchangeDto> currencyExchangeList = currencyExchangeService.getHistoryForCurrentUser("Max");
        assertNotNull(currencyExchangeList);
        assertEquals(currencyExchangeList.size(), 1);
        verify(currencyExchangeRepository, times(1)).findByUserLogin("Max");
    }

    @Test
    public void getHistoryForCurrentUser_TestCaptor() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        currencyExchangeService.getHistoryForCurrentUser("Max");
        verify(currencyExchangeRepository).findByUserLogin(captor.capture());
        String argument = captor.getValue();
        assertEquals(argument, "Max");
    }

    @Test
    public void getHistoryForCurrentUser_Should_Return_Null() {
        when(currencyExchangeRepository.findByUserLogin("ooo")).thenReturn(null);
        assertNull(currencyExchangeService.getHistoryForCurrentUser("ooo"));
        verify(currencyExchangeRepository, times(1)).findByUserLogin("ooo");
    }
}
