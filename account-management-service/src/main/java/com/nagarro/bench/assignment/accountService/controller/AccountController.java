package com.nagarro.bench.assignment.accountService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.bench.assignment.accountService.model.Account;
import com.nagarro.bench.assignment.accountService.model.AccountDetails;
import com.nagarro.bench.assignment.accountService.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping()
	public ResponseEntity<Account> createAccount(@RequestBody Account accountDetails) {
		return new ResponseEntity<Account>(accountService.createCustomerAccount(accountDetails), HttpStatus.CREATED);
	}
	
	@PostMapping("/addMoney/{customerId}")
	public ResponseEntity<Object> addMoneyToAccount(@PathVariable("customerId") String customerId,
			@RequestParam("amount") Double amount){
		if(amount <= 0) 
			return new ResponseEntity<Object>("Please enter valid amount", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<Object>(accountService.addMoneyToAccount(customerId, amount), HttpStatus.OK);
			
	}
	
	@PostMapping("/withdrawMoney/{customerId}")
	public ResponseEntity<Object> withdrawMoneyFromAccount(@PathVariable("customerId") String customerId,
			@RequestParam("amount") Double amount){
		if(amount <= 0) 
			return new ResponseEntity<>("Please enter valid amount", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<Object>(accountService.withdrawMoneyFromAccount(customerId, amount), HttpStatus.OK);
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<AccountDetails> getAccountDetails(@PathVariable("customerId") String customerId) {
		return new ResponseEntity<AccountDetails>(accountService.getAccountandCustomerDetails(customerId),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<String> deleteAccount(@PathVariable("customerId") String customerId) {
		
		String result = accountService.deleteCustomerAccount(customerId);
		
		if(result != null)
			return new ResponseEntity<String>("Account is deleted successfully !!", HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<String>("Account is not deleted !!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
