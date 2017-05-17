package com.fict.services;

import com.fict.entities.Customer;
import com.fict.entities.Role;
import com.fict.repository.CustomerRepository;
import com.fict.repository.RoleRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.hql.internal.ast.tree.BooleanLiteralNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
		String transactionNumber = RandomStringUtils.randomNumeric(16);
		customer.setTransactionNumber(transactionNumber);
		
		return customerRepository.save(customer);
	}

	@Override
	@Transactional
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> findAll(){
    	return (List<Customer>) customerRepository.findAll();
	}
}
