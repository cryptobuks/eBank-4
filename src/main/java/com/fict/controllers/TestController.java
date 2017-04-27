package com.fict.controllers;

import com.fict.entities.TestEntity;
import com.fict.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by stevo on 4/26/17.
 */
@RestController
public class TestController {

    @Autowired
    private TestService service;


    @RequestMapping("/")
    public String sayHello(){
        TestEntity testEntity = service.findTestEntityById(1L);
        System.out.println(testEntity.getName());
        return testEntity.getName();
    }

}
