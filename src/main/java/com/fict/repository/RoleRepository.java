package com.fict.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fict.entities.Role;

/**
 * @author stojan
 *
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
