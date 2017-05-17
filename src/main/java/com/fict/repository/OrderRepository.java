package com.fict.repository;

import com.fict.entities.Creditor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fict.entities.Customer;
import com.fict.entities.Order;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findOrdersByCustomer(Customer customer);

	List<Order> findOrdersByCustomerEmail(String email);

	Order findOrderById(Long id);

	List<Order> findOrdersByCreditor(Creditor creditor);
	
}
