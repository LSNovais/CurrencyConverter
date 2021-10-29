package br.com.project.currencyconverter.repository;

import br.com.project.currencyconverter.model.TransactionHist;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface TransactionHistRepository extends JpaRepository<TransactionHist, Long> {

    
}