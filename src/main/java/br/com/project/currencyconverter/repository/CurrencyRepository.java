/*
 * Class: CurrencyRepository.java
 * Created: 29/10/2021
 * Rights Reserved: Jaya
 */  

package br.com.project.currencyconverter.repository;


import br.com.project.currencyconverter.model.Currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * @version 1.0
 * @author Lucas Novais dos Santos
 */
@Repository
@EnableJpaRepositories
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    
    
}