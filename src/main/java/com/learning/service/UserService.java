package com.learning.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.DTO.UserRegistrationDTO;
import com.learning.entity.User;
import com.learning.helper.ObjectFactory;
import com.learning.helper.Validation;
import com.learning.jwt.AuthService;
import com.learning.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
   
    @Autowired
    private AuthService authService;
    
    @Autowired
    private CaptchaValidatorService captchaValidatorService;

    public Map<String, String> saveUser(UserRegistrationDTO registrationDTO) {  	
    	Map<String, String> response = new HashMap<>();
    	
    	Validation.validateUserInput(registrationDTO, response, captchaValidatorService);
    	
	    return !response.isEmpty() ? response 
	            : userRepository.findByEmail(registrationDTO.getEmail()).isPresent() 
	               ? Map.of("errorEmail", "User already exists with provided Email")
	            : saveUserAndReturnResponse(registrationDTO);
    }
    
    private Map<String, String> saveUserAndReturnResponse(UserRegistrationDTO registrationDTO) {
        try {
            userRepository.save(ObjectFactory.createUserObject(registrationDTO));
            return Map.of("redirectUrl", "/user/login");
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Internal Server Error");
        }
    }
    
    public void authenticateUser(String email, String password,HttpSession session, Map<String, String> response ) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {        	
            String token = authService.login(email, password, "User");
            session.setAttribute("loggedInUser", userOptional.get());
            session.setAttribute("jwtToken", token);
            response.put("redirectUrl", "/user/dashboard");
        }else {
            response.put("errorMsg", "Either email or password is incorrect");
        }
    }
}

