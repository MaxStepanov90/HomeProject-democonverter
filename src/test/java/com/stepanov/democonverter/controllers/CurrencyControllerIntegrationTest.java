package com.stepanov.democonverter.controllers;

import com.stepanov.democonverter.dto.CurrencyExchangeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WithUserDetails("Max")
@Sql(value = {"/create-user-before.sql", "/create-currency-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql", "/create-currency-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CurrencyControllerIntegrationTest {

    @Autowired
    private CurrencyController currencyController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(currencyController).isNotNull();
    }

    @Test
    public void getCurrenciesMethodTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(model().attribute("historycurrencyexchange", hasSize(2)))
                .andExpect(model().attribute("currencies", hasSize(5)))
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(view().name("convert"));
    }

    @Test
    public void getCurrencyExchangeMethodTest() throws Exception {
        mockMvc.perform(post("/convert")
                .param("sourceName", "USD")
                .param("targetName", "EUR")
                .param("sourceCount", "11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(model().attribute("historycurrencyexchange", hasSize(3)))
                .andExpect(model().attribute("currencyexchange", instanceOf(CurrencyExchangeDto.class)))
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(view().name("result"));
    }
}