package com.linhbowl.admin.controller;

import com.linhbowl.admin.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {

    @Autowired
    private CustomerService service;

    @PostMapping("/customers/check_unique_email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email){
        return service.checkUniqueEmail(id, email);
    }
}
