package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.learning.entity.Order;
import com.learning.entity.User;
import com.learning.service.OrderService;
import com.learning.service.PdfService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PdfService pdfService;

    @GetMapping
    public String viewUserOrders(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        List<Order> userOrders = orderService.getOrdersByUserId(loggedInUser.getUserId());
        model.addAttribute("orders", userOrders);
        return "user/orders";
    }

    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable String orderId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        orderService.cancelOrder(orderId, loggedInUser.getUserId());
        return "redirect:/user/orders";
    }
    
    @GetMapping("/history")
    public String viewOrderHistory(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        List<Order> pastOrders = orderService.getPastOrdersByUserId(loggedInUser.getUserId());
        model.addAttribute("pastOrders", pastOrders);
        return "user/orderHistory"; 
    }
    
    @GetMapping("/download-receipt/{orderId}")
    public ResponseEntity<byte[]> downloadReceipt(@PathVariable String orderId) {
        Optional<Order> orderOptional = orderService.getOrderById(orderId);
        Order order = orderOptional.get();
        byte[] pdfBytes = pdfService.generateReceipt(order);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Order_" + orderId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
    
    @GetMapping("/rejectedSeen")
    public ResponseEntity<Map<String, String>> updateRejected(HttpSession session){
    	Map<String, String> map = new HashMap<>();
    	User loggedInUser = (User) session.getAttribute("loggedInUser");
    	orderService.updateRejectedOrders(loggedInUser.getUserId());
    	
    	map.put("msg", "updated Successfully");
    	return ResponseEntity.ok(map);
    }
}
