/*
 * Class: Currency.java
 * Created: 28/10/2021
 * Rights Reserved: Jaya
 */  

package br.com.project.currencyconverter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import jdk.jfr.Name;
import lombok.Data;




/**
 * @version 1.0
 * @author Lucas Novais dos Santos
 */
@Entity
@Table(name = "CURRENCY")
@Data
public class Currency {
    

    @Id
    @Column(name = "id_currency")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCurrency;

    @Name("id_user")
    Integer idUser;

    @Name("currency_origin")
    String currencyOrigin;

    @Name("value_origin")
    Double valueOrigin;

    @Name("currency_destiny")
    String currencyDestiny;

    @Name("tx_conv_util")
    Double txConvUtil;

    @Name("date_hour")
    Date dateHour;

}