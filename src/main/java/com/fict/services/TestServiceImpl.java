package com.fict.services;

import com.fict.entities.TestEntity;
import com.fict.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by stevo on 4/27/17.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository testRepository;

    @Override
    public TestEntity findTestEntityById(Long id) {
        return testRepository.findTestEntityById(id);
    }
}
