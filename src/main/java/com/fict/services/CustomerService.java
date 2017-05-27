package com.fict.services;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.fict.entities.Customer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.money.CurrencyUnit;

/**
 * Created by stevo on 4/27/17.
 */

public interface CustomerService {

	Customer findCustomerById(Long id);

	Customer findCustomerByEmail(String email);

	Customer saveCustomer(Customer customer);

	Customer registerCustomer(Customer customer);

	Customer findCustomerByEmbg(String embg);

	Page<Customer> findAll(int pageNumber, int limit);
	
	Double convertCurrency(String from, String to, Double value);
	
	List<CurrencyUnit> getCurrencies();
}