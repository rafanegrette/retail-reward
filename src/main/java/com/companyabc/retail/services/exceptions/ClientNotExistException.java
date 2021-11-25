package com.companyabc.retail.services.exceptions;

public class ClientNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4780003138333146694L;

	public ClientNotExistException() {
		super("CLIENT_NOT_EXIST");
	}

}
