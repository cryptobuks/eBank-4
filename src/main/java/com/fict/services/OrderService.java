package com.fict.services;

import com.fict.entities.Creditor;
import com.fict.entities.Customer;
import com.fict.entities.Order;

import java.security.Principal;
import java.util.List;

/**
 * Created by stevo on 4/30/17.
 */
public interface OrderService {

    List<Order> findOrdersByUser(Principal principal);

    List<Order> findOrdersByCustomerEmail(String email);

    Order findOrderById(Long id);

    List<Order> findOrdersByCreditor(Creditor creditor);

    Order saveOrder(Order order, Principal principal);

    List<Order> findAll();

    Order editOrder(Order order);
}
