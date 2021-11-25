package com.companyabc.retail.services;

import java.util.Optional;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.model.ClientDTO;

public interface ClientService {

	Long create(ClientDTO clientDTO);

	Optional<Client> findById(long idClient);
}
