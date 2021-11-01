/*
 * Class: CurrencyConversionService.java
 * Created: 29/10/2021
 * Rights Reserved: Jaya 
 */  

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



/**
 * @version 1.0
 * @author Lucas Novais dos Santos
 */
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


    /**
     * Realiza a multiplicação do valor de origem {@code Double} com a taxa de conversão para a moeda destino {@code Double}
     * O valor retornado é o valor convertido para a nova moeda {@code Double}
     * Exemplo de retorno: de BRL para USD, retornará o valor em USD)
     * @param  currency possui os valores para conversão, moeda atual e a moeda destino
     * @return o valor {@code currency.getValueOrigin() * taxConversion(currency.getCurrencyOrigin(), currency.getCurrencyDestiny())} 
     * @since 11.0
     */
    public Double conversion(Currency currency)
            throws NullPointerException, ArithmeticException, IllegalArgumentException, Exception {
        logger.info("CurrencyConversionService.conversion - Preparando para gerar o valor convertido");
        return around( currency.getValueOrigin() * taxConversion(currency.getCurrencyOrigin(), currency.getCurrencyDestiny()) );
    }



    /**
     * Arredonda um valor {@code Double} para que tenha apenas duas casas decimais após a virgula
     * @param  around possui o valor a ser convertido
     * @return Math.round(around * 100.0)/100.0;
     * @since 11.0
     */
    private double around(double around) {
        return Math.round(around * 100.0)/100.0;
     }



    /**
     * Gera a taxa de conversão de uma moeda {@code Double} para a outra {@code Double}
     * O valor retornado é a taxa total da conversão {@code Double}
     * No decorrer do método taxConversion, é chamado o método ReturnAPIExternal 
     *  com uma moeda e um valor boolean para gerar a taxa de conversão dela
     * @param  currencyOrigin possui a moeda atual
     * @param  currencyDestiny possui a moeda destino
     * @return A taxa total da conversão atual {txConversionOrigin * txConversionDestiny}
     * @since 11.0
     */
    public Double taxConversion(String currencyOrigin, String currencyDestiny)
            throws NullPointerException, ArithmeticException, IllegalArgumentException, Exception {
        logger.info("CurrencyConversionService.taxConversion - Preparando para gerar a taxa de conversão.");

        txConversionOrigin = ReturnAPIExternal(currencyOrigin, true);
        txConversionDestiny = ReturnAPIExternal(currencyDestiny, false);
        
        logger.info("CurrencyConversionService.taxConversion - Taxa de conversão gerada - "
                + txConversionOrigin * txConversionDestiny);
        return txConversionOrigin * txConversionDestiny;
    }
    


    
    /**
     * Consulta a API externa http://api.exchangeratesapi.io/v1/latest para capturar a taxa 
     *  de conversão atual da moeda {@code Double}
     * O valor retornado é a taxa total da conversão da moeda {@code Double} recebida como parametro 
     * No decorrer do método ReturnAPIExternal, é chamado o método performRequest para consultar a API externa 
     * Caso seja o valor de origem da conversão, a consulta da API retorna a taxa da moeda e a taxa da moeda EUR
     * No retorno, será chamado o método returnRateByCurrency para que verifique se é a moeda de origem e retorne o valor pertinente{@code Double}
     * @param  currency moeda atual que terá sua taxa de conversão buscada pela API
     * @param  isOrigin verifica se a moeda é a origem ou a moeda de destino da conversão
     * @return A taxa da moeda atual {returnRateByCurrency(currency, currencyConversionDTO, isOrigin)}
     * @since 11.0
     */
    public Double ReturnAPIExternal(String currency, boolean isOrigin) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CurrencyConversionApiDTO currencyConversionDTO = new CurrencyConversionApiDTO();
        String responseStr = null;

            if(isOrigin){
                responseStr = this.performRequest(URL.concat(EUR).concat(currency), null, "GET", "application/json", "application/json",false);
            }else{
                responseStr = this.performRequest(URL.concat(currency), null, "GET", "application/json", "application/json",false);
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




    /**
     * Recebe os parametros currency {@code Currency.class}, values {@code Double} e o isOrigin {@code boolean}
     * O método returnRateByCurrency verifica se a moeda atual é de origem ou de destino da conversão
     * O retorno da API é a taxa de conversão baseada na moeda EUR
     * Caso a moeda recebida no parametro seja de origem da conversão, será desconvertido de EUR para a taxa da moeda atual
     * @param  currency verifica qual moeda que esta sendo usada na conversão
     * @param  values realiza a conversão da taxa para a moeda utilizada na conversão dos valores
     * @param  isOrigin verifica se a moeda é a origem ou a moeda de destino, para desconversão de taxa de EUR
     * @return A taxa da moeda atual, desconvertida de EUR
     * @since 11.0
     */
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


    /**
     * Recebe os parametros urlRequest {@code String}, body {@code String}, method {@code String}, 
     *   contentType {@code String}, accepts {@code String}, doOutput {@code boolean}
     * Realiza a consulta na API com base na URL e nos dados passados por parametros
     * @param  urlRequest url da requisição na API
     * @param  body corpo da requisição na API
     * @param  method metodo de requisição
     * @param  contentType Content-Type da requisição da API
     * @param  accepts Accept da requisição da API
     * @param  doOutput verifica se retorna objeto do tipo OutputStream
     * @return O resultado da requisição da API {responseStr.toString()}
     * @since 11.0
     */
    private String performRequest(String urlRequest, String body, 
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