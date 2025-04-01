package com.learning.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.DTO.UserRegistrationDTO;
import com.learning.entity.User;
import com.learning.helper.Validation;
import com.learning.jwt.AuthService;
import com.learning.repository.UserRepository;
import com.learning.service.CaptchaValidatorService;
import com.learning.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private CaptchaValidatorService captchaValidatorService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "user/register";
    }
    
    @PostMapping("/register")
	public ResponseEntity<Map<String, String>> userRegistrationHandler(@RequestBody UserRegistrationDTO registrationDTO, HttpSession session, Model model) {
    	Map<String, String> response = new HashMap<>();
    	if (!captchaValidatorService.isCaptchaValid(registrationDTO.getRecaptcha())) {
    		response.put("redirectUrl", "/user/register?error=Captcha+Invalid");
            return ResponseEntity.ok(response);
        }
    	
    	if(!Validation.emailValidation(registrationDTO.getEmail())) {
    		response.put("redirectUrl", "/user/register?error=Invalid+Email+Details");
            return ResponseEntity.ok(response);
    	}
    	
    	if(!Validation.passwordValidation(registrationDTO.getPassword())) {
    		String msg = "Password must have at least 8 character with atleast 1 small and capital letter, 1 digit and 1 Special character";
    		response.put("redirectUrl", "/user/register?error=Invalid+password"+msg);
    		return ResponseEntity.ok(response);
    	}
    	
    	if(!Validation.isValidPhoneNumber(registrationDTO.getPhone())) {
    		String msg = "Invalide phone number ! phone number must have 10 digits.";
    		response.put("redirectUrl", "/user/register?error=Invalid+phone+number"+msg);
            return ResponseEntity.ok(response);
    	}
    	
    	Optional<User> ad = userRepository.findByEmail(registrationDTO.getEmail());
    	
    	if(ad.isPresent()) {
    		response.put("redirectUrl", "/user/register?error=User+Already+Exist+With+Provided+Email");
    		return ResponseEntity.ok(response);
    	}
    	
    	if(!userService.saveUser(registrationDTO)) {
    		response.put("redirectUrl", "/user/register?error=Internal+Server+Error");
    		return ResponseEntity.ok(response);
    	}
    	
		response.put("redirectUrl", "/user/login");
    	return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }
    
    @PostMapping("/login")
    public String loginAdmin(@RequestParam("email") String email,
            				 @RequestParam("password") String password,
                              HttpSession session) {
    	
    	User user = userService.authenticateUser(email, password);
        if (user != null) {
            String token = authService.login(email, password, "User");
            session.setAttribute("loggedInUser", user);
            session.setAttribute("jwtToken", token);
            return "redirect:/user/dashboard";
        }
        return "redirect:/user/login?error=User+does+not+exist+with+given+email+and+password.";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/user/login"; 
    }
}
