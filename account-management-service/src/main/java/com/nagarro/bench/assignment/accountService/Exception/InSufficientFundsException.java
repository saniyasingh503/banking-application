package com.nagarro.bench.assignment.accountService.Exception;

public class InSufficientFundsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InSufficientFundsException(String message) {
		super(message);
	}

}
