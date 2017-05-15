package com.fict.services;

import org.springframework.security.core.Authentication;

import com.fict.entities.Customer;

/**
 * Created by stevo on 4/27/17.
 */

public interface CustomerService {

	Customer findCustomerById(Long id);
	
	Customer findCustomerByEmail(String email);
	
	Customer saveCustomer(Customer customer);
	
	Customer registerCustomer(Customer customer);
}
