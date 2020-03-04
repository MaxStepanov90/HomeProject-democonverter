package com.stepanov.democonverter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyDto {
    private String Identity;
    private String charCode;
    private String name;
    private int nominal;
    private double value;
    private LocalDate loadDate;
}
