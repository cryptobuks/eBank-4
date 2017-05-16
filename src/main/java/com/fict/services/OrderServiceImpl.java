package com.fict.services;

import com.fict.entities.Creditor;
import com.fict.entities.Customer;
import com.fict.entities.Order;
import com.fict.repository.CreditorRepository;
import com.fict.repository.CustomerRepository;
import com.fict.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

/**
 * Created by stevo on 4/30/17.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CreditorRepository creditorRepository;

    @Override
    public List<Order> findOrdersByCustomer(Customer customer) {
        return orderRepository.findOrdersByCustomer(customer);
    }

    @Override
    public List<Order> findOrdersByCreditor(Creditor creditor) {
        return orderRepository.findOrdersByCreditor(creditor);
    }

    @Override
    public List<Order> findOrdersByCustomerEmail(String email) {
        return orderRepository.findOrdersByCustomerEmail(email);
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findOrderById(id);
    }

    @Override
    @Transactional
    public Order saveOrder(Order order, Principal principal){
    	
        Customer customer = customerRepository.findCustomerByEmail(principal.getName());
        Double customerBalance = customer.getBalance() - order.getAmount();
        customer.setBalance(Math.max(customerBalance, 0.0));
        customerRepository.save(customer);
        
        order.setCustomer(customer);
        
        String creditorTransactionNumber = order.getCreditor().getTransactionNumber();
        
        Creditor creditor =  creditorRepository.findCreditorByTransactionNumber(creditorTransactionNumber);
        
        if (creditor == null){
        	creditor = creditorRepository.save(order.getCreditor());
        }
        
        order.setCreditor(creditor);
        
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order editOrder(Order order){
        return orderRepository.save(order);
    }
}
