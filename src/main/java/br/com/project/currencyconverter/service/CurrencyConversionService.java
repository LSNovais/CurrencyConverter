package br.com.project.currencyconverter.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import br.com.project.currencyconverter.model.Currency;

@Service
public class CurrencyConversionService {

    private List<String> currencyList;
    private List<Double> valueConversion;
    private int count;
    private Double txConversionOrigin;
    private Double txConversionDestiny;


    public CurrencyConversionService(){
        currencyList = new ArrayList();
        valueConversion = new ArrayList();

        currencyList.add("EUR");
        currencyList.add("USD");
        currencyList.add("BRL");
        currencyList.add("JPY");

        valueConversion.add(1.0);       //EUR
        valueConversion.add(1.15629);   //USD
        valueConversion.add(6.518041);  //BRL
        valueConversion.add(131.893949);//JPY

    };


    public Double conversion(Currency currency){
        return currency.getValorOrigem() * taxConversion(currency.getMoedaOrigem(), currency.getMoedaDestino());
    }

    public Double taxConversion(String currencyOrigin, String currencyDestiny){
        count = 0;
        txConversionOrigin = 0.0;
        txConversionDestiny = 0.0;

        for(String item : currencyList){
            if ( currencyOrigin.equals( item ) )
            txConversionOrigin = ( valueConversion.get(0) / valueConversion.get(count) );
            count ++;
        }

        count = 0;
        for(String item : currencyList){
            if ( currencyDestiny.equals( item ) )
            txConversionDestiny = valueConversion.get(count);
            count ++;
        }




        return txConversionOrigin * txConversionDestiny;
    }
    
}