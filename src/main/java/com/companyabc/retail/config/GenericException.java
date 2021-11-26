package com.companyabc.retail.config;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GenericException {
	private String company = "ABCCompany Limited";
	private String errorType;
	private String errorDescription;
	private LocalDateTime dateTime;
	
	public GenericException(String errorType, String errorDescription) {
		this.errorType = errorType;
		this.errorDescription = errorDescription;
		this.dateTime = LocalDateTime.now();
	}
}
