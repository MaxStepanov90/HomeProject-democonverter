package com.stepanov.democonverter.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "public", name = "currency_exchange")
public class CurrencyExchange {
    @Id
    @GeneratedValue
    private Long id;
    private String sourceCharCode;
    private String targetCharCode;
    private String sourceName;
    private String targetName;
    private double sourceCount;
    private double targetCount;
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
