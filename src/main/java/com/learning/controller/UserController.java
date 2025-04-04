package com.learning.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learning.DTO.LoginDTO;
import com.learning.DTO.UserRegistrationDTO;
import com.learning.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "user/register";
    }
    
    @PostMapping("/register")
	public ResponseEntity<Map<String, String>> userRegistrationHandler(@RequestBody UserRegistrationDTO registrationDTO, HttpSession session, Model model) {
    	Map<String, String> response = userService.saveUser(registrationDTO);
    	return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginAdmin(@RequestBody LoginDTO loginDTO,HttpSession session) {   	
    	Map<String, String> response = new HashMap<>();
    	userService.authenticateUser(loginDTO.getEmail(), loginDTO.getPassword(), session, response);    
		return ResponseEntity.ok(response);
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/user/login"; 
    }
}
