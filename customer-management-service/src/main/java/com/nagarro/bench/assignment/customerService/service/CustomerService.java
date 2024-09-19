package com.nagarro.bench.assignment.customerService.service;

import java.util.List;

import com.nagarro.bench.assignment.customerService.model.Customer;

public interface CustomerService {
	
	List<Customer> getAllCustomers();
	Customer getCustomerById(String customerId);
	Customer addCustomer(Customer customer);
	Customer updateCustomer(Customer customer, String id);
	void deleteCustomer(String customerId);

}
