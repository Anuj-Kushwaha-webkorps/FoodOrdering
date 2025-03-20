package com.learning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learning.entity.Order;
import com.learning.entity.User;
import com.learning.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewUserOrders(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/user/login";
        }

        List<Order> userOrders = orderService.getOrdersByUserId(loggedInUser.getUserId());
        model.addAttribute("orders", userOrders);
        return "/user/orders"; // orders.jsp
    }

    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable String orderId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/user/login";
        }

        orderService.cancelOrder(orderId, loggedInUser.getUserId());
        return "redirect:/user/orders";
    }
}
