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

@Service
@AllArgsConstructor
public class CurrencyService {

    public CurrencyService(){};

    private static Logger logger = LoggerFactory.getLogger(CurrencyconverterApplication.class);



    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> findAll() throws NullPointerException, SQLDataException, DataAccessException, Exception{
        logger.info("CurrencyService.findAll - Consultando conversões");
        return currencyRepository.findAll();
    }

    public Currency save(Currency currency) throws NullPointerException, SQLDataException, DataAccessException, Exception{
        logger.info("CurrencyService.save - Salvando valor convertido");
        return currencyRepository.save(currency);
    }
    
}