package com.fict.services;

import com.fict.entities.Creditor;
import com.fict.repository.CreditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by stevo on 4/30/17.
 */
@Service
public class CreditorServiceImpl implements CreditorService {

    @Autowired
    private CreditorRepository creditorRepository;

    @Override
    public Creditor findCreditorById(Long id) {
        return creditorRepository.findCreditorById(id);
    }
}
