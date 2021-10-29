package br.com.project.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import br.com.project.currencyconverter.model.TransactionHist;
import br.com.project.currencyconverter.repository.TransactionHistRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionHistService {

    public TransactionHistService(){};

    @Autowired
    private TransactionHistRepository transactionHistRepository;

    public List<TransactionHist> findAll(){
        return transactionHistRepository.findAll();
    }
    
}