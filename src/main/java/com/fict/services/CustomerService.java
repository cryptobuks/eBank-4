package com.fict.services;

import com.fict.entities.Customer;
import org.springframework.data.domain.Page;

import javax.money.CurrencyUnit;
import java.util.List;

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