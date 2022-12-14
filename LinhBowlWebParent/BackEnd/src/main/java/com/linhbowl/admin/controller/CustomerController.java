package com.linhbowl.admin.controller;

import com.linhbowl.entity.Customer;
import com.linhbowl.admin.exeption.CustomerNotFoundExeption;
import com.linhbowl.admin.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public String listFirstPage(Model model) {
        return listByPage(1, null, model);
    }

    @GetMapping("/customers/page/{pageNum}")
    public String listByPage(@PathVariable("pageNum") int pageNum, @Param("keyword") String keyword, Model model) {
        Page<Customer> page = customerService.listByPage(pageNum, keyword);
        List<Customer> customers = page.getContent();
        long startCount = (pageNum - 1) * CustomerService.CUSTOMER_PER_PAGE + 1;
        long endCount = startCount + CustomerService.CUSTOMER_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("customers", customers);
        model.addAttribute("keyword", keyword);

        return "customers/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            String name = customerService.getById(id).getFullName();
            customerService.deleteCustomer(id);
            ra.addFlashAttribute("message", "Customer [" + name + "] has been deleted.");
        } catch (CustomerNotFoundExeption e) {
            ra.addAttribute("message", e.getMessage());
        }
        return "redirect:/customers";
    }

    @PostMapping("/customers/save")
    public String saveCustomer(Customer customer, RedirectAttributes ra) {
        customerService.saveCustomer(customer);
        ra.addFlashAttribute("message", "Customer has saved successfully.");

        return "redirect:/customers";
    }

    @GetMapping("/customers/enabled/{id}")
    public String updateCustomerEnabledStatus(@PathVariable("id") Integer id, RedirectAttributes ra) throws CustomerNotFoundExeption {
        customerService.updateCustomerEnabledStatus(id);
        String status = customerService.getEnableById(id) ? "disabled" : "enabled";
        ra.addFlashAttribute("message", "Customer has been " + status);

        return "redirect:/customers";
    }
}
