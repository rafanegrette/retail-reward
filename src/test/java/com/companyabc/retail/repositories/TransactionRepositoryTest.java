package com.companyabc.retail.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TransactionRepositoryTest {

	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	List<Transaction> transactionsClient1 = new ArrayList<>();
	List<Transaction> transactionsClient2 = new ArrayList<>();
	Client client1;
	Client client2;
	
	@BeforeEach
	void setUp() throws Exception {
		
		//GIVEN

		client1 = new Client("Otto", "Simpson");
		client2 = new Client("Ned", "Flanders");
		
		clientRepository.save(client1);
		clientRepository.save(client2);
		
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
		
		transactionRepository.saveAll(transactionsClient1);
		transactionRepository.saveAll(transactionsClient2);
	}

	@Test
	void testFindAllByClientAndDateBetween() {
		// WHEN 
		
		List<Transaction> transactionsReturned = transactionRepository.findAllByClientAndDateBetween(client1,
				LocalDateTime.of(2021, 11, 1, 0, 0), LocalDateTime.of(2021, 11, 30, 0 ,0));
		
		// THEN
		assertEquals(3, transactionsReturned.size());
	}

	@Test
	void testFindAllByDateBetween() {
		// WHEN
		
		List<Transaction> transactionsReturned = transactionRepository.findAllByDateBetween(
				LocalDateTime.of(2021, 9, 1, 0, 0), LocalDateTime.of(2021, 10, 31, 0 ,0));
		
		// THEN 
		assertEquals(5, transactionsReturned.size());
	}

}
