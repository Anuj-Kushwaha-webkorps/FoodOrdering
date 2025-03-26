package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.entity.Admin;
import com.learning.entity.User;
import com.learning.helper.Validation;
import com.learning.jwt.AuthService;
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

    @GetMapping("/register")
    public String showRegistrationForm() {
    	System.out.println("showRegistration");
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
        
    	if (!captchaValidatorService.isCaptchaValid(captchaResponse)) {
    		String msgTxt = "Captcha verification failed";
    		model.addAttribute("msg", msgTxt);
            return "/user/error";
        }

    	if(!Validation.emailValidation(email)) {
            return "redirect:/user/register?error=Invalid+Email+Details";
    	}
    	
    	if(!Validation.passwordValidation(password)) {
    		String msg = "Password must have at least 8 character with atleast 1 small and capital letter, 1 digit and 1 Special character";
            return "redirect:/user/register?error=Invalid+password "+msg;
    	}
    	
    	if(!Validation.isValidPhoneNumber(phone)) {
    		String msg = "Invalide phone number ! phone number must have 10 digits.";
            return "redirect:/user/register?error=Invalid+phone+number "+msg;
    	}

    	userService.saveAdmin(name, email, password, address, phone);
        return "redirect:/user/login";
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

class LoginRequest {
    private String email;
    private String password;

    // Getters, setters, and constructors
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
