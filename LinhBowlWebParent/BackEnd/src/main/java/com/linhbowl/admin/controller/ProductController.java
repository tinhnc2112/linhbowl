package com.linhbowl.admin.controller;

import com.linhbowl.admin.FileUploadUtil;
import com.linhbowl.entity.Category;
import com.linhbowl.entity.Product;
import com.linhbowl.admin.exeption.ProductNotFoundExeption;
import com.linhbowl.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public String listAll(Model model) {
        return listByPage(1, null, model);
    }

    @GetMapping("/products/page/{pageNum}")
    public String listByPage(@PathVariable("pageNum") int pageNum, @Param("keyword") String keyword, Model model) {
        Page<Product> page = service.listByPage(pageNum, keyword);
        List<Product> products = page.getContent();
        long startCount = (pageNum - 1) * ProductService.PRODUCT_PER_PAGE + 1;
        long endCount = startCount + ProductService.PRODUCT_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "products/products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        Product product = new Product();
        List<Category> listCategories = service.listCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", listCategories);
        model.addAttribute("pageTitle", "Create new product");
        return "products/product_form";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            product.setPhoto(fileName);
            Product saveProduct = service.save(product);

            String uploadDir = "product-images/" + saveProduct.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            service.save(product);
        }
        ra.addFlashAttribute("message", "The product have been saved successfully.");
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Product product = service.getById(id);
            List<Category> listCategories = service.listCategories();

            model.addAttribute("product", product);
            model.addAttribute("categories", listCategories);
            model.addAttribute("pageTitle", "Edit Product");
            ra.addFlashAttribute("message", "The product has been updated.");
            return "products/product_form";
        } catch (ProductNotFoundExeption e) {
            model.addAttribute("message", e.getMessage());
            return "redirect:/products";
        }
    }

    @GetMapping("/products/detail/{id}")
    public String detailProduct(@PathVariable("id") Integer id, Model model) {
        try {
            Product product = service.getById(id);
            model.addAttribute("product", product);
            return "products/product_detail_modal";
        } catch (ProductNotFoundExeption e) {
            return "redirect:/products";
        }
    }

    @GetMapping("/products/enabled/{id}")
    public String updateEnabledStatus(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.updateEnabledStatus(id);
            String name = service.getById(id).getName();
            String status = service.getEnableById(id) ? "disable" : "enable";
            String message = name + " has been " + status;
            ra.addFlashAttribute("message", message);
        } catch (ProductNotFoundExeption e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            String name = service.getById(id).getName();
            service.deleteProduct(id);
            ra.addFlashAttribute("message", name + " has been deleted successfully.");
        } catch (ProductNotFoundExeption e) {
            ra.addAttribute("message", e.getMessage());
        }
        return "redirect:/products";
    }
}
