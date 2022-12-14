package com.linhbowl.site.service;

import com.linhbowl.entity.Customer;
import com.linhbowl.site.repository.CustomerRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    public void registerCustomer(Customer customer) {
        encoder(customer);
        customer.setEnabled(false);
        String randomCode = RandomString.make(64);
        customer.setVerificationCode(randomCode);
        repo.save(customer);
    }

    private void encoder(Customer customer) {
        String encodePassword = encoder.encode(customer.getPassword());
        customer.setPassword(encodePassword);
    }

    public boolean verify(String verificationCode) {
        Customer customer = repo.findByVerificationCode(verificationCode);
        if (customer == null || customer.isEnabled()) {
            return false;
        } else {
            repo.enable(customer.getId());
            return true;
        }
    }

    public Customer getCustomerByEmail(String email){
        return repo.findByEmail(email);
    }

}
