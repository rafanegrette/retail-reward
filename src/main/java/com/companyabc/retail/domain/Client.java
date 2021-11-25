package com.companyabc.retail.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NonNull;

@Entity
@Data
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@Size(min = 2, max = 20, message = "First Name must be between 2 and 20 characters")
	private String firstName;
	
	@NonNull
	@Size(min = 2, max = 20, message = "Last Name must be between 2 and 20 characters")
	private String lastName;
}
