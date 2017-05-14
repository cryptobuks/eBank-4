package com.fict.services;

import com.fict.entities.Creditor;
import com.fict.entities.Customer;
import com.fict.entities.Order;

import java.util.List;

/**
 * Created by stevo on 4/30/17.
 */
public interface OrderService {

    List<Order> findOrdersByCustomer(Customer customer);
    List<Order> findOrdersByCustomerEmail(String email);
    Order findOrderById(Long id);
    List<Order> findOrdersByCreditor(Creditor creditor);
}
