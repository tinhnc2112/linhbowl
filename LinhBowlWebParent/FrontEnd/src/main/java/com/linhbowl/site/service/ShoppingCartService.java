package com.linhbowl.site.service;

import com.linhbowl.entity.CartItem;
import com.linhbowl.entity.Customer;
import com.linhbowl.entity.Product;
import com.linhbowl.site.repository.CartItemRepository;
import com.linhbowl.site.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private ProductRepository productRepo;

    public Integer addProduct(Integer productId, Integer quantity, Customer customer){
        Integer updateQuantity = quantity;
        Product product = new Product(productId);
        CartItem item = cartRepo.findByCustomerAndProduct(customer, product);
        if(item != null){
            updateQuantity = item.getQuantity() + quantity;
        } else {
            item = new CartItem();
            item.setCustomer(customer);
            item.setProduct(product);
        }
        if(updateQuantity > 5){
            updateQuantity = 5;
        }
        item.setQuantity(updateQuantity);
        cartRepo.save(item);
        return updateQuantity;
    }

    public List<CartItem> listCartItems(Customer customer){
        return cartRepo.findByCustomer(customer);
    }

    public float updateQuantity(Integer productId, Integer quantity, Customer customer){
        cartRepo.updateQuantity(quantity,customer, productId);
        Product product = productRepo.findById(productId).get();
        float subTotal = product.getPrice() * quantity;
        return subTotal;
    }

    public void removeProduct(Integer productId, Customer customer){
        cartRepo.deleteByCustomerAndProduct(customer.getId(), productId);
    }
}
