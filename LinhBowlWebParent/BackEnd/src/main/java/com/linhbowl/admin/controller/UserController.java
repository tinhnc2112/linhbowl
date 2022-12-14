package com.linhbowl.admin.controller;

import com.linhbowl.admin.FileUploadUtil;
import com.linhbowl.entity.Role;
import com.linhbowl.admin.exeption.UserNotFoundExeption;
import com.linhbowl.admin.service.UserService;
import com.linhbowl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String listAll(Model model, HttpServletRequest request) {
        model.addAttribute("pageTitle", "Users Manager");
        return listByPage(1, model, null, request);
    }

    @GetMapping("users/page/{pageNum}")
    public String listByPage(@PathVariable("pageNum") int pageNum, Model model, @Param(value="keyword") String keyword, HttpServletRequest request) {
        Page<User> page = service.listByPage(pageNum, keyword);
        List<User> listUsers = page.getContent();
        long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
        long endCount = startCount + UserService.USERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("users", listUsers);
        model.addAttribute("keyword", keyword);
        return "users/users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> listRoles = service.listRole();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create new user");

        return "users/user_form";
    }

    @PostMapping("/users/save")
    public String addUser(User user, RedirectAttributes redirectAttributes,
                          @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhoto(fileName);
            User savedUser = service.save(user);
            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhoto().isEmpty()) user.setPhoto(null);
            service.save(user);
        }

        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
        String partOfEmail = user.getEmail().split("@")[0];
        return "redirect:/users/page/1?keyword=" + partOfEmail;
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = service.get(id);
            List<Role> listRoles = service.listRole();
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit user");
            model.addAttribute("listRoles", listRoles);
            redirectAttributes.addFlashAttribute("message", "The user has been updated.");
            return "users/user_form";
        } catch (UserNotFoundExeption e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            service.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "The user ID " + id + " has been deleted successfully");
        } catch (UserNotFoundExeption e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/enable/{id}")
    public String updateEnableUser(@PathVariable("id") Integer id, RedirectAttributes attributes) throws UserNotFoundExeption {
        service.updateEnableUser(id);
        String name = service.get(id).getFirstName();
        String status = service.getEnableById(id) ? "disable" : "enable";
        String message = name + " has been " + status;
        attributes.addFlashAttribute("message", message);
        String partOfEmail = service.get(id).getEmail().split("@")[0];
        return "redirect:/users/page/1?keyword=" + partOfEmail;
    }
}
