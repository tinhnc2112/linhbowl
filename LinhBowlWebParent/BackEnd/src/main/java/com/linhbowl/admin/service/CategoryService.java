package com.linhbowl.admin.service;

import com.linhbowl.admin.exeption.CategoryNotFoundExeption;
import com.linhbowl.entity.Category;
import com.linhbowl.admin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CategoryService {

    public static final int CATEGORY_PER_PAGE = 4;

    @Autowired
    private CategoryRepository repo;

    public Category save(Category category) {
        return repo.save(category);
    }

    public List<Category> listAll() {
        return (List<Category>) repo.findAll();
    }

    public Page<Category> listByPage(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, CATEGORY_PER_PAGE);
        if (keyword != null) {
            return repo.listByPage(keyword, pageable);
        } else
            return repo.findAll(pageable);
    }

    public Category getById(Integer id) throws CategoryNotFoundExeption {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new CategoryNotFoundExeption("Could not find any category with ID: " + id);
        }
    }

    public void updateEnabledStatus(Integer id) {
        boolean enable;
        if (repo.findById(id).get().isEnabled()) {
            enable = false;
        } else {
            enable = true;
        }
        repo.updateEnableStatus(id, enable);
    }

    public boolean getEnableById(Integer id) {
        return repo.findById(id).get().isEnabled();
    }

    public void deleteCategory(Integer id) throws CategoryNotFoundExeption {
        boolean existsById = repo.existsById(id);
        if (!existsById) {
            throw new CategoryNotFoundExeption("Could not find any category with ID: " + id);
        }
        repo.deleteById(id);
    }

    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id == null || id == 0);
        Category categoryByName = repo.getByName(name);
        if (isCreatingNew) {
            if (categoryByName != null) {
                return "DuplicateName";
            }
        }
        return "OK";
    }

}
