package br.com.project.currencyconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CurrencyconverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyconverterApplication.class, args);
	}

}
