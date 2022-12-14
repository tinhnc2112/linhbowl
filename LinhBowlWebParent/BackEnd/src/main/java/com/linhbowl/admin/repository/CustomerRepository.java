package com.linhbowl.admin.repository;

import com.linhbowl.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {

    Customer findByEmail(String email);

    @Query("UPDATE Customer c SET c.enabled = true, c.verificationCode = null WHERE c.id =?1")
    @Modifying
    void enable(Integer id);

    @Query("UPDATE Customer c SET c.enabled =?1 WHERE c.id =?2")
    @Modifying
    void updateEnabledStatus(boolean enabled, Integer id);

    @Query("SELECT c from Customer c WHERE CONCAT(c.email, ' ', c.firstName, ' ', c.lastName, ' ', c.phoneNumber, ' ', c.address) LIKE %?1%")
    Page<Customer> listByPage(String keyword, Pageable pageable);
}
