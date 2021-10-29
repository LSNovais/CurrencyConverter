package br.com.project.currencyconverter.repository;


import br.com.project.currencyconverter.model.Currency;
import br.com.project.currencyconverter.repository.CurrencyRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    
    
}