/*
 * Class: CurrencyService.java
 * Created: 29/10/2021
 * Rights Reserved: Jaya
 */  

package br.com.project.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLDataException;
import java.util.List;

import br.com.project.currencyconverter.CurrencyconverterApplication;
import br.com.project.currencyconverter.model.Currency;
import br.com.project.currencyconverter.repository.CurrencyRepository;
import lombok.AllArgsConstructor;



/**
 * @version 1.0
 * @author Lucas Novais dos Santos
 */
@Service
@AllArgsConstructor
public class CurrencyService {

    public CurrencyService(){};

    private static Logger logger = LoggerFactory.getLogger(CurrencyconverterApplication.class);



    @Autowired
    private CurrencyRepository currencyRepository;


    
    /**
     * Realiza a consulta no banco das transações realizadas
     * @return Uma lista de todas as consultas realizadas {currencyRepository.findAll()}
     * @since 11.0
     */
    public List<Currency> findAll() throws NullPointerException, SQLDataException, DataAccessException, Exception{
        logger.info("CurrencyService.findAll - Consultando conversões");
        return currencyRepository.findAll();
    }


    /**
     * Salva o valor de conversão do usuário
     * @param currency Possui os valores que serão salvos no banco de dados
     * @return Valores de conversão salvos {currencyRepository.save(currency)}
     * @since 11.0
     */
    public Currency save(Currency currency) throws NullPointerException, SQLDataException, DataAccessException, Exception{
        logger.info("CurrencyService.save - Salvando valor convertido");
        return currencyRepository.save(currency);
    }
    
}