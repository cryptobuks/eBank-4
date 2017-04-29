package com.fict.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fict.entities.Customer;
import com.fict.services.TestService;

/**
 * Created by stevo on 4/26/17.
 */
@RestController
public class TestController {

    @Autowired
    private TestService service;


    @RequestMapping("/{id}")
	public String sayHello(@PathVariable Long id) {
		Customer customer = service.findTestEntityById(id);
		return customer.getFirstName();
    }
    
}
