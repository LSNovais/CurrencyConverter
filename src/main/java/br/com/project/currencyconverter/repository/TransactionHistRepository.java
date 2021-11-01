/*
 * Class: TransactionHistRepository.java
 * Created: 29/10/2021
 * Rights Reserved: Jaya
 */  

package br.com.project.currencyconverter.repository;

import br.com.project.currencyconverter.model.TransactionHist;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


/**
 * @version 1.0
 * @author Lucas Novais dos Santos
 */
@Repository
@EnableJpaRepositories
public interface TransactionHistRepository extends JpaRepository<TransactionHist, Long> {

    
}