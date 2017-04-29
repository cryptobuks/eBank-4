package com.fict.services;

import com.fict.entities.Creditor;
import com.fict.entities.Customer;
import com.fict.entities.Order;
import com.fict.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by stevo on 4/30/17.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findOrdersByCustomer(Customer customer) {
        return orderRepository.findOrdersByCustomer(customer);
    }

    @Override
    public List<Order> findOrdersByCreditor(Creditor creditor) {
        return orderRepository.findOrdersByCreditor(creditor);
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findOrderById(id);
    }
}
