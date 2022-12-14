package com.linhbowl.site.controller;

import com.linhbowl.entity.Customer;
import com.linhbowl.site.Utility;
import com.linhbowl.site.exception.CustomerNotFoundException;
import com.linhbowl.site.service.CustomerService;
import com.linhbowl.site.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/cart/add/{productId}/{quantity}")
    public String addProductToCart(@PathVariable("productId") Integer productId,
                                   @PathVariable("quantity") Integer quantity, HttpServletRequest request){
        try {
            Customer customer = getAuthenticatedCustomer(request);
            Integer updatedQuantity = cartService.addProduct(productId, quantity, customer);
            return updatedQuantity + " item(s) of this product were added to your shopping cart.";
        } catch (CustomerNotFoundException ex){
            return "login";
        }
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if (email == null){
            throw new CustomerNotFoundException("No authenticated customer");
        }

        return customerService.getCustomerByEmail(email);
    }

    @PostMapping("/cart/update/{productId}/{quantity}")
    public String updateQuantity(@PathVariable("productId") Integer productId,
                                   @PathVariable("quantity") Integer quantity, HttpServletRequest request){
        try {
            Customer customer = getAuthenticatedCustomer(request);
            float subTotal = cartService.updateQuantity(productId, quantity, customer);

            return String.valueOf(subTotal);
        } catch (CustomerNotFoundException ex){
            return "You must login to change quantity of product.";
        }
    }

    @DeleteMapping("/cart/remove/{productId}")
    public String removeProduct(@PathVariable("productId") Integer productId, HttpServletRequest request){
        try{
            Customer customer = getAuthenticatedCustomer(request);
            cartService.removeProduct(productId, customer);
            return "";
        }catch(CustomerNotFoundException e){
            return "You must login to remove product.";
        }
    }
}
