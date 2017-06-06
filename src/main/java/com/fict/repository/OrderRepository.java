package com.fict.repository;

import com.fict.entities.Creditor;
import com.fict.entities.Customer;
import com.fict.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

	Page<Order> findOrdersByCustomer(Customer customer, Pageable pageable);

	List<Order> findOrdersByCustomerEmail(String email);

	Order findOrderById(Long id);

	List<Order> findOrdersByCreditor(Creditor creditor);


}
