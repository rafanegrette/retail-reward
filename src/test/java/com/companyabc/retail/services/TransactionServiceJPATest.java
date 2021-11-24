package com.companyabc.retail.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;
import com.companyabc.retail.repositories.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceJPATest {

	Client client1;
	Client client2;
	List<Transaction> transactionsClient1 = new ArrayList<>();
	List<Transaction> transactionsClient2 = new ArrayList<>();
	
	
	@Mock
	TransactionRepository transactionRepository;
	
	@InjectMocks
	TransactionServiceJPA transactionService;
	
	@BeforeEach
	void setUp() {
		//GIVEN
		client1 = new Client("Otto", "Simpson");
		client2 = new Client("Ned", "Flanders");
		
		Transaction transaction1 = new Transaction(client1, BigDecimal.valueOf(50), LocalDateTime.of(2021, 11, 10, 13, 10));
		Transaction transaction2 = new Transaction(client1, BigDecimal.valueOf(150), LocalDateTime.of(2021, 11, 15, 13, 10));
		Transaction transaction3 = new Transaction(client1, BigDecimal.valueOf(20), LocalDateTime.of(2021, 11, 16, 13, 10));
		Transaction transaction4 = new Transaction(client1, BigDecimal.valueOf(60), LocalDateTime.of(2021, 10, 10, 13, 10));
		Transaction transaction5 = new Transaction(client1, BigDecimal.valueOf(170), LocalDateTime.of(2021, 10, 10, 11, 10));
		transactionsClient1.add(transaction1);
		transactionsClient1.add(transaction2);
		transactionsClient1.add(transaction3);
		transactionsClient1.add(transaction4);
		transactionsClient1.add(transaction5);
		
		Transaction transaction6 = new Transaction(client2, BigDecimal.valueOf(20), LocalDateTime.of(2021, 10, 16, 13, 10));
		Transaction transaction7 = new Transaction(client2, BigDecimal.valueOf(60), LocalDateTime.of(2021, 9, 16, 13, 10));
		Transaction transaction8 = new Transaction(client2, BigDecimal.valueOf(170), LocalDateTime.of(2021, 9, 10, 11, 10));
		transactionsClient2.add(transaction6);
		transactionsClient2.add(transaction7);
		transactionsClient2.add(transaction8);
	}
	
	@Test
	void testRecibePayment() {
		//GIVEN
		BigDecimal amount = BigDecimal.valueOf(40.00);
		
		//WHEN
		transactionService.recibePayment(client1, amount);
		
		//THEN
		verify(transactionRepository, times(1)).save(any(Transaction.class));
	}
	
	@Test
	void testFindLastTransactionsByClientFrom() {
		// GIVEN: transactionsClient1
		
		// WHEN
		ReflectionTestUtils.setField(transactionService, "noLastMonthsTransactions", 3);
		when(transactionRepository.findAllByClientAndDateBetween(client1, LocalDate.of(2021, 8, 1), LocalDate.of(2021, 11, 30)))
								.thenReturn(transactionsClient1);
		List<Transaction> transactionsReturned = transactionService.findLastTransactionsByClientFrom(client1, LocalDate.of(2021, 11, 30));
		
		// THEN
		assertEquals(transactionsClient1.size(), transactionsReturned.size());
		assertEquals("Simpson", transactionsReturned.get(0).getClient().getLastName());
	}

	@Test
	void testFindLastTransactionsFrom() {
		// GIVEN
		List<Transaction> totalTransactions = new ArrayList<>();
		totalTransactions.addAll(transactionsClient1);
		totalTransactions.addAll(transactionsClient2);
		
		// WHEN
		ReflectionTestUtils.setField(transactionService, "noLastMonthsTransactions", 3);
		when(transactionRepository.findAllByDateBetween(LocalDate.of(2021, 8, 1), LocalDate.of(2021, 11, 30))).thenReturn(totalTransactions);
		List<Transaction> transactionsReturned = transactionService.findLastTransactionsFrom(LocalDate.of(2021, 11, 30));
		
		// THEN
		assertEquals(totalTransactions.size() ,transactionsReturned.size());
	}
}
