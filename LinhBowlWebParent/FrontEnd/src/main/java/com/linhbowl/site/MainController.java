package com.linhbowl.site;

import com.linhbowl.entity.Product;
import com.linhbowl.site.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Controller
public class MainController {

    @Autowired private ProductService productService;
    @GetMapping("")
    public String viewHomePage(Model model){
        List<Product> bestSellers = productService.listBestSeller();
        model.addAttribute("bestSellers",bestSellers);
        return "index";
    }

    @GetMapping("/about")
    public String viewAbout(){
        return "about";
    }

    @GetMapping("/contact")
    public String viewContact(){
        return "contact";
    }

    @GetMapping("/menu")
    public String viewMenu(){
        return "menu";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            return "login";
        }
        return "redirect:/";
    }
}
