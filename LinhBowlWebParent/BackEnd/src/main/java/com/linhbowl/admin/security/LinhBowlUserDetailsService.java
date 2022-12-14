package com.linhbowl.admin.security;

import com.linhbowl.entity.User;
import com.linhbowl.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LinhBowlUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.getUserByEmail(email);
        if (user != null) {
            return new LinhBowlUserDetails(user);
        }
        throw new UsernameNotFoundException("Could not find user with email: " + email);
    }
}
