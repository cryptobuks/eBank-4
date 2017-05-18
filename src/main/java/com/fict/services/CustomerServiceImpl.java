package com.fict.services;

import com.fict.entities.Customer;
import com.fict.entities.Role;
import com.fict.repository.CustomerRepository;
import com.fict.repository.RoleRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.hql.internal.ast.tree.BooleanLiteralNode;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

/**
 * Created by stevo on 4/30/17.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public Customer findCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }
    
	@Override
	@Transactional
	public Customer registerCustomer(Customer customer) {
		
		Role role = roleRepository.findRoleByName("NORMAL");
		
		customer.setActive(Boolean.TRUE);
		customer.setBalance(0.0);
		customer.setRole(role);
		
		String transactionNumber;
		
		do {
			
			transactionNumber = RandomStringUtils.randomNumeric(16);
			
		} while(customerRepository.findCustomerByTransactionNumber(transactionNumber) != null);
		
		customer.setTransactionNumber(transactionNumber);
		
		return customerRepository.save(customer);
	}
	
	private void mapCustomer(Customer customer, Customer toSaveCustomer){
    	
    	String roleName = customer.getRole().getName();
    	Role role = roleRepository.findRoleByName(roleName);
    	
    	toSaveCustomer.setRole(role);
    	toSaveCustomer.setActive(customer.isActive());
    	toSaveCustomer.setFirstName(customer.getFirstName());
    	toSaveCustomer.setLastName(customer.getLastName());
		toSaveCustomer.setEmail(customer.getEmail());
		
	}

	@Override
	@Transactional
	public Customer saveCustomer(Customer customer) {
		
		Customer toSaveCustomer = customerRepository.findCustomerById(customer.getId());
		
    	mapCustomer(customer, toSaveCustomer);
    	
		return customerRepository.save(toSaveCustomer);
	}

	@Override
	public Page<Customer> findAll(int pageNumber, int limit){
		PageRequest request = new PageRequest(pageNumber-1,limit, Sort.Direction.ASC,"id");

		return customerRepository.findAll(request);
	}

	@Override
	public Double convertCurrency(String first, String second, Double value) {
		
		MonetaryAmount amount = Money.of(value, first);
		
		CurrencyConversion conversion = MonetaryConversions
                .getConversion(second);
		
		MonetaryAmount convertedAmount = amount.with(conversion);
		
		Double result = convertedAmount.getNumber().doubleValue();
		
		return result;
	}
}
