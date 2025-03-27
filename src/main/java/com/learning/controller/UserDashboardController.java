package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learning.entity.User;
import com.learning.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserDashboardController {
	 @Autowired
	    private OrderService orderService;

    @GetMapping("/dashboard")
    public String showUserDashboard(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Long pendingOrderCount = orderService.getAcceptedAndRejectedOrdersByUserId(loggedInUser.getUserId());
        model.addAttribute("user", loggedInUser);
        model.addAttribute("notification", pendingOrderCount);
        return "user/dashboard";
    }
}
