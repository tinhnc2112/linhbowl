package com.linhbowl.admin.service;

import com.linhbowl.entity.Customer;
import com.linhbowl.admin.exeption.CustomerNotFoundExeption;
import com.linhbowl.admin.repository.CustomerRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CustomerService {

    public static final int CUSTOMER_PER_PAGE = 10;

    @Autowired
    private CustomerRepository repo;

    public Page<Customer> listByPage(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, CUSTOMER_PER_PAGE);
        if (keyword != null) {
            return repo.listByPage(keyword, pageable);
        } else {
            return repo.findAll(pageable);
        }
    }

    public Customer getById(Integer id) throws CustomerNotFoundExeption {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new CustomerNotFoundExeption("Could not find any customer with id " + id);
        }
    }

    public boolean getEnableById(Integer id) throws CustomerNotFoundExeption {
        return getById(id).isEnabled();
    }

    public String checkUniqueEmail(Integer id, String email) {
        boolean isCreating = (id == null || id == 0);

        Customer customerByEmail = repo.findByEmail(email);
        if (isCreating) {
            if (customerByEmail != null) {
                return "Duplicated";
            }
        } else {
            if (customerByEmail != null && customerByEmail.getId() != id) {
                return "Duplicated";
            }
        }
        return "OK";
    }

    public void updateCustomerEnabledStatus(Integer id) throws CustomerNotFoundExeption {
        boolean enabled;
        if (getById(id).isEnabled()) {
            enabled = false;
        } else {
            enabled = true;
        }
        repo.updateEnabledStatus(enabled, id);
    }

    public Customer saveCustomer(Customer customer) {
        return repo.save(customer);
    }

    public void deleteCustomer(Integer id) throws CustomerNotFoundExeption {
        Customer customer = getById(id);
        if (customer != null) {
            repo.deleteById(id);
        }
    }
}
