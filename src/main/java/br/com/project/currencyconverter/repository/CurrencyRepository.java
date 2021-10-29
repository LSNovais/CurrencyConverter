package br.com.project.currencyconverter.repository;


import br.com.project.currencyconverter.model.Currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Repository
@EnableJpaRepositories
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    
    
}