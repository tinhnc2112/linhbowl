package com.linhbowl.site.repository;

import com.linhbowl.entity.CartItem;
import com.linhbowl.entity.Customer;
import com.linhbowl.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Integer> {

    List<CartItem> findByCustomer(Customer customer);

    CartItem findByCustomerAndProduct(Customer customer, Product product);

    @Modifying
    @Query("UPDATE CartItem c SET c.quantity =?1 WHERE c.customer =?2 AND c.product.id =?3")
    void updateQuantity(Integer quantity, Customer customer, Integer productId);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.customer.id =?1 AND c.product.id =?2")
    void deleteByCustomerAndProduct(Integer customerID, Integer productID);
}
