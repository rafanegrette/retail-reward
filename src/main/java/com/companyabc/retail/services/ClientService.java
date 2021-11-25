package com.companyabc.retail.services;

import com.companyabc.retail.domain.Client;
import com.companyabc.retail.model.ClientDTO;

public interface ClientService {

	Integer create(ClientDTO clientDTO);

	Client findById(long idClient);
}
