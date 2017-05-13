package com.fict.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fict.entities.Customer;

/**
 * Created by stevo on 4/27/17.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {


	Customer findCustomerByEmail(String email);

	Customer findCustomerById(Long id);

}
