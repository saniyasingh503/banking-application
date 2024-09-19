package com.nagarro.bench.assignment.customerService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.bench.assignment.customerService.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	
	Optional<Customer> findByEmail(String email);

}
