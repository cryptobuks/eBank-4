package com.fict.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fict.entities.Customer;
import com.fict.repository.TestRepository;

/**
 * Created by stevo on 4/27/17.
 */
@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestRepository testRepository;

	@Override
	public Customer findTestEntityById(Long id) {
		return testRepository.findCustomerById(id);
	}

}
