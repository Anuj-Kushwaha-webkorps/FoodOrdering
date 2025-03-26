package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.entity.Admin;
import com.learning.helper.Validation;
import com.learning.jwt.AuthService;
import com.learning.service.AdminService;
import com.learning.service.CaptchaValidatorService;
import com.learning.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
    private AuthService authService;

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private CaptchaValidatorService captchaValidatorService;
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "admin/register";
    }

    @PostMapping("/register")
    public String registerAdmin(@RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("address") String address,
                                @RequestParam("phone") String phone,
                                @RequestParam("g-recaptcha-response") String captchaResponse,
                                Model model) {
        
    	if (!captchaValidatorService.isCaptchaValid(captchaResponse)) {
    		String msgTxt = "Captcha verification failed";
    		model.addAttribute("msg", msgTxt);
    		System.out.println("error found in recaptcha");
            return "redirect:/admin/register?error=Captcha+Invalid";
        }
    	
    	if(!Validation.emailValidation(email)) {
            return "redirect:/admin/register?error=Invalid+Email+Details";
    	}
    	
    	if(!Validation.passwordValidation(password)) {
    		String msg = "Password must have at least 8 character with atleast 1 small and capital letter, 1 digit and 1 Special character";
            return "redirect:/admin/register?error=Invalid+password "+msg;
    	}
    	
    	if(!Validation.isValidPhoneNumber(phone)) {
    		String msg = "Invalide phone number ! phone number must have 10 digits.";
            return "redirect:/admin/register?error=Invalid+phone+number "+msg;
    	}

    	adminService.saveAdmin(name, email, password, address, phone);
        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "admin/login";
    }
    
    @PostMapping("/login")
    public String loginAdmin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session, Model model) {
        Admin admin = adminService.authenticateAdmin(email, password);
        
        if (admin != null) {
            session.setAttribute("loggedInAdmin", admin);
            String token = authService.login(email, password, "Admin");
            session.setAttribute("jwtToken", token);
            Long pendingOrderCount = orderService.getPendingOrderCountForAdmin(admin.getAdminId());
            model.addAttribute("pendingOrderCount", pendingOrderCount);
            return "admin/dashboard";
        }
        return "redirect:/admin/login?error=Invalid+Details";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/admin/login"; 
    }
    
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
    	Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (loggedInAdmin == null) {
            return "redirect:/admin/login"; 
        }
        
        Long pendingOrderCount = orderService.getPendingOrderCountForAdmin(loggedInAdmin.getAdminId());
        model.addAttribute("pendingOrderCount", pendingOrderCount);
    	return "admin/dashboard";
    }
}
