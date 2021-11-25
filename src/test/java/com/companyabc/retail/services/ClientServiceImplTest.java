package com.companyabc.retail.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.model.ClientDTO;
import com.companyabc.retail.repositories.ClientRepository;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
	
	@Mock
	ClientRepository clientRepository;

	@InjectMocks
	ClientServiceImpl clientService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreate() {
		// GIVEN
		ClientDTO clientDTO = new ClientDTO("Otto", "Simpson");
		Client client = new Client("Otto", "Simpson");
		Client clientReturned = new Client("Otto", "Simpson");
		clientReturned.setId(1L);
		
		// WHEN
		when(clientRepository.save(client)).thenReturn(clientReturned);
		Long idClientReturned = clientService.create(clientDTO);
		//THEN
		assertEquals(clientReturned.getId(), idClientReturned);
	}

	@Test
	void testFindById() {
		// GIVEN
		Client client = new Client("Otto", "Simpson");
		client.setId(1L); 
		Optional<Client> clientOptional = Optional.of(client);
		
		// WHEN
		when(clientRepository.findById(1L)).thenReturn(clientOptional);
		Optional<Client> clientReturned = clientService.findById(1L);
		
		// THEN
		
		assertEquals(1L, clientReturned.get().getId());
	}

	@Test
	void testFindByIdNotFound() {
		// GIVEN
		Optional<Client> clientOptional = Optional.ofNullable(null);
		
		// WHEN
		when(clientRepository.findById(9L)).thenReturn(clientOptional);
		Optional<Client> clientReturned = clientService.findById(9L);
		
		// THEN
		
		assertTrue(!clientReturned.isPresent());
	}

}
