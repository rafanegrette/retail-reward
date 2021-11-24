package com.companyabc.retail.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	@Query(value = "SELECT t FROM Transaction t WHERE t.client.id = :#{#client.id} AND t.dateTime BETWEEN :initDate AND :endDate")
	List<Transaction> findAllByClientAndDateBetween(@Param("client") Client client, 
			@Param("initDate") LocalDateTime initDate, 
			@Param("endDate") LocalDateTime endDate);
	
	@Query(value = "SELECT t FROM Transaction t WHERE t.dateTime BETWEEN :initDate AND :endDate")
	List<Transaction> findAllByDateBetween(@Param("initDate") LocalDateTime initDate, @Param("endDate") LocalDateTime endDate);
}
