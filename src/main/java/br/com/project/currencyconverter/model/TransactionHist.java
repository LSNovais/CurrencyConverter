/*
 * Class: TransactionHist.java
 * Created: 28/10/2021
 * Rights Reserved: Jaya
 */  

package br.com.project.currencyconverter.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jdk.jfr.Name;
import lombok.Data;



/**
 * @version 1.0
 * @author Lucas Novais dos Santos
 */
@Entity
@Table(name = "TRANSACTIONHIST")
@Data
public class TransactionHist {

    @Id
    @Column(name = "id_transaction_hist")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    Currency idUser;

    @Name("value_destiny")
    Double valueDestiny;


    
}