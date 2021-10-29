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

@Entity
@Table(name = "TRANSACTIONHIST")
@Data
public class TransactionHist {

    @Id
    @Column(name = "id_transaction_hist")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Currency idUsuario;

    @Name("valor_destino")
    Double valorDestino;

    
}