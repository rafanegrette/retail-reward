package com.companyabc.retail.repositories;

import com.companyabc.retail.domain.Transaction;

public interface TransactionRepository {

	void save(Transaction transaction);
}
