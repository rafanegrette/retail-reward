package com.companyabc.retail.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;

public interface TransactionService {
	void recibePayment(Client client, BigDecimal amount);
	List<Transaction> findLastTransactionsByClientFrom(Client client, LocalDate date);
	List<Transaction> findLastTransactionsFrom(LocalDate date);
}
