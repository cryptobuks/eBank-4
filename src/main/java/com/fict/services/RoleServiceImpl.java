package com.fict.services;

import com.fict.entities.Role;
import com.fict.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
	
    @Autowired
    private RoleRepository roleRepository;
	
	@Override
	public Role findRoleById(Long id) {
		return roleRepository.findRoleById(id);
	}

}
