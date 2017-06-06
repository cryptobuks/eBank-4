package com.fict.controllers;

import com.fict.entities.Order;
import com.fict.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	
	
	@RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public Order saveOrder(@RequestBody Order order, Principal principal){
		
        return orderService.saveOrder(order, principal);
    }
	
	@RequestMapping("/orders/user")
	public Page<Order> getOrdersByUser(@RequestParam(name = "p", defaultValue = "1") int pageNumber, 
			@RequestParam(name = "l", defaultValue = "5")int limit, Principal principal) {

		return orderService.findOrdersByUser(pageNumber, limit, principal);
	}
	
	@RequestMapping("admin/order/{id}")
	public Order getOrder(@PathVariable Long id) {
		
		return orderService.findOrderById(id);
	}

   	@RequestMapping("/admin/orders")
	public Page<Order> getAllOrders(@RequestParam(name = "p", defaultValue = "1") int pageNumber, 
			@RequestParam(name = "l", defaultValue = "5") int limit){

    	return orderService.findAll(pageNumber, limit);
	}

	@RequestMapping(value = "/admin/order/edit", method = RequestMethod.PUT)
	public Order editOrder(@RequestBody Order order) {
		
		return orderService.editOrder(order);
	}
}
