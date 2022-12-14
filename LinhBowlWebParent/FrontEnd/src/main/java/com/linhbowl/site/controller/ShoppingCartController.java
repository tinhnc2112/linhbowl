package com.linhbowl.site.controller;

import com.linhbowl.entity.CartItem;
import com.linhbowl.entity.Customer;
import com.linhbowl.site.Utility;
import com.linhbowl.site.exception.CustomerNotFoundException;
import com.linhbowl.site.service.CustomerService;
import com.linhbowl.site.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/cart")
    public String listCartItem(Model model, HttpServletRequest request){
        try{
            Customer customer = getAuthenticatedCustomer(request);
            List<CartItem> cartItems = cartService.listCartItems(customer);
            float estimatedTotal = 0.0F;
            for(CartItem item : cartItems){
                estimatedTotal += item.getSubTotal();
            }
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("estimatedTotal", estimatedTotal);
            return "cart/shopping_cart";
        }catch (CustomerNotFoundException e){
            return "You must login to add this product to cart.";
        }

    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) throws CustomerNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if (email == null){
            throw new CustomerNotFoundException("No authenticated customer");
        }

        return customerService.getCustomerByEmail(email);
    }
}
