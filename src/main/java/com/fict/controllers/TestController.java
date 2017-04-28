package com.fict.controllers;

import com.fict.entities.TestEntity;
import com.fict.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by stevo on 4/26/17.
 */
@RestController
public class TestController {

    @Autowired
    private TestService service;


    @RequestMapping("/{id}")
    public String sayHello(@PathVariable int id){
        TestEntity testEntity = service.findTestEntityById(id);
        return testEntity.getFirstname();
    }

}
