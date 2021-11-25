package com.companyabc.retail.model;


import lombok.Data;
import lombok.NonNull;

@Data
public class ClientDTO {
	
	@NonNull
	private String firstName;
	
	@NonNull
	private String lastName;
}
