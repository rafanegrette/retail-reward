package com.companyabc.retail.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.domain.Transaction;
import com.companyabc.retail.repositories.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceJPATest {

	Client client1;
	
	@Mock
	TransactionRepository transactionRepository;
	
	@InjectMocks
	TransactionServiceJPA transactionService;
	
	@BeforeEach
	void setUp() {
		
		client1 = new Client("Otto", "Simpson");
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

}
