package com.linhbowl.admin.controller;

import com.linhbowl.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {
    @Autowired
    ProductService service;

    @PostMapping("/products/check_unique")
    public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
        return service.checkUnique(id, name);
    }
}
