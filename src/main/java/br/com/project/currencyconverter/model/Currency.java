package br.com.project.currencyconverter.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import jdk.jfr.Name;
import lombok.Data;

@Entity
@Table(name = "CURRENCY")
@Data
public class Currency {
    

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idUsuario;

    @Name("moeda_origem")
    String moedaOrigem;

    @Name("valor_origem")
    Double valorOrigem;

    @Name("moeda_destino")
    String moedaDestino;

    @Name("tx_conv_util")
    Double txConvUtil;

    @Name("data_hora")
    String dataHora;

}