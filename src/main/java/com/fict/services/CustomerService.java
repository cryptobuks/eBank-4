package com.fict.services;

import com.fict.entities.Customer;

/**
 * Created by stevo on 4/27/17.
 */
public interface CustomerService {

	Customer findCustomerById(Long id);
	Customer findCustomerByEmail(String email);

}
