package com.nagarro.bench.assignment.accountService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AccountAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AccountAlreadyExistException(String message) {
		super(message);
	}

}
