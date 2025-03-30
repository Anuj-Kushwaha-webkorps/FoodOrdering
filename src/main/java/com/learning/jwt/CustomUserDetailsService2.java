package com.learning.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.entity.User;
import com.learning.repository.UserRepository;

@Service
public class CustomUserDetailsService2 implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    

    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("custom user load");

        if(user.isPresent()) {        	
        	return new org.springframework.security.core.userdetails.User(
        			user.get().getEmail(),
        			user.get().getPassword(),
        			new ArrayList<>() 
        			);
        }else {
              throw new UsernameNotFoundException("User not found with email: " + email);

        }

        
    }
}

