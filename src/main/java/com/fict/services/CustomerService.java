package com.fict.services;

import org.springframework.security.core.Authentication;

import com.fict.entities.Customer;

import java.util.List;

/**
 * Created by stevo on 4/27/17.
 */

public interface CustomerService {

	Customer findCustomerById(Long id);

	Customer findCustomerByEmail(String email);

	Customer saveCustomer(Customer customer);

	Customer registerCustomer(Customer customer);

	List<Customer> findAll();
	
	Double convertCurrency(String first, String second , Double value);
}