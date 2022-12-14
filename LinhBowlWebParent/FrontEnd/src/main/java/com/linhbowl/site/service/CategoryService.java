package com.linhbowl.site.service;

import com.linhbowl.entity.Category;
import com.linhbowl.site.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public List<Category> listCategories(){
        return repo.findAllEnabled();
    }
}
