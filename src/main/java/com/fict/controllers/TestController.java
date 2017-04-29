package com.fict.controllers;

import com.fict.entities.Order;
import com.fict.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fict.entities.Customer;
import com.fict.services.TestService;

import java.util.List;

/**
 * Created by stevo on 4/26/17.
 */
@RestController
public class TestController {
	
	@Autowired
	private TestService service;

	@Autowired
	private OrderService orderService;

	@RequestMapping("/{id}")
	public Customer sayHello(@PathVariable Long id) {
		Customer customer = service.findTestEntityById(id);
		return customer;
	}

	@RequestMapping("/order/{id}")
	public Order getOrder(@PathVariable Long id) {
		return orderService.findOrderById(id);
	}

	@RequestMapping("/order/customer/{id}")
	public List<Order> getOrders(@PathVariable Long id) {
		Customer customer = service.findTestEntityById(3L);
		return orderService.findOrderByCustomer(customer);
	}
    
}
