package br.com.project.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import br.com.project.currencyconverter.model.Currency;
import br.com.project.currencyconverter.repository.CurrencyRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CurrencyService {

    public CurrencyService(){};


    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> findAll(){
        return currencyRepository.findAll();
    }

    public Currency save(Currency currency){
        return currencyRepository.save(currency);
    }
    
}