package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.entity.Admin;
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
            return "/admin/login?error=true";
        }

		System.out.println("successfull in recaptcha");

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
        System.out.println(admin);
        System.out.println(admin.getName());
        if (admin != null) {
            session.setAttribute("loggedInAdmin", admin);
            Long pendingOrderCount = orderService.getPendingOrderCountForAdmin(admin.getAdminId());
            model.addAttribute("pendingOrderCount", pendingOrderCount);
            return "/admin/dashboard";
        }
        return "redirect:/admin/login?error=true";
    }
    
    @GetMapping("/dashboard")
    public String showDashboard() {
    	return "/admin/dashboard";
    }
}
