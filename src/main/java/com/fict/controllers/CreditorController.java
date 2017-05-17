package com.fict.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fict.entities.Creditor;
import com.fict.services.CreditorService;
import com.fict.services.CustomerService;
import com.fict.services.OrderService;

@RestController
public class CreditorController {

	@Autowired
   	private CreditorService creditorService;

	@RequestMapping("admin/creditor/{id}")
	public Creditor getCreditor(@PathVariable Long id) {
		return creditorService.findCreditorById(id);
    }
}
