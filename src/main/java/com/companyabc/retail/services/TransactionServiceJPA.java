package com.companyabc.retail.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;
import com.companyabc.retail.model.TransactionDTO;
import com.companyabc.retail.repositories.TransactionRepository;
import com.companyabc.retail.services.exceptions.InvalidTransactionException;

@Service
class TransactionServiceJPA implements TransactionService {

	@Value("${app.transactions.months:3}")
	private Integer noLastMonthsTransactions;
	
	private final TransactionRepository transactionRepository;
	private final ClientService clientService;
	
	public TransactionServiceJPA(TransactionRepository transactionRepository,
								ClientService clientService) {
		super();
		this.transactionRepository = transactionRepository;
		this.clientService = clientService;
	}


	@Override
	public void recibePayment(TransactionDTO transactionDTO) {
		Optional<Client> clientOptional = clientService.findById(transactionDTO.getIdClient());
		Client client = clientOptional.orElseThrow(InvalidTransactionException::new);
		
		Transaction transaction = new Transaction(client, 
				transactionDTO.getAmount(), transactionDTO.getDateTime());
		transactionRepository.save(transaction);
	}


	@Override
	public List<Transaction> findLastTransactionsByClientFrom(Client client, LocalDate date) {
		LocalDateTime startDate = date.plusMonths(noLastMonthsTransactions * -1).withDayOfMonth(1).atTime(LocalTime.of(0, 0));
		List<Transaction> transactions = transactionRepository.findAllByClientAndDateBetween(client, startDate, date.atTime(0,0));
		return transactions;
	}


	@Override
	public List<Transaction> findLastTransactionsFrom(LocalDate date) {
		LocalDateTime startDate = date.plusMonths(noLastMonthsTransactions * -1).withDayOfMonth(1).atTime(LocalTime.of(0, 0));
		List<Transaction> transactions = transactionRepository.findAllByDateBetween(startDate, date.atTime(0, 0));
		return transactions;
	}

}
