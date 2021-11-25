package com.companyabc.retail.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@ManyToOne
	private Client client;
	@NonNull
	private BigDecimal amount;
	@NonNull
	private LocalDateTime dateTime;
	
	public Transaction (Client client, BigDecimal amount, LocalDateTime dateTime) {
		this.client = client;
		this.amount = amount;
		this.dateTime = dateTime;
	}
}
