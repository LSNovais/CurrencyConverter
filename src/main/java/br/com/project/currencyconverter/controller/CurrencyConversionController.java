/*
 * Class: CurrencyConversionController.java
 * Created: 29/10/2021
 * Created by: Lucas Novais dos Santos
 * Rights Reserved: Jaya
 */  

package br.com.project.currencyconverter.controller;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.currencyconverter.CurrencyconverterApplication;
import br.com.project.currencyconverter.model.Currency;
import br.com.project.currencyconverter.model.TransactionHist;
import br.com.project.currencyconverter.service.CurrencyConversionService;
import br.com.project.currencyconverter.service.CurrencyService;
import br.com.project.currencyconverter.service.TransactionHistService;
import lombok.AllArgsConstructor;


/**
 * @version 1.0
 * @author Lucas Novais dos Santos
 */
@CrossOrigin(origins = "http://localhost:8080/currencyConverter")
@RestController
@AllArgsConstructor
@RequestMapping("/**")
public class CurrencyConversionController {
    

	private static Logger logger = LoggerFactory.getLogger(CurrencyconverterApplication.class);

    @Autowired
    private TransactionHistService transactionHistService;
    
    @Autowired
    private CurrencyService currencyService;

    public CurrencyConversionController(){};
        


    /**
     * API path = "/consult/transactions/{idUsuario}" 
     * Utilizada como GET, coleta o idUsuario {@code Integer} enviada como parametro pelo usuário
     * Retorna histórico de transações do idUsuario recebido
     * @param  idUsuario id do usuário recebido por parametro, que será usado para consultar seu historico
     * @return o historico do usuário  ResponseEntity.ok(transactionHist);
     * @since 11.0
     */
    @CrossOrigin
    @GetMapping(path = "/consult/transactions/{idUsuario}")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> findByUser(@PathVariable Integer idUsuario){
        List<TransactionHist> transactionHist = new ArrayList();
        try{
            transactionHist =  transactionHistService.findByUser(idUsuario);
            if(ResponseEntity.ok(transactionHist) != null){
                logger.info("PessoaController.findByUser - Encontrado historico de usuario com idUsuario "+idUsuario);
                return ResponseEntity.ok(transactionHist);
            }else{
                logger.info("PessoaController.findByUser - Não foi encontrado historico de usuario com idUsuario "+idUsuario);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionHist);
            }
        }catch(SQLDataException dae){
            logger.info("PessoaController.findByUser - Não foi encontrado historico de usuario com idUsuario "+idUsuario);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionHist);
        }catch(NoSuchElementException nsee){
            logger.info("PessoaController.findByUser - Não foi encontrado historico de usuario com idUsuario "+idUsuario);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionHist);
        }catch(NullPointerException npe){
            logger.info("PessoaController.findByUser - Não foi encontrado historico de usuario com idUsuario "+idUsuario);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionHist);
        }catch(Exception e){
            logger.info("PessoaController.findByUser - Falha ao localizar historico de usuario com idUsuario "+idUsuario);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(transactionHist);
        }
    }




    /**
     * Salva a conversão dos valores no banco de dados na tabela CURRENCY
     * @param  currency Valor que será inserido no banco de dados
     * @return A confirmação da inserção dos dados no banco de dados;
     * @since 11.0
     */
    public ResponseEntity<?> converterCurrency(Currency currency){
        Currency currencyAdded = new Currency();
        try{
            currencyAdded = currencyService.save(currency);
            logger.info("PessoaController.converterCurrency - Nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUser()+" realizada com sucesso!");
            return ResponseEntity.status(HttpStatus.CREATED).body(currencyAdded);
        }catch(DataAccessException dae){
            logger.info("PessoaController.converterCurrency - Falha ao gravar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUser());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(currencyAdded);
        }catch(SQLDataException dae){
            logger.info("PessoaController.converterCurrency - Falha ao gravar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUser());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(currencyAdded);
        }catch(Exception e){
            logger.info("PessoaController.converterCurrency - Falha ao realizar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUser());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(currencyAdded);
        }

    }





    /**
     * API path = "/convert/{idUsuario}/{moedaOrigem}/{valorOrigem}/{moedaDestino}
     * Utilizada como GET, coleta o: 
     *        idUsuario {@code Integer}, moedaOrigem {@code String}, valorOrigem {@code Double}, moedaDestino {@code String}
     * Realiza as devidas conversões atráves dos métodos que chama, e armazena a conversão no banco de dados
     * @param  idUsuario id do usuário recebido por parametro, que será usado para salvar seu historico
     * @param  moedaOrigem moeda de origem recebida por parametro, que será usada para realizar a conversão
     * @param  valorOrigem valor de origem recebido por parametro, que será usada para realizar a conversão
     * @param  moedaDestino moeda de destino recebida por parametro, que será usada para realizar a conversão
     * @return confirmação da conversão e o retorno dos dados salvos no banco de dados
     * @since 11.0
     */
    @CrossOrigin
    @GetMapping(path = "/convert/{idUser}/{currencyOrigin}/{valueOrigin}/{currencyDestiny}")
    public ResponseEntity<?> converterTransactionHist(@PathVariable Integer idUser, @PathVariable String currencyOrigin, @PathVariable Double valueOrigin, @PathVariable String currencyDestiny) {
        TransactionHist transactionHist = new TransactionHist();
        TransactionHist transactionHistAdded = new TransactionHist();
        CurrencyConversionService currencyConversionService = new CurrencyConversionService();
        Currency currency = new Currency();

        try{
            currency.setCurrencyOrigin(currencyOrigin);
            currency.setValueOrigin(valueOrigin);
            currency.setCurrencyDestiny(currencyDestiny);
            currency.setDateHour(new Date());
            currency.setIdUser(idUser);
            logger.info("PessoaController.converterTransactionHist - Valores recebidos pela API /convert/{idUsuario}/{moedaOrigem}/{valorOrigem}/{moedaDestino} instanciados com sucesso:"
            +"\n Moeda Origem: "+currency.getCurrencyOrigin()
            +"\n Valor Origem: "+currency.getValueOrigin()
            +"\n Moeda Destino: "+currency.getCurrencyDestiny()
            +"\n Data Hora: "+currency.getDateHour()
            +"\n idUsuario: "+currency.getIdUser());

            currency.setTxConvUtil(currencyConversionService.taxConversion(currency.getCurrencyOrigin(), currency.getCurrencyDestiny()));
            logger.info("PessoaController.converterTransactionHist - Gerada taxa de conversão para o valor "+currency.getValueOrigin()+" ser alterado de "+currency.getCurrencyOrigin()+" para "+currency.getCurrencyDestiny());

            try{
                if(converterCurrency(currency).getStatusCode().equals(HttpStatus.CREATED)){
                    transactionHist.setId(idUser);
                    transactionHist.setIdUser(currency);
                    transactionHist.setValueDestiny(currencyConversionService.conversion(currency));
                    logger.info("PessoaController.converterTransactionHist - Gerado o valor convertido "+transactionHist.getValueDestiny()+" de "+currency.getCurrencyOrigin()+" para "+currency.getCurrencyDestiny());
                }else{
                    logger.info("PessoaController.converterTransactionHist - Falha ao realizar nova conversão");
                    return converterCurrency(currency);
                }
            transactionHistAdded = transactionHistService.save(transactionHist);
            logger.info("PessoaController.converterTransactionHist - Conversão realizada com sucesso e gerado historico com sucesso!");
            return ResponseEntity.status(HttpStatus.CREATED).body(transactionHistAdded);
            }catch(DataAccessException sqle){
                logger.info("PessoaController.converterTransactionHist - Falha ao gravar nova conversão");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
            }catch(SQLDataException dae){
                logger.info("PessoaController.converterTransactionHist - Falha ao gravar nova conversão");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
            }catch(Exception e){
                logger.info("PessoaController.converterTransactionHist - Falha ao realizar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUser());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
            }

        }catch(DataAccessException dae){
            logger.info("PessoaController.converterTransactionHist - Falha ao gravar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUser());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
        }catch(SQLDataException dae){
            logger.info("PessoaController.converterTransactionHist - Falha ao gravar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUser());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
        }catch(HttpMessageConversionException httpmce){
            logger.info("Falha ao coletar valores de conversão da API externa");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
        }catch(Exception e){
            logger.info("PessoaController.converterTransactionHist - Falha ao realizar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUser());
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
        }
    }

}