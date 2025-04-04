package com.learning.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.entity.Admin;
import com.learning.repository.AdminRepository;

@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {

    
    @Autowired
    private AdminRepository adminRepository;

    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("custom admin load");

        Optional<Admin> admin = adminRepository.findByEmail(email);
        
        if(admin.isPresent()) {        	
        	return new org.springframework.security.core.userdetails.User(
        			admin.get().getEmail(),
        			admin.get().getPassword(),
        			new ArrayList<>() 
        			);
        }else {
            throw new UsernameNotFoundException("User not found with email: " + email);

        }
        
    }
}

