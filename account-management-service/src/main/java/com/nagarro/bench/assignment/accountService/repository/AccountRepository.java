package com.nagarro.bench.assignment.accountService.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.bench.assignment.accountService.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Optional<Account> findByCustomerId(String customerId);
	Optional<Account> findByAccountNumber(String accountNumber);

}
