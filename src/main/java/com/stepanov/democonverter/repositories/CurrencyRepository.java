package com.stepanov.democonverter.repositories;

import com.stepanov.democonverter.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,String> {
    Currency findByCharCode(String charCode);
}
