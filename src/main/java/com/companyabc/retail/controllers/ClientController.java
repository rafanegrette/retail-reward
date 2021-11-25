package com.companyabc.retail.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.companyabc.retail.config.SwaggerConfig;
import com.companyabc.retail.model.ClientDTO;
import com.companyabc.retail.services.ClientService;
import com.companyabc.retail.services.exceptions.ClientNotExistException;
import com.companyabc.retail.services.exceptions.InvalidClientException;

import io.swagger.annotations.Api;

@Api(tags = {SwaggerConfig.CLIENT_TAG})
@RestController
@RequestMapping("clients")
public class ClientController {

	private final ClientService clientService;
	
	
	public ClientController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}


	@PostMapping
	public ResponseEntity<String> createClient(@RequestBody ClientDTO client){
		try {
			Long idClient = clientService.create(client);
			return new ResponseEntity<>(idClient.toString(), HttpStatus.CREATED);
		} catch (InvalidClientException iEx) {
			return new ResponseEntity<>(iEx.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@GetMapping(value = "{idClient}")
	public ResponseEntity<ClientDTO> findById(@PathVariable("idClient") Long idClient) {
		try {
			return new ResponseEntity<>(clientService.findDTOById(idClient), HttpStatus.OK);
		} catch (ClientNotExistException cEx) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<ClientDTO>> findById() {
		try {
			return new ResponseEntity<>(clientService.findDTOAll(), HttpStatus.OK);
		} catch (ClientNotExistException cEx) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@DeleteMapping(value = "{idClient}")
	public ResponseEntity<String> delete(@PathVariable("idClient") Long idClient) {
		clientService.deleteById(idClient);
		return new ResponseEntity<>("Client deleted", HttpStatus.OK);
	}
}
