package com.companyabc.retail.services;

import java.time.LocalDate;
import java.util.List;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;
import com.companyabc.retail.model.TransactionDTO;

public interface TransactionService {
	void recibePayment(TransactionDTO transaction);
	List<Transaction> findLastTransactionsByClientFrom(Client client, LocalDate date);
	List<Transaction> findLastTransactionsFrom(LocalDate date);
	List<TransactionDTO> findAll();
	void update(Long idTransaction, TransactionDTO transactionDTO);
	void delete(Long idTransaction);
}
