package com.learning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.learning.entity.Admin;
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
    private OrderService orderService;
    
    @Autowired
    private CaptchaValidatorService captchaValidatorService;

    public String saveAdmin(String name, String email, String password, String address, String phone,String captchaResponse, Model model) {
    	if (!captchaValidatorService.isCaptchaValid(captchaResponse)) {
    		String msgTxt = "Captcha verification failed";
    		model.addAttribute("msg", msgTxt);
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
    	
    	Optional<Admin> ad = adminRepository.findByEmail(email);
    	
    	if(ad.isPresent()) {
    		return "redirect:/admin/register?error=User+Already+Exist+With+Provided+Email";
    	}
    	
    	Admin admin = Admin.builder()
                .adminId(java.util.UUID.randomUUID().toString())
                .name(name)
                .email(email)
                .password(password) 
                .address(address)
                .phoneNumber(phone)
                .build();

        adminRepository.save(admin);
        return "redirect:/admin/login";
    }
    
    public String authenticateAdmin(String email, String password, HttpSession session, Model model) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent() && adminOptional.get().getPassword().equals(password)) {
            Admin admin =  adminOptional.get();  
                session.setAttribute("loggedInAdmin", admin);
                String token = authService.login(email, password, "Admin");
                session.setAttribute("jwtToken", token);
                Long pendingOrderCount = orderService.getPendingOrderCountForAdmin(admin.getAdminId());
                model.addAttribute("pendingOrderCount", pendingOrderCount);
                return "admin/dashboard";   
        }
        return "redirect:/admin/login?error=Invalid+Details";
    }
}

