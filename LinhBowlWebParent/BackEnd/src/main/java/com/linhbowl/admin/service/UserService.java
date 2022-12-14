package com.linhbowl.admin.service;

import com.linhbowl.admin.repository.RoleRepository;
import com.linhbowl.entity.Role;
import com.linhbowl.admin.exeption.UserNotFoundExeption;
import com.linhbowl.admin.repository.UserRepository;
import com.linhbowl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {
    public static final int USERS_PER_PAGE = 4;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    public List<User> listAll() {
        return (List<User>) userRepo.findAll();
    }

    public Page<User> listByPage(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);
        if (keyword != null) {
            return userRepo.findAll(keyword, pageable);
        }
        return userRepo.findAll(pageable);
    }

    public List<Role> listRole() {
        return roleRepo.findAll();
    }

    public void encodePassword(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
    }

    public User save(User user) {
        boolean isUpdatingUser = (user.getId() != null);
        if (isUpdatingUser) {
            User existUser = userRepo.findById(user.getId()).get();
            if (user.getPassword().isEmpty()) {
                user.setPassword(existUser.getPassword());
            } else {
                user.setPassword(user.getPassword());
                encodePassword(user);
            }
        } else {
            user.setPassword(user.getPassword());
            encodePassword(user);
        }
        return userRepo.save(user);
    }

    public User updateAccount(User userForm) {
        User userDB = userRepo.findById(userForm.getId()).get();
        if (!userForm.getPassword().isEmpty()) {
            userDB.setPassword(userForm.getPassword());
            encodePassword(userDB);
        }
        if (userForm.getPhoto() != null) {
            userDB.setPhoto(userForm.getPhoto());
        }
        userDB.setFirstName(userForm.getFirstName());
        userDB.setLastName(userForm.getLastName());

        return userRepo.save(userDB);
    }


    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepo.getUserByEmail(email);
        if (userByEmail == null) return true;
        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            if (userByEmail != null) return false;
        } else {
            if (userByEmail.getId() != id) return false;
        }
        return true;
    }


    public User get(Integer id) throws UserNotFoundExeption {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundExeption("Could not find any user with ID " + id);
        }
    }

    public void deleteUser(Integer id) throws UserNotFoundExeption {
        Long countById = userRepo.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundExeption("Could not find any user with ID " + id);
        }
        userRepo.deleteById(id);
    }

    public void updateEnableUser(Integer id) {
        boolean enable;
        if (userRepo.findById(id).get().isEnabled()) {
            enable = false;
        } else {
            enable = true;
        }
        userRepo.updateEnableStatus(id, enable);
    }

    public boolean getEnableById(Integer id) {
        return userRepo.findById(id).get().isEnabled();
    }
}
