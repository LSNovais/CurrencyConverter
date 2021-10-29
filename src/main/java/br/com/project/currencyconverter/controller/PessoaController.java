package br.com.project.currencyconverter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.currencyconverter.model.TransactionHist;
import br.com.project.currencyconverter.service.TransactionHistService;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:8080/currencyConverter")
@RestController
@AllArgsConstructor
@RequestMapping("/**")
public class PessoaController {
    

    @Autowired
    private TransactionHistService transactionHistService;



    public PessoaController(){};
        
    @CrossOrigin
    @GetMapping(path = "/consult/transactions")
    public ResponseEntity<?> findAll(){
        List<TransactionHist> transactionHist = new ArrayList();
        transactionHist =  transactionHistService.findAll();
        return ResponseEntity.ok(transactionHist);
    }

}