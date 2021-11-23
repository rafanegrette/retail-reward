package com.companyabc.retail.services;

import java.math.BigDecimal;
import com.companyabc.retail.domain.Client;

public interface TransactionService {
	void recibePayment(Client client, BigDecimal amount);
}
