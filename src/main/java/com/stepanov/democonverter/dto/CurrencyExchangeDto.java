package com.stepanov.democonverter.dto;

import com.stepanov.democonverter.entities.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "поле не может быть пустым")
    private double sourceCount;
    private double targetCount;
    private LocalDate creationDate;
    @ToString.Exclude
    private User user;
}
