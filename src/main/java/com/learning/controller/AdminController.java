package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registerAdmin(@RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("address") String address,
                                @RequestParam("phone") String phone,
                                @RequestParam("g-recaptcha-response") String captchaResponse,
                                Model model) {       
    	return adminService.saveAdmin(name, email, password, address, phone, captchaResponse, model);     
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
