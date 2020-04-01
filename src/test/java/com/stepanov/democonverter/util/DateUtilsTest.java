package com.stepanov.democonverter.util;

import com.stepanov.democonverter.data.DataBaseInit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
class DateUtilsTest {
    @InjectMocks
    private DateUtils dateUtils;
    @Mock
    private DataBaseInit dataBaseInit;

    @Test
    void checkDateOfLoadCurrency_Should_Call_PostConstructMethod() throws SAXException, ParserConfigurationException, ParseException, IOException {
        dateUtils.checkDateOfLoadCurrency(LocalDate.parse("2000-10-10"));
        verify(dataBaseInit, times(1)).postConstruct();
    }

    @Test
    void checkDateOfLoadCurrency_Should_Not_Call_PostConstructMethod() throws SAXException, ParserConfigurationException, ParseException, IOException {
        dateUtils.checkDateOfLoadCurrency(LocalDate.now());
        verify(dataBaseInit, times(0)).postConstruct();
    }

    @Test
    public void beforeToday_Should_Return_True() {
        assertTrue(dateUtils.beforeToday(LocalDate.parse("2000-10-10")));
    }

    @Test
    public void beforeTodayTest_Should_Return_False() {
        assertFalse(dateUtils.beforeToday(LocalDate.now()));
    }
}
