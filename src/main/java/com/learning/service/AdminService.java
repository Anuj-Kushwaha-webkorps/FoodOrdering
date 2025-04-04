package com.learning.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.DTO.UserRegistrationDTO;
import com.learning.entity.Admin;
import com.learning.helper.ObjectFactory;
import com.learning.helper.Validation;
import com.learning.jwt.AuthService;
import com.learning.repository.AdminRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private CaptchaValidatorService captchaValidatorService;
    
    public Map<String, String> saveAdmin(UserRegistrationDTO registrationDTO) {
    	    
    	Map<String, String> response = new HashMap<>();
    	Validation.validateUserInput(registrationDTO, response, captchaValidatorService);
	    
    	return !response.isEmpty() ? response 
	            : adminRepository.findByEmail(registrationDTO.getEmail()).isPresent() 
	            ? Map.of("errorEmail", "Admin already exists with provided Email")
	            : saveAdminAndReturnResponse(registrationDTO);
    }
    
    private Map<String, String> saveAdminAndReturnResponse(UserRegistrationDTO registrationDTO) {
    	try {    	
        	Admin admin = ObjectFactory.createAdminObject(registrationDTO);
    		adminRepository.save(admin);
    		return Map.of("redirectUrl", "/admin/login");
    	}catch(Exception e) {
    		return Map.of("error", "Internal Server Error");
    	}
    }
    
    public Boolean authenticateAdmin(String email, String password, HttpSession session, Map<String, String> response) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        
        if (adminOptional.isPresent() && adminOptional.get().getPassword().equals(password)) {
            Admin admin =  adminOptional.get();  
            session.setAttribute("loggedInAdmin", admin);
            String token = authService.login(email, password, "Admin");
            session.setAttribute("jwtToken", token);
            return true;
        }else {
        	response.put("errorMsg", "Username or Password is incorrect.");
        	return false;
        }
    }
}

