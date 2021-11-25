package com.companyabc.retail.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.companyabc.retail.config.SwaggerConfig;
import com.companyabc.retail.model.TransactionDTO;
import com.companyabc.retail.services.TransactionService;
import com.companyabc.retail.services.exceptions.InvalidTransactionException;

import io.swagger.annotations.Api;


@Api(tags = {SwaggerConfig.TRANSACTION_TAG})
@RestController
@RequestMapping("transactions")
public class TransactionController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
	private final TransactionService transactionService;

	
	public TransactionController(TransactionService transctionService) {
		super();
		this.transactionService = transctionService;
	}


	@PostMapping
	public ResponseEntity<String> create(@RequestBody TransactionDTO transactionDTO) {
		try {
			LOGGER.info("Creating transaction");
			transactionService.recibePayment(transactionDTO);
			return new ResponseEntity<String> ("CREATED", HttpStatus.CREATED);
		} catch (InvalidTransactionException iEx) {
			LOGGER.error("Creating transaction", iEx);
			return new ResponseEntity<String> (iEx.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<TransactionDTO>> findAll() {
		LOGGER.info("Quering all transactions");
		List<TransactionDTO> transactions = transactionService.findAll();
		return new ResponseEntity<List<TransactionDTO>>(transactions, HttpStatus.OK);
	}
	
	@PutMapping(value = "{idTransaction}")
	public ResponseEntity<String> update(@PathVariable("idTransaction") Long idTransaction,
											@RequestBody TransactionDTO transactionDTO) {
		try {
			LOGGER.info("Updating transaction id: " + idTransaction);
			transactionService.update(idTransaction, transactionDTO);
			return new ResponseEntity<String> ("UPDATED", HttpStatus.OK);
		} catch (InvalidTransactionException ex) {
			LOGGER.error("Updating transaction", ex);
			throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, ex.getMessage());
			//return new ResponseEntity<String> (ex.getMessage(), HttpStatus.NOT_MODIFIED);
		}
	}
	
	@DeleteMapping(value = "{idTransaction}")
	public ResponseEntity<String> delete(@PathVariable("idTransaction") Long idTransaction) {
		LOGGER.warn("Delete transaction Id: " + idTransaction);
		transactionService.delete(idTransaction);
		return new ResponseEntity<String> ("DELETED", HttpStatus.OK);
	}
}
