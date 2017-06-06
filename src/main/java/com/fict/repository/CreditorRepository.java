package com.fict.repository;

import com.fict.entities.Creditor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditorRepository extends CrudRepository<Creditor, Long> {

    Creditor findCreditorById(Long id);
    
    Creditor findCreditorByTransactionNumber(String transactionNumber);
}
