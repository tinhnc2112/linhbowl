package com.linhbowl.admin.controller;

import com.linhbowl.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    @Autowired
    UserService service;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@RequestParam("id") Integer id, @RequestParam("email") String email) {
        return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
