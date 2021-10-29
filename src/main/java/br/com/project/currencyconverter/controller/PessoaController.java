package br.com.project.currencyconverter.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.currencyconverter.model.Currency;
import br.com.project.currencyconverter.model.TransactionHist;
import br.com.project.currencyconverter.service.CurrencyService;
import br.com.project.currencyconverter.service.TransactionHistService;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:8080/currencyConverter")
@RestController
@AllArgsConstructor
@RequestMapping("/**")
public class PessoaController {
    

    @Autowired
    private TransactionHistService transactionHistService;
    
    @Autowired
    private CurrencyService currencyService;





    public PessoaController(){};
        
    @CrossOrigin
    @GetMapping(path = "/consult/transactions")
    public ResponseEntity<?> findAll(){
        List<TransactionHist> transactionHist = new ArrayList();
        transactionHist =  transactionHistService.findAll();
        return ResponseEntity.ok(transactionHist);
    }

    public ResponseEntity<?> converterCurrency(Currency currency){
        Currency currencyAdded = new Currency();
        currencyAdded = currencyService.save(currency);
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyAdded);
    }


    @CrossOrigin
    @GetMapping(path = "/convert/{moedaOrigem}/{valorOrigem}/{moedaDestino}")
    public ResponseEntity<?> converterTransactionHist(@PathVariable String moedaOrigem, @PathVariable Double valorOrigem, @PathVariable String moedaDestino){
        TransactionHist transactionHist = new TransactionHist();
        TransactionHist transactionHistAdded = new TransactionHist();
        Currency currency = new Currency();

        currency.setMoedaOrigem(moedaOrigem);
        currency.setValorOrigem(valorOrigem);
        currency.setMoedaDestino(moedaDestino);
        currency.setDataHora("teste");
        currency.setIdUsuario(2);
        currency.setTxConvUtil(100.0);

        if(converterCurrency(currency).getStatusCode().equals(HttpStatus.CREATED)){
            transactionHist.setId(2);
            transactionHist.setIdUsuario(currency);
            transactionHist.setValorDestino(200.0);
        }else{
            return converterCurrency(currency);
        }
        
        transactionHistAdded = transactionHistService.save(transactionHist);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionHistAdded);
    }

}