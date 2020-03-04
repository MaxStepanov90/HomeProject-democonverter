package com.stepanov.democonverter.mapper;

import com.stepanov.democonverter.dto.CurrencyDto;
import com.stepanov.democonverter.entities.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CurrencyMapper {

    CurrencyMapper CURRENCY_MAPPER = Mappers.getMapper(CurrencyMapper.class);

    List<CurrencyDto> fromCurrencyList(List<Currency> currencyList);

}

