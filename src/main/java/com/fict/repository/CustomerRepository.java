package com.fict.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fict.entities.Customer;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by stevo on 4/27/17.
 */
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {


	Customer findCustomerByEmail(String email);

	Customer findCustomerById(Long id);
	
	Customer findCustomerByTransactionNumber(String transactionNumber);

	Customer findCustomerByEmbg(String embg);

}
