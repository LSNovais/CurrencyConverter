package br.com.project.currencyconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyconverterApplication {

	private static Logger logger = LoggerFactory.getLogger(CurrencyconverterApplication.class);

	public static void main(String[] args) {
		logger.info("Iniciando a api de conversão de valores");
		SpringApplication.run(CurrencyconverterApplication.class, args);
		logger.info("Api de conversão de valores iniciada e pronta para enviar e receber requisições");

	}

}
