package com.linhbowl.site.repository;

import com.linhbowl.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.enabled = true ORDER BY p.name ASC")
    List<Product> findAllEnabled();

    Optional<Product> findById(Integer id);

    Product findByName(String name);
}
