package com.linhbowl.admin.controller;

import com.linhbowl.admin.FileUploadUtil;
import com.linhbowl.admin.security.LinhBowlUserDetails;
import com.linhbowl.entity.User;
import com.linhbowl.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class AccountController {

    @Autowired
    private UserService service;

    @GetMapping("/account")
    public String viewAccount(@AuthenticationPrincipal LinhBowlUserDetails loggedUser, Model model) {
        String email = loggedUser.getUsername();
        User user = service.getUserByEmail(email);
        model.addAttribute("user", user);
        return "users/account_form";
    }

    @PostMapping("/account/update")
    public String saveDetails(User user, RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal LinhBowlUserDetails loggedUser,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhoto(fileName);
            User savedUser = service.updateAccount(user);
            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhoto().isEmpty()) user.setPhoto(null);
            service.updateAccount(user);
        }
        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());

        redirectAttributes.addFlashAttribute("message", "Your account has been updated.");
        return "redirect:/account";
    }
}
