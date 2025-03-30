package com.learning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.learning.DTO.UserRegistrationDTO;
import com.learning.entity.Admin;
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
    
    public Boolean saveAdmin(UserRegistrationDTO userRegistrationDTO) {
    	    	
    	Admin admin = Admin.builder()
                .adminId(java.util.UUID.randomUUID().toString())
                .name(userRegistrationDTO.getName())
                .email(userRegistrationDTO.getEmail())
                .password(userRegistrationDTO.getPassword()) 
                .address(userRegistrationDTO.getAddress())
                .phoneNumber(userRegistrationDTO.getPhone())
                .build();

    	try {    		
    		adminRepository.save(admin);
    		return true;
    	}catch(Exception e) {
    		return false;
    	}
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

