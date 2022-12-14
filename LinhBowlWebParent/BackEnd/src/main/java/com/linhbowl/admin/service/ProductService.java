package com.linhbowl.admin.service;

import com.linhbowl.admin.repository.CategoryRepository;
import com.linhbowl.admin.repository.ProductRepository;
import com.linhbowl.entity.Category;
import com.linhbowl.admin.exeption.ProductNotFoundExeption;
import com.linhbowl.entity.Product;
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
public class ProductService {

    public static final int PRODUCT_PER_PAGE = 4;
    @Autowired
    private ProductRepository repoProduct;

    @Autowired
    private CategoryRepository categoryRepo;

    public List<Product> listAll() {
        return (List<Product>) repoProduct.findAll();
    }

    public Page<Product> listByPage(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCT_PER_PAGE);
        if (keyword != null) {
            return repoProduct.listByPage(keyword, pageable);
        } else
            return repoProduct.findAll(pageable);
    }

    public List<Category> listCategories() {
        return (List<Category>) categoryRepo.findAll();
    }

    public Product save(Product product) {
        return repoProduct.save(product);
    }

    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id == null || id == 0);
        Product productByName = repoProduct.getByName(name);

        if (isCreatingNew) {
            if (productByName != null) {
                return "Duplicate";
            }
        } else {
            if (productByName != null && productByName.getId() != id) {
                return "Duplicate";
            }
        }
        return "OK";
    }

    public void updateEnabledStatus(Integer id) throws ProductNotFoundExeption {
        boolean enable;
        if (getById(id).isEnabled()) {
            enable = false;
        } else {
            enable = true;
        }
        repoProduct.updateEnableStatus(id, enable);
    }

    public boolean getEnableById(Integer id) {
        return repoProduct.findById(id).get().isEnabled();
    }

    public Product getById(Integer id) throws ProductNotFoundExeption {
        try {
            return repoProduct.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundExeption("Could not find any user with ID " + id);
        }
    }

    public void deleteProduct(Integer id) throws ProductNotFoundExeption {
        Product product = getById(id);
        if (product == null) {
            throw new ProductNotFoundExeption("Could not find any product with id " + id);
        }
        repoProduct.deleteById(id);
    }
}
