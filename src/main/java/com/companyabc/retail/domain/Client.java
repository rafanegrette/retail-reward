package com.companyabc.retail.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Client {
	private long id;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
}
