package com.companyabc.retail.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.companyabc.retail.model.ClientDTO;
import com.companyabc.retail.model.TransactionDTO;
import com.companyabc.retail.services.ClientService;
import com.companyabc.retail.services.TransactionService;

@Component
@Order(1)
public class BootstrapInitialData implements CommandLineRunner{

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Override
	public void run(String... args) throws Exception {
		ClientDTO client1 = new ClientDTO("Ralph", "Simpson");
		ClientDTO client2 = new ClientDTO("Napoleon", "Hill");
		Long idClient1 = clientService.create(client1);
		Long idClient2 = clientService.create(client2);
		
		LocalDateTime dateTime = LocalDateTime.now();

		TransactionDTO transaction1 = new TransactionDTO(idClient1, BigDecimal.valueOf(50), dateTime.minusDays(10));
		TransactionDTO transaction2 = new TransactionDTO(idClient1, BigDecimal.valueOf(75), dateTime.minusDays(20));
		TransactionDTO transaction3 = new TransactionDTO(idClient1, BigDecimal.valueOf(30), dateTime.minusDays(28));
		TransactionDTO transaction4 = new TransactionDTO(idClient1, BigDecimal.valueOf(110), dateTime.minusDays(48));
		TransactionDTO transaction5 = new TransactionDTO(idClient2, BigDecimal.valueOf(30), dateTime.minusDays(10));
		TransactionDTO transaction6 = new TransactionDTO(idClient2, BigDecimal.valueOf(55), dateTime.minusDays(34));
		TransactionDTO transaction7 = new TransactionDTO(idClient2, BigDecimal.valueOf(78), dateTime.minusDays(15));
		TransactionDTO transaction8 = new TransactionDTO(idClient2, BigDecimal.valueOf(310), dateTime.minusDays(51));

		transactionService.recibePayment(transaction1);
		transactionService.recibePayment(transaction2);
		transactionService.recibePayment(transaction3);
		transactionService.recibePayment(transaction4);
		transactionService.recibePayment(transaction5);
		transactionService.recibePayment(transaction6);
		transactionService.recibePayment(transaction7);
		transactionService.recibePayment(transaction8);
	}

}
