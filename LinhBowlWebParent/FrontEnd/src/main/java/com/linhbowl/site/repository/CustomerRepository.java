package com.linhbowl.site.repository;

import com.linhbowl.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c from Customer c WHERE c.email = ?1")
    Customer findByEmail(String email);

    @Query("SELECT c from Customer c WHERE c.verificationCode = ?1")
    Customer findByVerificationCode(String code);

    @Query("UPDATE Customer c SET c.enabled = true, c.verificationCode = null WHERE c.id = ?1")
    @Modifying
    void enable(Integer id);

}
