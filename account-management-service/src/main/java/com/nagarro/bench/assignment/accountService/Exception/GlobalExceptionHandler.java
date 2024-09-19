package com.nagarro.bench.assignment.accountService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception){
		ErrorDetails errorDetails = new ErrorDetails("Not Found", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccountAlreadyExistException.class)
	public ResponseEntity<ErrorDetails> handleAccountAlreadyExistException(AccountAlreadyExistException exception){
		ErrorDetails errorDetails = new ErrorDetails("Account Conflict", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InSufficientFundsException.class)
	public ResponseEntity<ErrorDetails> handleInSufficientFundsException(InSufficientFundsException exception){
		ErrorDetails errorDetails = new ErrorDetails("Bad Request", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception){
		ErrorDetails errorDetails = new ErrorDetails("Internal Server Error", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
