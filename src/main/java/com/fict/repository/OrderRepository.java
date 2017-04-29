package com.fict.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fict.entities.Customer;
import com.fict.entities.Order;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findOrdersByCustomer(Customer customer);
	Order findOrderById(Long id);
}
