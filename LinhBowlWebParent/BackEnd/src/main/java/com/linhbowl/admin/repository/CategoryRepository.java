package com.linhbowl.admin.repository;

import com.linhbowl.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.name =?1")
    Category getByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
    Page<Category> listByPage(String keyword, Pageable pageable);

    @Query("UPDATE Category c SET c.enabled =?2 WHERE c.id =?1")
    @Modifying
    void updateEnableStatus(Integer id, boolean enabled);
}
