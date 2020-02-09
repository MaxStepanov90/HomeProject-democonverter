package com.stepanov.democonverter.entities;

import javax.persistence.*;
import java.time.LocalDate;

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


    public CurrencyExchange() {
    }

    public CurrencyExchange(String sourceCharCode, String targetCharCod, String sourceName, String targetName, double sourceCount,
                            double targetCount, LocalDate creationDate, User user) {

        this.sourceCharCode = sourceCharCode;
        this.targetCharCode = targetCharCod;
        this.sourceName = sourceName;
        this.targetName = targetName;
        this.sourceCount = sourceCount;
        this.targetCount = targetCount;
        this.creationDate = creationDate;
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceCharCode() {
        return sourceCharCode;
    }

    public void setSourceCharCode(String sourceCharCode) {
        this.sourceCharCode = sourceCharCode;
    }

    public String getTargetCharCode() {
        return targetCharCode;
    }

    public void setTargetCharCode(String targetCharCode) {
        this.targetCharCode = targetCharCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public double getSourceCount() {
        return sourceCount;
    }

    public void setSourceCount(double sourceCount) {
        this.sourceCount = sourceCount;
    }

    public double getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(double targetCount) {
        this.targetCount = targetCount;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
