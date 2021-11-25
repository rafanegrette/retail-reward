package com.companyabc.retail.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;
import com.companyabc.retail.model.RewardReportDTO;

@ExtendWith(MockitoExtension.class)
class RewardServiceImplTest {

	@Mock
	TransactionService transactionService;
	
	@Mock
	ClientService clientService;
	
	@InjectMocks
	RewardServiceImpl rewardService;
	
	Client client1;
	Client client2;
	List<Transaction> transactionsClient1 = new ArrayList<>();
	List<Transaction> transactionsClient2 = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
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
	void testCalculateTotalByClient() {
		// WHEN
		when(clientService.findById(1L)).thenReturn(Optional.of(client1));
		when(transactionService.findLastTransactionsByClientFrom(client1, LocalDate.of(2021, 11, 30))).thenReturn(transactionsClient1);
		RewardReportDTO report = rewardService.calculateByClient(1L, LocalDate.of(2021, 11, 30));
		
		// THEN
		// T1: 0 pts
		// T2: 150  = 50x2 + 1x50 = 150 pts
		// T3: 0 pts
		// T4: 60 = 10 x 1 = 10 pts
		// T5: 170 = 70 x 2 + 1 x50 = 190 pts
		// Total: 350 pts
		assertEquals(350, report.getTotal());		
	}
	
	@Test
	void testCalculateMonthlyByClient() {
		// WHEN
		when(clientService.findById(1L)).thenReturn(Optional.of(client1));
		when(transactionService.findLastTransactionsByClientFrom(client1, LocalDate.of(2021, 11, 30))).thenReturn(transactionsClient1);
		RewardReportDTO report = rewardService.calculateByClient(1L, LocalDate.of(2021, 11, 30));
		
		// THEN
		assertEquals(150, report.getMonths().get(Month.NOVEMBER));
		assertEquals(200, report.getMonths().get(Month.OCTOBER));
		assertEquals(0, report.getMonths().get(Month.SEPTEMBER));
	}

	@Test
	void testCalculateAll() {
		List<Transaction> totalTransactions = new ArrayList<>();
		totalTransactions.addAll(transactionsClient1);
		totalTransactions.addAll(transactionsClient2);
		
		//WHEN
		
		when(transactionService.findLastTransactionsFrom(LocalDate.of(2021, 11, 30))).thenReturn(totalTransactions);
		List<RewardReportDTO> reports = rewardService.calculateAll(LocalDate.of(2021, 11, 30));
		//THEN
		assertEquals(2, reports.size());
		assertEquals("Flanders", reports.get(1).getClientName());
	}

}
