package com.companyabc.retail.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.companyabc.retail.model.TransactionDTO;
import com.companyabc.retail.services.TransactionService;
import com.companyabc.retail.services.exceptions.InvalidTransactionException;

@RestController
@RequestMapping("transactions")
public class TransactionController {
	
	private final TransactionService transctionService;

	
	public TransactionController(TransactionService transctionService) {
		super();
		this.transctionService = transctionService;
	}


	@PostMapping
	public ResponseEntity<String> create(@RequestBody TransactionDTO transactionDTO) {
		try {
			transctionService.recibePayment(transactionDTO);
			return new ResponseEntity<String> ("CREATED", HttpStatus.CREATED);
		} catch (InvalidTransactionException iEx) {
			return new ResponseEntity<String> (iEx.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
