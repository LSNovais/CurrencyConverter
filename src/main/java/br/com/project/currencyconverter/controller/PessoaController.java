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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.currencyconverter.CurrencyconverterApplication;
import br.com.project.currencyconverter.model.Currency;
import br.com.project.currencyconverter.model.TransactionHist;
import br.com.project.currencyconverter.service.CurrencyConversionService;
import br.com.project.currencyconverter.service.CurrencyService;
import br.com.project.currencyconverter.service.TransactionHistService;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:8080/currencyConverter")
@RestController
@AllArgsConstructor
@RequestMapping("/**")
public class PessoaController {
    

	private static Logger logger = LoggerFactory.getLogger(CurrencyconverterApplication.class);

    @Autowired
    private TransactionHistService transactionHistService;
    
    @Autowired
    private CurrencyService currencyService;

    public PessoaController(){};
        
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> converterCurrency(Currency currency){
        Currency currencyAdded = new Currency();
        try{
            currencyAdded = currencyService.save(currency);
            logger.info("PessoaController.converterCurrency - Nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUsuario()+" realizada com sucesso!");
            return ResponseEntity.status(HttpStatus.CREATED).body(currencyAdded);
        }catch(DataAccessException dae){
            logger.info("PessoaController.converterCurrency - Falha ao gravar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUsuario());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(currencyAdded);
        }catch(SQLDataException dae){
            logger.info("PessoaController.converterCurrency - Falha ao gravar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUsuario());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(currencyAdded);
        }catch(Exception e){
            logger.info("PessoaController.converterCurrency - Falha ao realizar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUsuario());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(currencyAdded);
        }

    }


    @CrossOrigin
    @PostMapping(path = "/convert/{idUsuario}/{moedaOrigem}/{valorOrigem}/{moedaDestino}")
    public ResponseEntity<?> converterTransactionHist(@PathVariable Integer idUsuario, @PathVariable String moedaOrigem, @PathVariable Double valorOrigem, @PathVariable String moedaDestino) {
        TransactionHist transactionHist = new TransactionHist();
        TransactionHist transactionHistAdded = new TransactionHist();
        CurrencyConversionService currencyConversionService = new CurrencyConversionService();
        Currency currency = new Currency();

        try{
            currency.setMoedaOrigem(moedaOrigem);
            currency.setValorOrigem(valorOrigem);
            currency.setMoedaDestino(moedaDestino);
            currency.setDataHora(new Date());
            currency.setIdUsuario(idUsuario);
            logger.info("PessoaController.converterTransactionHist - Valores recebidos pela API /convert/{idUsuario}/{moedaOrigem}/{valorOrigem}/{moedaDestino} instanciados com sucesso:"
            +"\n Moeda Origem: "+currency.getMoedaOrigem()
            +"\n Valor Origem: "+currency.getValorOrigem()
            +"\n Moeda Destino: "+currency.getMoedaDestino()
            +"\n Data Hora: "+currency.getDataHora()
            +"\n idUsuario: "+currency.getIdUsuario());

            currency.setTxConvUtil(currencyConversionService.taxConversion(currency.getMoedaOrigem(), currency.getMoedaDestino()));
            logger.info("PessoaController.converterTransactionHist - Gerada taxa de conversão para o valor "+currency.getValorOrigem()+" ser alterado de "+currency.getMoedaOrigem()+" para "+currency.getMoedaDestino());

            try{
                if(converterCurrency(currency).getStatusCode().equals(HttpStatus.CREATED)){
                    transactionHist.setId(idUsuario);
                    transactionHist.setIdUsuario(currency);
                    transactionHist.setValorDestino(currencyConversionService.conversion(currency));
                    logger.info("PessoaController.converterTransactionHist - Gerado o valor convertido "+transactionHist.getValorDestino()+" de "+currency.getMoedaOrigem()+" para "+currency.getMoedaDestino());
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
                logger.info("PessoaController.converterTransactionHist - Falha ao realizar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUsuario());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
            }

        }catch(DataAccessException dae){
            logger.info("PessoaController.converterTransactionHist - Falha ao gravar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUsuario());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
        }catch(SQLDataException dae){
            logger.info("PessoaController.converterTransactionHist - Falha ao gravar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUsuario());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
        }catch(HttpMessageConversionException httpmce){
            logger.info("Falha ao coletar valores de conversão da API externa");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
        }catch(Exception e){
            logger.info("PessoaController.converterTransactionHist - Falha ao realizar nova conversão com o id "+currency.getIdCurrency()+" e idUsuario "+currency.getIdUsuario());
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionHistAdded);
        }
    }

}