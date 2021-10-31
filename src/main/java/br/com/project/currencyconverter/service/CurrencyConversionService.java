package br.com.project.currencyconverter.service;

import org.springframework.stereotype.Service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.project.currencyconverter.CurrencyconverterApplication;
import br.com.project.currencyconverter.dto.CurrencyConversionApiDTO;
import br.com.project.currencyconverter.model.Currency;

@Service
public class CurrencyConversionService {

    private Double txConversionOrigin;
    private Double txConversionDestiny;
    private StringBuffer responseStr = new StringBuffer();

    final String URL = "http://api.exchangeratesapi.io/v1/latest?access_key=1719e991e5f2114c4f9e97d07f062246&symbols=";
    final String EUR = "EUR,";

    private static Logger logger = LoggerFactory.getLogger(CurrencyconverterApplication.class);

    public CurrencyConversionService() {
    };


    public Double conversion(Currency currency)
            throws NullPointerException, ArithmeticException, IllegalArgumentException, Exception {
        logger.info("CurrencyConversionService.conversion - Preparando para gerar o valor convertido");
        return around( currency.getValorOrigem() * taxConversion(currency.getMoedaOrigem(), currency.getMoedaDestino()) );
    }

    private double around(double around) {
        return Math.round(around * 100.0)/100.0;
     }


    public Double taxConversion(String currencyOrigin, String currencyDestiny)
            throws NullPointerException, ArithmeticException, IllegalArgumentException, Exception {
        logger.info("CurrencyConversionService.taxConversion - Preparando para gerar a taxa de conversão.");

        txConversionOrigin = ReturnAPIExternal(currencyOrigin, true);
        txConversionDestiny = ReturnAPIExternal(currencyDestiny, false);
        
        logger.info("CurrencyConversionService.taxConversion - Taxa de conversão gerada - "
                + txConversionOrigin * txConversionDestiny);
        return txConversionOrigin * txConversionDestiny;
    }
    

    public Double ReturnAPIExternal(String currency, boolean isOrigin) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CurrencyConversionApiDTO currencyConversionDTO = new CurrencyConversionApiDTO();
        String responseStr = null;

            if(isOrigin){
                responseStr = this.realizarRequest(URL.concat(EUR).concat(currency), null, "GET", "application/json", "application/json",false);
            }else{
                responseStr = this.realizarRequest(URL.concat(currency), null, "GET", "application/json", "application/json",false);
            }

            if(responseStr != null){
                mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    			currencyConversionDTO = mapper.readValue(responseStr, CurrencyConversionApiDTO.class);
            }

        logger.info("CurrencyConversionDTO.ReturnAPIExternal - Iniciada consulta pela API externa para coletar o valor de conversão atual da moeda "+currency
        +"\n Timestamp: "+currencyConversionDTO.getTimestamp()
        +"\n Currency: "+returnRateByCurrency(currency, currencyConversionDTO, isOrigin)
        +"\n Success: "+currencyConversionDTO.getSuccess()
        +"\n Date: "+currencyConversionDTO.getDate());

        return returnRateByCurrency(currency, currencyConversionDTO, isOrigin);
    }


    private Double returnRateByCurrency(String currency, CurrencyConversionApiDTO values, boolean isOrigin){

        switch(currency){
            case "EUR": 
                return values.getRates().getEUR();
            case "BRL": 
                return isOrigin ? ( values.getRates().getEUR() / values.getRates().getBRL() ) : values.getRates().getBRL();
            case "USD": 
                return isOrigin ? ( values.getRates().getEUR() / values.getRates().getUSD() ) : values.getRates().getUSD();
            case "JPY": 
                return isOrigin ? ( values.getRates().getEUR() / values.getRates().getJPY() ) : values.getRates().getJPY();
            default:
                return 0.0;
        }

    }



    private String realizarRequest(String urlRequest, String body, 
    String method, String contentType, String accepts, Boolean doOutput){
        OutputStream os = null;
        HttpURLConnection conn = null;
        BufferedReader in = null;

        try{    		
            
            URL url = new URL(urlRequest);
            
            conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("GET");
            if(contentType != null){
                conn.setRequestProperty("Content-Type", contentType);
            }
            if(accepts != null){
                conn.setRequestProperty("Accept", accepts);
            }
            
            conn.setDoOutput(doOutput);
            if(doOutput){
                os = conn.getOutputStream();
                
                os.write(body.getBytes());
                    
                
            }
            
            
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            
            String inputLine;
            responseStr = new StringBuffer();
            
            while((inputLine = in.readLine()) != null){
                responseStr.append(inputLine);
            }
            
            in.close();
            
            return responseStr.toString();    		
            
        }catch(IOException ioe){
            logger.error("IOException: " + ioe.getMessage());
            return responseStr.toString();    		
        }catch(Exception ex){
            logger.error("IOException: " + ex.getMessage());
            return responseStr.toString();    		
        }
        }

    
}