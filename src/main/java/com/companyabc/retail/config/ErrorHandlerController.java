package com.companyabc.retail.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ErrorHandlerController {

	private static final String NOT_FOUND_TEXT = "This path is not part of Retail-Reward App. Ask for a valid path.";
	
	private static final String DATA_INTEGRITY_TEXT = "There is an data integrity error, please verify your data/actions and contact support team.";
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public GenericException handleNotFoundError(NoHandlerFoundException ex) {
		return new GenericException("NOT_FOUND", NOT_FOUND_TEXT);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public GenericException handleDataError(DataIntegrityViolationException ex) {
		return new GenericException("DATA_INTEGRITY", DATA_INTEGRITY_TEXT);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GenericException handleArgumentError(IllegalArgumentException ex) {
		return new GenericException("ILLEGAL_ARGUMENT", ex.getMessage());
	}
}
