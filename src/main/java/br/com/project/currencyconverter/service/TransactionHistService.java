/*
 * Class: TransactionHistService.java
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
import java.util.ArrayList;
import java.util.List;

import br.com.project.currencyconverter.CurrencyconverterApplication;
import br.com.project.currencyconverter.model.TransactionHist;
import br.com.project.currencyconverter.repository.TransactionHistRepository;
import lombok.AllArgsConstructor;



/**
 * @version 1.0
 * @author Lucas Novais dos Santos
 */
@Service
@AllArgsConstructor
public class TransactionHistService {

    public TransactionHistService(){};

    private static Logger logger = LoggerFactory.getLogger(CurrencyconverterApplication.class);


    @Autowired
    private TransactionHistRepository transactionHistRepository;

    /**
     * Localiza o histórico de transações realizadas pelo idUser {@code Integer}
     * @param idUser Id do usuário que terá seu histórico consultado
     * @return Lista de histórico do usuario {transactionHistListPerUser}
     * @since 11.0
     */
    public List<TransactionHist> findByUser(int idUser) throws NullPointerException, SQLDataException, DataAccessException, Exception{
        List<TransactionHist> transactionHistList = new ArrayList<>();
        List<TransactionHist> transactionHistListPerUser = new ArrayList<>();

        logger.info("transactionHistRepository.findByUser - Consultando históricos de conversão");
        transactionHistList =  transactionHistRepository.findAll();

        logger.info("transactionHistRepository.findByUser - Filtrando históricos de conversão por idUsuario");
        logger.info("a "+transactionHistList.size());

        for(int x = 0; x < transactionHistList.size(); x++){
            if(transactionHistList.get(x).getIdUser().getIdUser().equals(idUser))

                transactionHistListPerUser.add(transactionHistList.get(x));
                logger.info("transactionHistRepository.findByUser - transactionHistListPerUser"+transactionHistList.get(x));

        }

        return transactionHistListPerUser;
    }



    /**
     * Salvar o histórico da transação realizada pelo usuário {@code TransactionHist}
     * @param transactionHist Dados da transação que serão salvos
     * @return Histórico da transação salva {transactionHistRepository.save(transactionHist)}
     * @since 11.0
     */
    public TransactionHist save(TransactionHist transactionHist) throws NullPointerException, SQLDataException, DataAccessException, Exception{
        logger.info("transactionHistRepository.findAll - Salvando conversão");
        return transactionHistRepository.save(transactionHist);
    }
    
}