package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.entity.Admin;
import com.learning.entity.User;
import com.learning.service.CaptchaValidatorService;
import com.learning.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CaptchaValidatorService captchaValidatorService;

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
        
    	if (!captchaValidatorService.isCaptchaValid(captchaResponse)) {
    		String msgTxt = "Captcha verification failed";
    		model.addAttribute("msg", msgTxt);
            return "/user/error";
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
            session.setAttribute("loggedInUser", user);
            return "redirect:/user/dashboard";
        }
        return "redirect:/user/login?error=true";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/user/login"; 
    }
}
