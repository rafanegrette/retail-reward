package com.companyabc.retail.repositories;

import java.time.LocalDate;
import java.util.List;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;

public interface TransactionRepository {

	void save(Transaction transaction);
	
	List<Transaction> findAllByClientAndDateBetween(Client client, LocalDate initDate, LocalDate endDate);
	
	List<Transaction> findAllByDateBetween(LocalDate initDate, LocalDate endDate);
}
