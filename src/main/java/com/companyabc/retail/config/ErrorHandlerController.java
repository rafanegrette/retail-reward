package com.companyabc.retail.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ErrorHandlerController {

	private final String NOT_FOUND_TEXT = "This path is not part of Retail-Reward App. Ask for a valid path.";
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleNotFoundError(NoHandlerFoundException ex) {
		return NOT_FOUND_TEXT;
	}
}
