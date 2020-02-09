package com.stepanov.democonverter.repositories;

import com.stepanov.democonverter.entities.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {

    List<CurrencyExchange> findByUserLogin(String login);
}
