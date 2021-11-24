package com.companyabc.retail.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;
import com.companyabc.retail.repositories.TransactionRepository;

class TransactionServiceJPA implements TransactionService {

	@Value("${app.transactions.months:3}")
	private Integer noLastMonthsTransactions;
	
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
		LocalDate startDate = date.plusMonths(noLastMonthsTransactions * -1).withDayOfMonth(1);
		List<Transaction> transactions = transactionRepository.findAllByClientAndDateBetween(client, startDate, date);
		return transactions;
	}


	@Override
	public List<Transaction> findLastTransactionsFrom(LocalDate date) {
		LocalDate startDate = date.plusMonths(noLastMonthsTransactions * -1).withDayOfMonth(1);
		List<Transaction> transactions = transactionRepository.findAllByDateBetween(startDate, date);
		return transactions;
	}

}
