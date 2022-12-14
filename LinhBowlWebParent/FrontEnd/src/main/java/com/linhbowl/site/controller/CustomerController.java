package com.linhbowl.site.controller;

import com.linhbowl.entity.Customer;
import com.linhbowl.site.Utility;
import com.linhbowl.site.service.CustomerService;
import com.linhbowl.site.service.EmailSettingBag;
import com.linhbowl.site.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SettingService settingService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register/register_form";
    }

    @PostMapping("/customers/create")
    public String createCustomer(Customer customer, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        customerService.registerCustomer(customer);
        sendVerificationEmail(request, customer);
        return "register/register_success";
    }

    private void sendVerificationEmail(HttpServletRequest request, Customer customer) throws MessagingException, UnsupportedEncodingException {
        EmailSettingBag emailSettings = settingService.getEmailSetting();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);

        String toAddress = customer.getEmail();
        String subject = emailSettings.getCustomerVerifySubject();
        String content = emailSettings.getCustomerVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", customer.getFullName());
        String verifyURL = Utility.getSiteURL(request) + "/verify?code=" + customer.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);
        mailSender.send(message);
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code) {
        boolean verified = customerService.verify(code);
        return "register/" + (verified ? "verify_success" : "verify_fail");
    }

}
