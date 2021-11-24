package com.companyabc.retail.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;
import com.companyabc.retail.repositories.TransactionRepository;

class TransactionServiceJPA implements TransactionService {

	private final TransactionRepository transactionRepository;
	

	
	public TransactionServiceJPA(TransactionRepository transactionRepository) {
		super();
		this.transactionRepository = transactionRepository;
	}


	@Override
	public void recibePayment(Client client, BigDecimal amount) {
		Transaction transaction = new Transaction(client, amount, LocalDateTime.now());
		transactionRepository.save(transaction);
	}


	@Override
	public List<Transaction> findLastTransactionsByClientFrom(Client client, LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Transaction> findLastTransactionsFrom(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

}
