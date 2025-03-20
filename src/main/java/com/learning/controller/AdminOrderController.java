package com.learning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learning.entity.Admin;
import com.learning.entity.Order;
import com.learning.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewOrders(HttpSession session, Model model) {
        Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (loggedInAdmin == null) {
            return "redirect:/admin/login";
        }

        List<Order> adminOrders = orderService.getOrdersByAdminId(loggedInAdmin.getAdminId());
        model.addAttribute("orders", adminOrders);
        return "admin/orders"; // admin/orders.jsp
    }

    @PostMapping("/accept/{orderId}")
    public String acceptOrder(@PathVariable String orderId, HttpSession session) {
        Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (loggedInAdmin == null) {
            return "redirect:/admin/login";
        }

        orderService.updateOrderStatus(orderId, "ACCEPTED");
        return "redirect:/admin/orders";
    }

    @PostMapping("/reject/{orderId}")
    public String rejectOrder(@PathVariable String orderId, HttpSession session) {
        Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (loggedInAdmin == null) {
            return "redirect:/admin/login";
        }

        orderService.updateOrderStatus(orderId, "REJECTED");
        return "redirect:/admin/orders";
    }
    
    @GetMapping("/history")
    public String viewOrderHistory(HttpSession session, Model model) {
        Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (loggedInAdmin == null) {
            return "redirect:/admin/login";
        }

        List<Order> pastOrders = orderService.getPastOrdersByAdminId(loggedInAdmin.getAdminId());
        model.addAttribute("pastOrders", pastOrders);
        return "/admin/orderHistory"; 
    }
}
