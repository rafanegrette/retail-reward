package com.companyabc.retail.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.model.ClientDTO;
import com.companyabc.retail.repositories.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	
	public ClientServiceImpl(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
	}

	@Override
	public Long create(ClientDTO clientDTO) {
		Client client = new Client(clientDTO.getFirstName(), clientDTO.getLastName());
		client = clientRepository.save(client);
		return client.getId();
	}

	@Override
	public Optional<Client> findById(long idClient) {
		return clientRepository.findById(idClient);
	}

}
