package com.learning.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.DTO.UserRegistrationDTO;
import com.learning.entity.Admin;
import com.learning.helper.Validation;
import com.learning.repository.AdminRepository;
import com.learning.service.AdminService;
import com.learning.service.CaptchaValidatorService;
import com.learning.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CaptchaValidatorService captchaValidatorService;
    
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "admin/register";
    }
    
    @PostMapping("/register")
	public ResponseEntity<Map<String, String>> userRegistrationHandler(@RequestBody UserRegistrationDTO registrationDTO, HttpSession session) {
    	Map<String, String> response = new HashMap<>();
    	if (!captchaValidatorService.isCaptchaValid(registrationDTO.getRecaptcha())) {
    		response.put("redirectUrl", "/admin/register?error=Captcha+Invalid");
            return ResponseEntity.ok(response);
        }
    	
    	if(!Validation.emailValidation(registrationDTO.getEmail())) {
    		response.put("redirectUrl", "/admin/register?error=Invalid+Email+Details");
            return ResponseEntity.ok(response);
    	}
    	
    	if(!Validation.passwordValidation(registrationDTO.getPassword())) {
    		String msg = "Password must have at least 8 character with atleast 1 small and capital letter, 1 digit and 1 Special character";
    		response.put("redirectUrl", "/admin/register?error=Invalid+password"+msg);
    		return ResponseEntity.ok(response);
    	}
    	
    	if(!Validation.isValidPhoneNumber(registrationDTO.getPhone())) {
    		String msg = "Invalide phone number ! phone number must have 10 digits.";
    		response.put("redirectUrl", "/admin/register?error=Invalid+phone+number"+msg);
            return ResponseEntity.ok(response);
    	}
    	
    	Optional<Admin> ad = adminRepository.findByEmail(registrationDTO.getEmail());
    	
    	if(ad.isPresent()) {
    		response.put("redirectUrl", "/admin/register?error=User+Already+Exist+With+Provided+Email");
    		return ResponseEntity.ok(response);
    	}
    	
    	if(!adminService.saveAdmin(registrationDTO)) {
    		response.put("redirectUrl", "/admin/register?error=Internal+Server+Error");
    		return ResponseEntity.ok(response);
    	}
    	
		response.put("redirectUrl", "/admin/login");
    	return ResponseEntity.ok(response);
    }
    

    @GetMapping("/login")
    public String showLoginForm() {
        return "admin/login";
    }
    
    @PostMapping("/login")
    public String loginAdmin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session, Model model) {
    	return adminService.authenticateAdmin(email.trim(), password.trim(),session,model);
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        SecurityContextHolder.clearContext();
        return "redirect:/admin/login"; 
    }
    
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
    	Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");     
        Long pendingOrderCount = orderService.getPendingOrderCountForAdmin(loggedInAdmin.getAdminId());
        model.addAttribute("pendingOrderCount", pendingOrderCount);
    	return "admin/dashboard";
    }
}
