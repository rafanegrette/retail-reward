package com.companyabc.retail.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NonNull;

@Data
public class TransactionDTO {
	
	@NonNull
	private Long idClient;
	
	@NonNull
	private BigDecimal amount;
	
	@NonNull
	private LocalDateTime dateTime;

}
