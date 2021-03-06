package com.companyabc.retail.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.model.ClientDTO;
import com.companyabc.retail.repositories.ClientRepository;
import com.companyabc.retail.services.exceptions.ClientNotExistException;

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

	@Override
	public ClientDTO findDTOById(long idClient) {
		Client client = clientRepository.findById(idClient).orElseThrow(ClientNotExistException::new);
		return new ClientDTO(client.getFirstName(), client.getLastName());
	}

	@Override
	public List<ClientDTO> findDTOAll() {
		return clientRepository.findAll().stream()
				.map(c -> new ClientDTO(c.getFirstName(), c.getLastName()))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteById(Long idClient) {
		clientRepository.deleteById(idClient);
		
	}

}
