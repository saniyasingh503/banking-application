package com.nagarro.bench.assignment.customerService.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;

import com.nagarro.bench.assignment.customerService.model.Constants;
import com.nagarro.bench.assignment.customerService.model.Customer;
import com.nagarro.bench.assignment.customerService.service.CustomerService;
import com.netflix.discovery.EurekaClient;

@Controller
@RequestMapping("/api/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EurekaClient eurekaClient;
	
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return new ResponseEntity<List<Customer>>(customerService.getAllCustomers(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerDetailsById(@PathVariable("id") String customerId) {
		return new ResponseEntity<Customer>(customerService.getCustomerById(customerId), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Customer> addCustomerDetails(@RequestBody Customer customer){
		return new ResponseEntity<Customer>(customerService.addCustomer(customer), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomerDetails(@RequestBody Customer customer,@PathVariable("id") String customerId) {
		return new ResponseEntity<Customer>(customerService.updateCustomer(customer, customerId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable("id") String customerId) {
		Customer customer = customerService.getCustomerById(customerId);
		
		if(customer == null) 
			return new ResponseEntity<>("User is not found by id: " + customerId, HttpStatus.NOT_FOUND);
			
		try {
			Map<String, String> parameter = new HashMap<>();
            parameter.put("customerId", customerId);
            
            String baseUrl = eurekaClient.getNextServerFromEureka(Constants.ACCOUNT_SERVICE_HOSTNAME, false).getHomePageUrl() + "api/accounts/";
            
            restTemplate.delete(baseUrl + customerId, parameter);
		} catch (NotFound e) {
			customerService.deleteCustomer(customerId);
            return new ResponseEntity<>("Customer is deleted successfully!!", HttpStatus.NO_CONTENT);
		} catch (HttpClientErrorException e) {
            return new ResponseEntity<>("Customer account is not found", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Customer account is not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		customerService.deleteCustomer(customerId);
	
		return new ResponseEntity<Object>("Customer details and account is deleted successfully", HttpStatus.NO_CONTENT);
	}

}
