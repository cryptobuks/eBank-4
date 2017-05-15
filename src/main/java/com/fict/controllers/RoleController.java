package com.fict.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fict.entities.Creditor;
import com.fict.entities.Role;
import com.fict.services.OrderService;
import com.fict.services.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("admin/role/{id}")
	public Role getRole(@PathVariable Long id) {
		return roleService.findRoleById(id);
    }
	
}
