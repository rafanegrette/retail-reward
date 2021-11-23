package com.companyabc.retail.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NonNull;

@Data
public class Transaction {
	private long id;
	@NonNull
	private Client client;
	@NonNull
	private BigDecimal amount;
	@NonNull
	private LocalDateTime dateTime;
}
