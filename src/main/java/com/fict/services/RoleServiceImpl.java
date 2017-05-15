package com.fict.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fict.entities.Role;
import com.fict.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	
    @Autowired
    private RoleRepository roleRepository;
	
	@Override
	public Role findRoleById(Long id) {
		return roleRepository.findRoleById(id);
	}

}
