package com.stepanov.democonverter.mapper;

import com.stepanov.democonverter.dto.CurrencyExchangeDto;
import com.stepanov.democonverter.entities.CurrencyExchange;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CurrencyExchangeMapper {

    CurrencyExchangeMapper CURRENCY_EXCHANGE_MAPPER = Mappers.getMapper(CurrencyExchangeMapper.class);

    CurrencyExchangeDto fromCurrencyExchange(CurrencyExchange currencyExchange);

    List<CurrencyExchangeDto> fromCurrencyExchangeList(List<CurrencyExchange> currencyExchangeList);
}
