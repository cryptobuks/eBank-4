package com.fict.repository;

import com.fict.entities.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {


	Customer findCustomerByEmail(String email);

	Customer findCustomerById(Long id);
	
	Customer findCustomerByTransactionNumber(String transactionNumber);

	Customer findCustomerByEmbg(String embg);

}
