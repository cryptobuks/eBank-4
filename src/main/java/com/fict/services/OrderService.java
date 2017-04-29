package com.fict.services;

import com.fict.entities.Customer;
import com.fict.entities.Order;

import java.util.List;

/**
 * Created by stevo on 4/30/17.
 */
public interface OrderService {

    List<Order> findOrderByCustomer(Customer customer);
    Order findOrderById(Long id);

}
