package com.companyabc.retail.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.companyabc.retail.model.ClientDTO;
import com.companyabc.retail.services.ClientService;
import com.companyabc.retail.services.exceptions.InvalidClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ClientService clientService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreateClient() throws Exception {
		// GIVEN
		
		ClientDTO clientDTO = new ClientDTO("Otto", "Simpson");
		ObjectMapper object = new ObjectMapper();
		String clientDTOstr = object.writeValueAsString(clientDTO);
		
		// WHEN
		when(clientService.create(clientDTO)).thenReturn(1L);
		
		// THEN
		MvcResult mvcResult = this.mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON_VALUE).content(clientDTOstr)).andExpect(status().isCreated()).andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
		assertEquals(1 , Integer.valueOf(mvcResult.getResponse().getContentAsString()));

	}
	
	@Test
	void testCreateClientFail() throws Exception {
		// GIVEN
		
		ClientDTO clientDTO = new ClientDTO("", "Simpson");
		ObjectMapper object = new ObjectMapper()
				.registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());
		String clientDTOstr = object.writeValueAsString(clientDTO);
		
		// WHEN
		when(clientService.create(clientDTO)).thenThrow(InvalidClientException.class);
		
		// THEN
		MvcResult mvcResult = this.mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON_VALUE).content(clientDTOstr)).andExpect(status().isUnprocessableEntity()).andReturn();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), mvcResult.getResponse().getStatus());

	}

}
