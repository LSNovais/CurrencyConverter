package br.com.project.currencyconverter.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyConversionApiDTO {

	public CurrencyConversionApiDTO(){
        super();
    };
    
    @JsonProperty("success")
    private String success;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("base")
    private String base;
    @JsonProperty("date")
    private String date;
    @JsonProperty("rates")
    private SubCurrencyConversionApiDTO rates;



}
