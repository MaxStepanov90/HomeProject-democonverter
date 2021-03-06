package com.stepanov.democonverter.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Currency {

    @Id
    private String Identity;
    private String charCode;
    private String name;
    private int nominal;
    private double value;
    private LocalDate loadDate;
}

