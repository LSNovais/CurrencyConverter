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
public class SubCurrencyConversionApiDTO {
    
    public SubCurrencyConversionApiDTO(){
        super();
    };

    @JsonProperty("EUR")
    private Double EUR;

    @JsonProperty("BRL")
    private Double BRL;

    @JsonProperty("USD")
    private Double USD;

    @JsonProperty("JPY")
    private Double JPY;

}
