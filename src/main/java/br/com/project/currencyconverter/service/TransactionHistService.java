package br.com.project.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import br.com.project.currencyconverter.CurrencyconverterApplication;
import br.com.project.currencyconverter.model.TransactionHist;
import br.com.project.currencyconverter.repository.TransactionHistRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionHistService {

    public TransactionHistService(){};

    private static Logger logger = LoggerFactory.getLogger(CurrencyconverterApplication.class);


    @Autowired
    private TransactionHistRepository transactionHistRepository;

    public List<TransactionHist> findByUser(int idUsuario) throws NullPointerException, SQLDataException, DataAccessException, Exception{
        List<TransactionHist> transactionHistList = new ArrayList<>();
        List<TransactionHist> transactionHistListPerUser = new ArrayList<>();

        logger.info("transactionHistRepository.findByUser - Consultando históricos de conversão");
        transactionHistList =  transactionHistRepository.findAll();

        logger.info("transactionHistRepository.findByUser - Filtrando históricos de conversão por idUsuario");
        for(int x = 0; x < transactionHistList.size(); x++){
            if(transactionHistList.get(x).getIdUsuario().getIdUsuario().equals(idUsuario))
                transactionHistListPerUser.add(transactionHistList.get(x));
        }

        return transactionHistListPerUser;
    }

    public TransactionHist save(TransactionHist transactionHist) throws NullPointerException, SQLDataException, DataAccessException, Exception{
        logger.info("transactionHistRepository.findAll - Salvando conversão");
        return transactionHistRepository.save(transactionHist);
    }
    
}