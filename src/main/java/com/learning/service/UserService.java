package com.learning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.DTO.UserRegistrationDTO;
import com.learning.entity.User;
import com.learning.helper.ObjectFactory;
import com.learning.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Boolean saveUser(UserRegistrationDTO userRegistrationDTO) {
    	
    	User user = ObjectFactory.createUserObject(userRegistrationDTO);

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

