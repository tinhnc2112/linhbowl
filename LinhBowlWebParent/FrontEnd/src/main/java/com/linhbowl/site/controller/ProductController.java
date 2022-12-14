package com.linhbowl.site.controller;

import com.linhbowl.entity.Product;
import com.linhbowl.site.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;
    @GetMapping("/products")
    public String viewProducts(Model model){
        List<Product> products = service.listEnabledProducts();
        model.addAttribute("products", products);
        return "products/products";
    }

    @GetMapping("/products/{productName}")
    public String viewProduct(@PathVariable("productName")String name, Model model){
        Product product = service.findByName(name);
        model.addAttribute("product", product);
        return "products/product_detail";
    }
}
