package com.fict.controllers;

import com.fict.entities.Role;
import com.fict.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("admin/role/{id}")
	public Role getRole(@PathVariable Long id) {
		return roleService.findRoleById(id);
    }
	
}
