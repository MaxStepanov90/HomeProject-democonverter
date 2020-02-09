package com.stepanov.democonverter.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "usr")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String password;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurrencyExchange> exchangeList;

    public User() {
    }

    public User(Long id, String login, String password,List<CurrencyExchange> exchangeList) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.exchangeList = exchangeList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CurrencyExchange> getExchangeList() {
        return exchangeList;
    }

    public void setExchangeList(List<CurrencyExchange> exchangeList) {
        this.exchangeList = exchangeList;
    }
}
