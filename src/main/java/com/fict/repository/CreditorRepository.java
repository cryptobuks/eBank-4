package com.fict.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fict.entities.Creditor;

@Repository
public interface CreditorRepository extends CrudRepository<Creditor, Long> {

    Creditor findCreditorById(Long id);

}
