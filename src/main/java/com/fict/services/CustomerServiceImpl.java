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
	
	private void mapCustomer(Customer customer, Customer savedCustomer){
		
    	Customer checkValidCustomer = customerRepository.findCustomerByEmail(customer.getEmail());
    	
    	if (checkValidCustomer != null && !checkValidCustomer.equals(savedCustomer)){
    		throw new DataIntegrityViolationException("Email is already taken.");
    	}
    	
    	String roleName = customer.getRole().getName();
    	Role role = roleRepository.findRoleByName(roleName);
    	
    	savedCustomer.setRole(role);
		savedCustomer.setActive(customer.isActive());
		savedCustomer.setFirstName(customer.getFirstName());
		savedCustomer.setLastName(customer.getLastName());
		savedCustomer.setEmail(customer.getEmail());
		
	}

	@Override
	@Transactional
	public Customer saveCustomer(Customer customer) {
		
		Customer savedCustomer = customerRepository.findCustomerById(customer.getId());
		
    	mapCustomer(customer, savedCustomer);
    	
		return customerRepository.save(savedCustomer);
	}

	@Override
	public List<Customer> findAll(){
    	return (List<Customer>) customerRepository.findAll();
	}

	@Override
	public Double convertCurrency(String first, String second, Double value) {
		
		MonetaryAmount amount = Money.of(value, first);
		
		CurrencyConversion conversion = MonetaryConversions
                .getConversion(second);
		
		MonetaryAmount convertedAmountUSD = amount.with(conversion);
		
		Double result = convertedAmountUSD.getNumber().doubleValue();
		
		return result;
	}
}
