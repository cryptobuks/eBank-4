package com.fict.services;

import com.fict.entities.Customer;
import com.fict.entities.Role;
import com.fict.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by stevo on 4/30/17.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    public Customer registerCustomer(Customer customer)
    {
        customer.setBalance(0.0);
        customer.setActive(true);
        customer.setRole(new Role(2L,"NORMAL"));
        customerRepository.save(customer);
        return customer;
    }
}
