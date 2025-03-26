package com.learning.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.entity.Admin;
import com.learning.entity.User;
import com.learning.repository.AdminRepository;
import com.learning.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminRepository adminRepository;

    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        
        Optional<Admin> admin = adminRepository.findByEmail(email);
        
        if (user.isEmpty() && admin.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        if(admin.isEmpty()) {
        	return new org.springframework.security.core.userdetails.User(
                    user.get().getEmail(),
                    user.get().getPassword(),
                    new ArrayList<>()  // Authorities (roles) can be added here if needed
            );
        }else {
        	return new org.springframework.security.core.userdetails.User(
                    admin.get().getEmail(),
                    admin.get().getPassword(),
                    new ArrayList<>()  // Authorities (roles) can be added here if needed
            );
        }
        
    }
}

