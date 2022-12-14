package com.linhbowl.site;

import com.linhbowl.entity.CartItem;
import com.linhbowl.entity.Customer;
import com.linhbowl.entity.Product;
import com.linhbowl.site.repository.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CartItemRepoTest {

    @Autowired
    private CartItemRepository repo;
    @Autowired private TestEntityManager entityManager;

    @Test
    public void testSaveCartItem(){
        int cusId = 1;
        int productId = 2;
        Customer cus = entityManager.find(Customer.class, cusId);
        Product pr = entityManager.find(Product.class, productId);
        CartItem cartItem = new CartItem();
        cartItem.setCustomer(cus);
        cartItem.setProduct(pr);
        cartItem.setQuantity(2);
        repo.save(cartItem);
    }

    @Test
    public void testFindByCus(){
        int cusId = 1;
        Customer cus = entityManager.find(Customer.class, cusId);
        List<CartItem> list = repo.findByCustomer(cus);
        list.forEach(System.out::println);
    }
    @Test
    public void findByCusAndProduct(){
        int cusId = 1;
        int productId = 2;
        Customer cus = entityManager.find(Customer.class, cusId);
        Product pr = entityManager.find(Product.class, productId);
        CartItem cartItem = repo.findByCustomerAndProduct(cus, pr);
        System.out.println(cartItem);
    }
    @Test
    public void updateQuantity(){
        int cusId = 1;
        int productId = 2;
        Customer cus = entityManager.find(Customer.class, cusId);
        Integer prId = entityManager.find(Product.class, productId).getId();
        int quantity = 4;
        repo.updateQuantity(quantity, cus, prId);
    }
    @Test
    public void delete(){
        int cusId = 1;
        int productId = 2;
        repo.deleteByCustomerAndProduct(cusId, productId);
    }
}
