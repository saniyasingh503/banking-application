package com.nagarro.bench.assignment.accountService.service;

import com.nagarro.bench.assignment.accountService.model.Account;
import com.nagarro.bench.assignment.accountService.model.AccountDetails;

public interface AccountService {
	
	Account createCustomerAccount(Account accountDetails);
	Double getBalance(String accountNumber);
	Account getAccountDetailsByCustomerId(String customerId);
	Account addMoneyToAccount(String customerId, Double amount);
	Account withdrawMoneyFromAccount(String customerId, Double amount);
	String deleteCustomerAccount(String customerId);
	AccountDetails getAccountandCustomerDetails(String customerId);

}
