package br.com.project.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<TransactionHist> findByUser(int idUsuario){
        List<TransactionHist> transactionHistList = new ArrayList<>();
        List<TransactionHist> transactionHistListPerUser = new ArrayList<>();

        transactionHistList =  transactionHistRepository.findAll();

        for(int x = 0; x < transactionHistList.size(); x++){
            if(transactionHistList.get(x).getIdUsuario().getIdUsuario().equals(idUsuario))
                transactionHistListPerUser.add(transactionHistList.get(x));
        }

        return transactionHistListPerUser;
    }

    public TransactionHist save(TransactionHist transactionHist){
        return transactionHistRepository.save(transactionHist);
    }
    
}