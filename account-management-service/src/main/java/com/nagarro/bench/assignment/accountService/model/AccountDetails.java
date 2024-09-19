package com.nagarro.bench.assignment.accountService.model;

public class AccountDetails {
	
	private Account accountDetails;
	private Customer customerDetails;
	
	public AccountDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AccountDetails(Account accountDetails, Customer customerDetails) {
		super();
		this.accountDetails = accountDetails;
		this.customerDetails = customerDetails;
	}
	
	public Account getAccountDetails() {
		return accountDetails;
	}
	
	public void setAccountDetails(Account accountDetails) {
		this.accountDetails = accountDetails;
	}
	
	public Customer getCustomerDetails() {
		return customerDetails;
	}
	
	public void setCustomerDetails(Customer customerDetails) {
		this.customerDetails = customerDetails;
	}

}
