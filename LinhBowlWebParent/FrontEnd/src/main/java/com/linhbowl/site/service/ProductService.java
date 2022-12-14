package com.linhbowl.site.service;

import com.linhbowl.entity.Product;
import com.linhbowl.site.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> listBestSeller(){
        List<Product> bestSellers = new ArrayList<>();
        bestSellers.add(repo.findById(1).get());
        bestSellers.add(repo.findById(9).get());
        bestSellers.add(repo.findById(2).get());
        return bestSellers;
    }
    public List<Product> listEnabledProducts(){
        return repo.findAllEnabled();
    }

    public Product findByName(String name){
        return repo.findByName(name);
    }
}
