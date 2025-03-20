package com.learning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Admin;
import com.learning.entity.User;
import com.learning.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveAdmin(String name, String email, String password, String address, String phone) {
        User user = User.builder()
                .userId(java.util.UUID.randomUUID().toString())
                .name(name)
                .email(email)
                .password(password) 
                .address(address)
                .phoneNumber(phone)
                .build();

        userRepository.save(user);
    }
    
    public User authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return userOptional.get();
        }
        return null;
    }
}

