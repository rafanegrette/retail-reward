package com.companyabc.retail.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
	
	private Long idTransaction;
	
	@NonNull
	private Long idClient;
	
	@NonNull
	private BigDecimal amount;
	
	@NonNull
	private LocalDateTime dateTime;
	
	public TransactionDTO(Long idClient, BigDecimal amount, LocalDateTime dateTime) {
		this.idClient = idClient;
		this.amount = amount;
		this.dateTime = dateTime;
	}

}
