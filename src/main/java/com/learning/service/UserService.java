package com.learning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.DTO.UserRegistrationDTO;
import com.learning.entity.User;
import com.learning.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Boolean saveAdmin(UserRegistrationDTO userRegistrationDTO) {
    	
    	User user = User.builder()
                .userId(java.util.UUID.randomUUID().toString())
                .name(userRegistrationDTO.getName())
                .email(userRegistrationDTO.getEmail())
                .password(userRegistrationDTO.getPassword()) 
                .address(userRegistrationDTO.getAddress())
                .phoneNumber(userRegistrationDTO.getPhone())
                .build();

    	try {    		
    		userRepository.save(user);
    		return true;
    	}catch(Exception e) {
    		return false;
    	}
    }
    
    public User authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return userOptional.get();
        }
        return null;
    }
}

