package com.learning.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learning.DTO.LoginDTO;
import com.learning.DTO.UserRegistrationDTO;
import com.learning.entity.Admin;
import com.learning.service.AdminService;
import com.learning.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private OrderService orderService;
        

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "admin/register";
    }
    
    @PostMapping("/register")
	public ResponseEntity<Map<String, String>> userRegistrationHandler(@RequestBody UserRegistrationDTO registrationDTO) {
    	Map<String, String> response = adminService.saveAdmin(registrationDTO);
    	return ResponseEntity.ok(response);
    }
    

    @GetMapping("/login")
    public String showLoginForm() {
        return "admin/login";
    }
  
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginAdmin(@RequestBody LoginDTO loginDTO, HttpSession session, Model model) {
    	Map<String, String> response = new HashMap<>();
    	if(adminService.authenticateAdmin(loginDTO.getEmail().trim(), loginDTO.getPassword(),session,response)) {
            response.put("redirectUrl", "/admin/dashboard");   
    		return ResponseEntity.ok(response);
    	}else {
    		return ResponseEntity.ok(response);
    	}
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
