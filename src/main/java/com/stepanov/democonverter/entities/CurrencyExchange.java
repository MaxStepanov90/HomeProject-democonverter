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

    public CurrencyExchange(CurrencyExchangeBuilder builder) {
        this.id = builder.id;
        this.sourceCharCode = builder.sourceCharCode;
        this.targetCharCode = builder.targetCharCode;
        this.sourceName = builder.sourceName;
        this.targetName = builder.targetName;
        this.sourceCount = builder.sourceCount;
        this.targetCount = builder.targetCount;
        this.creationDate = builder.creationDate;
        this.user = builder.user;
    }

    public Long getId() {
        return id;
    }

    public String getSourceCharCode() {
        return sourceCharCode;
    }

    public String getTargetCharCode() {
        return targetCharCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public double getSourceCount() {
        return sourceCount;
    }

    public double getTargetCount() {
        return targetCount;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public User getUser() {
        return user;
    }

    public static class CurrencyExchangeBuilder {

        private Long id;
        private String sourceCharCode;
        private String targetCharCode;
        private String sourceName;
        private String targetName;
        private double sourceCount;
        private double targetCount;
        private LocalDate creationDate;
        private User user;

        public void setId(Long id) {
            this.id = id;
        }

        public CurrencyExchangeBuilder setSourceCharCode(String sourceCharCode) {
            this.sourceCharCode = sourceCharCode;
            return this;
        }

        public CurrencyExchangeBuilder setTargetCharCode(String targetCharCode) {
            this.targetCharCode = targetCharCode;
            return this;
        }

        public CurrencyExchangeBuilder setSourceName(String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        public CurrencyExchangeBuilder setTargetName(String targetName) {
            this.targetName = targetName;
            return this;
        }

        public CurrencyExchangeBuilder setSourceCount(double sourceCount) {
            this.sourceCount = sourceCount;
            return this;
        }

        public CurrencyExchangeBuilder setTargetCount(double targetCount) {
            this.targetCount = targetCount;
            return this;
        }

        public CurrencyExchangeBuilder setCreationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public CurrencyExchangeBuilder setUser(User user) {
            this.user = user;
            return this;
        }
        public CurrencyExchange build(){
            return new CurrencyExchange(this);
        }
    }
}
