package com.nagarro.bench.assignment.customerService.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.bench.assignment.customerService.exception.UserAlreadyExistException;
import com.nagarro.bench.assignment.customerService.exception.UserNotFoundException;
import com.nagarro.bench.assignment.customerService.model.Customer;
import com.nagarro.bench.assignment.customerService.repository.CustomerRepository;
import com.nagarro.bench.assignment.customerService.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;


	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customerList = customerRepository.findAll();
		return customerList;
	}

	@Override
	public Customer getCustomerById(String customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new UserNotFoundException("User is not found by id: " + customerId));
	}

	@Override
	public Customer addCustomer(Customer customer) {
		//find if customer exist having same email
		Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());
		if(existingCustomer.isPresent()) 
			throw new UserAlreadyExistException("User already exist with this email: " + customer.getEmail());
		
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer, String id) {
		Optional<Customer> existingCustomer = customerRepository.findById(id);
		
		if(!existingCustomer.isPresent()) 
			throw new UserNotFoundException("User id not found by id: " + id);
		
		Customer oldCustomer = existingCustomer.get();
		oldCustomer.setFirstName(customer.getFirstName());
		oldCustomer.setLastName(customer.getLastName());
		oldCustomer.setEmail(customer.getEmail());
		return customerRepository.save(oldCustomer);
	}

	@Override
	public void deleteCustomer(String customerId) {
            customerRepository.deleteById(customerId);;
	}
}
