package com.linhbowl.admin.repository;

import com.linhbowl.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.name =?1")
    Product getByName(String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    Page<Product> listByPage(String keyword, Pageable pageable);

    @Query("UPDATE Product p SET p.enabled =?2 WHERE p.id =?1")
    @Modifying
    void updateEnableStatus(Integer id, boolean enabled);

    Long countById(Integer id);
}
