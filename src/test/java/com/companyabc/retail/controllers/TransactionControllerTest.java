package com.companyabc.retail.controllers;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.companyabc.retail.model.TransactionDTO;
import com.companyabc.retail.services.TransactionService;
import com.companyabc.retail.services.exceptions.InvalidTransactionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TransactionService transctionService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreateOK() throws Exception {
		// GIVEN
		TransactionDTO transactionDTO = new TransactionDTO(1L, BigDecimal.valueOf(78), LocalDateTime.now());
		ObjectMapper objectMapper = new ObjectMapper()
				.registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());
		String transactionDTOStr = objectMapper.writeValueAsString(transactionDTO);
		
		// WHEN
		// THEN
		this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_VALUE).content(transactionDTOStr)).andExpect(status().isCreated());
		verify(transctionService, times(1)).recibePayment(transactionDTO);
	}
	
	@Test
	void testCreateInvalidAmount() throws Exception {
		// GIVEN
		TransactionDTO transactionDTO = new TransactionDTO(1L, BigDecimal.valueOf(0), LocalDateTime.now());
		ObjectMapper objectMapper = new ObjectMapper()
				.registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());
		String transactionDTOStr = objectMapper.writeValueAsString(transactionDTO);
		
		// WHEN
		doThrow(new InvalidTransactionException("INVALID_AMOUNT")).when(transctionService).recibePayment(transactionDTO);
		// THEN
		MvcResult mvcResult = this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_VALUE).content(transactionDTOStr)).andExpect(status().isUnprocessableEntity()).andReturn();
		verify(transctionService, times(1)).recibePayment(transactionDTO);
		assertEquals("INVALID_AMOUNT", mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	void testCreateClientNotExist() throws Exception {
		// GIVEN
		TransactionDTO transactionDTO = new TransactionDTO(2L, BigDecimal.valueOf(78), LocalDateTime.now());
		ObjectMapper objectMapper = new ObjectMapper()
				.registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());
		String transactionDTOStr = objectMapper.writeValueAsString(transactionDTO);
		
		// WHEN
		doThrow(new InvalidTransactionException("CLIENT_NOT_EXIST")).when(transctionService).recibePayment(transactionDTO);
		// THEN
		MvcResult mvcResult = this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_VALUE).content(transactionDTOStr)).andExpect(status().isUnprocessableEntity()).andReturn();
		assertEquals("CLIENT_NOT_EXIST", mvcResult.getResponse().getContentAsString());
	}

}
