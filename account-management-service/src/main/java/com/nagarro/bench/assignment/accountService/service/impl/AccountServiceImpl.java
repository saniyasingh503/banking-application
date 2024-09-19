package com.nagarro.bench.assignment.accountService.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.bench.assignment.accountService.Exception.AccountAlreadyExistException;
import com.nagarro.bench.assignment.accountService.Exception.InSufficientFundsException;
import com.nagarro.bench.assignment.accountService.Exception.ResourceNotFoundException;
import com.nagarro.bench.assignment.accountService.model.Account;
import com.nagarro.bench.assignment.accountService.model.AccountDetails;
import com.nagarro.bench.assignment.accountService.model.Constants;
import com.nagarro.bench.assignment.accountService.model.Customer;
import com.nagarro.bench.assignment.accountService.repository.AccountRepository;
import com.nagarro.bench.assignment.accountService.service.AccountService;
import com.netflix.discovery.EurekaClient;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Override
	public Account createCustomerAccount(Account accountDetails) {
		Customer customerDetails = getCustomerDetailsById(accountDetails.getCustomerId());
		
		if(Objects.isNull(customerDetails))
			throw new ResourceNotFoundException("User is not found by id: " + accountDetails.getCustomerId());
		
		Optional<Account> account = accountRepository.findByCustomerId(accountDetails.getCustomerId());
		
		if(account.isPresent())
			throw new AccountAlreadyExistException("Account already exist on the server");
		
		Integer genereatedAccountNumber = Integer.parseInt(Constants.ACC_PREFIX) + 1;
		accountDetails.setAccountNumber(genereatedAccountNumber.toString());
		if(accountDetails.getAmount() == null)
			accountDetails.setAmount(0.0);
		
		return accountRepository.save(accountDetails);

	}
	
	@Override
	public Double getBalance(String accountNumber) {
		Optional<Account> accountDetails = accountRepository.findByAccountNumber(accountNumber);
		
		if(!accountDetails.isPresent())
			throw new ResourceNotFoundException("Customer account is not found");
		
		return accountDetails.get().getAmount();
	}

	@Override
	public Account getAccountDetailsByCustomerId(String customerId) {
		Optional<Account> account = accountRepository.findByCustomerId(customerId);
		
		if(account.isEmpty())
			throw new ResourceNotFoundException("Customer account is not found");
		
		return account.get();
	}

	@Override
	public Account addMoneyToAccount(String customerId, Double amount) {
		Customer customer = getCustomerDetailsById(customerId);
		
		if(Objects.isNull(customer)) 
			throw new ResourceNotFoundException("User is not found by id: " + customerId);
		
		Account accountDetails = getAccountDetailsByCustomerId(customerId);
		
		accountDetails.setAmount(accountDetails.getAmount() + amount);
		return accountRepository.save(accountDetails);
	}

	@Override
	public Account withdrawMoneyFromAccount(String customerId, Double amount) {
		Customer customer = getCustomerDetailsById(customerId);
		
		if(Objects.isNull(customer)) 
			throw new ResourceNotFoundException("User is not found by id: " + customerId);
		
		Account accountDetails = getAccountDetailsByCustomerId(customerId);
		
		if(accountDetails.getAmount().compareTo(amount) < 0) 
			throw new InSufficientFundsException("Insufficient balance in an account");
		
		accountDetails.setAmount(accountDetails.getAmount() - amount);
		return accountRepository.save(accountDetails);
	}

	@Override
	public AccountDetails getAccountandCustomerDetails(String customerId) {
		Customer customerDetails = getCustomerDetailsById(customerId);
		
		if(Objects.isNull(customerDetails)) 
			throw new ResourceNotFoundException("User is not found by id: " + customerId);
		
		Account accountDetails = getAccountDetailsByCustomerId(customerId);
		AccountDetails allDetails = new AccountDetails(accountDetails, customerDetails);
		return allDetails;	
	}
	
	public Customer getCustomerDetailsById(String customerId) {
		Customer customerDetails = null;
		try {
			String customerServiceBaseUrl = eurekaClient.getNextServerFromEureka(Constants.CUSTOMER_SERVICE_HOSTNAME, false)
					.getHomePageUrl() + "api/customers/";
			customerDetails = restTemplate.getForObject(customerServiceBaseUrl + customerId, Customer.class);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return customerDetails;
	}

	@Override
	public String deleteCustomerAccount(String customerId) {
		Customer customerDetails = getCustomerDetailsById(customerId);
		
		if(Objects.isNull(customerDetails))
			throw new ResourceNotFoundException("User is not found by id: "+ customerId);
		
		Optional<Account> accountDetails = accountRepository.findByCustomerId(customerId);
		
		if(!accountDetails.isPresent())
			throw new ResourceNotFoundException("Customer account is not found");
		
		accountRepository.deleteById(accountDetails.get().getId());
		return "Customer account is deleted successfully";
	}
	

}
