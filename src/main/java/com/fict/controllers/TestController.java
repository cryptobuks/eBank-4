package com.fict.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fict.entities.Creditor;
import com.fict.entities.Customer;
import com.fict.entities.Order;
import com.fict.services.CreditorService;
import com.fict.services.CustomerService;
import com.fict.services.OrderService;

/**
 * Created by stevo on 4/26/17.
 */
@RestController
public class TestController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	@Autowired
   	private CreditorService creditorService;

	@RequestMapping("/admin/customer/{id}")
	public Customer getCustomer(@PathVariable Long id) {
		return customerService.findCustomerById(id);
	}

	@RequestMapping("/order/{id}")
	public Order getOrder(@PathVariable Long id) {
		return orderService.findOrderById(id);
	}

	@RequestMapping("/creditor/{id}")
	public Creditor getCreditor(@PathVariable Long id) {
		return creditorService.findCreditorById(id);
    }

	@RequestMapping("/customer/order/{id}")
	public List<Order> getOrders(@PathVariable Long id) {
		Customer customer = customerService.findCustomerById(id);
		return orderService.findOrdersByCustomer(customer);
	}

	@RequestMapping("/creditor/order/{id}")
    public List<Order> getOrdersByCreditor(@PathVariable Long id) {
		Creditor creditor = creditorService.findCreditorById(id);
		return orderService.findOrdersByCreditor(creditor);
   	}

}
