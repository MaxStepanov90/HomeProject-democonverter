package com.stepanov.democonverter.dto;

import com.stepanov.democonverter.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyExchangeDto {
    private Long id;
    private String sourceCharCode;
    private String targetCharCode;
    private String sourceName;
    private String targetName;
    private double sourceCount;
    private double targetCount;
    private LocalDate creationDate;
    private User user;
}
