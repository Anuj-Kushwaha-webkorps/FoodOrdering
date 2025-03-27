package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.entity.User;
import com.learning.jwt.AuthService;
import com.learning.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("address") String address,
                                @RequestParam("phone") String phone,
                                @RequestParam("g-recaptcha-response") String captchaResponse,
                                Model model) {
	
    	return userService.saveAdmin(name, email, password, address, phone,captchaResponse,model );        
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
