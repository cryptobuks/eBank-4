package com.fict.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fict.entities.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {


}
