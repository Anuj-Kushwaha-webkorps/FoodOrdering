package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.service.CartService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;

    @GetMapping("/add/{dishId}")
    public String addToCart(@PathVariable String dishId, HttpSession session) {
    	return cartService.addToCart(dishId, session);
    }

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        return cartService.viewCart(session, model);
    }
    
    @GetMapping("/edit/{dishId}")
    public String editCartItem(@PathVariable String dishId, HttpSession session, Model model) {
        return cartService.editCartItem(dishId, session, model);
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("dishId") String dishId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session) {
        return cartService.updateCartItem(dishId, quantity, session);
    }

    @GetMapping("/remove/{dishId}")
    public String removeFromCart(@PathVariable String dishId, HttpSession session) {
        return cartService.removeFromCart(dishId, session);
    }
    
    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {
        return cartService.checkoutPage(session, model);
    }
    
    @PostMapping("/confirm-order")
    public String confirmOrder(@RequestParam("address") String address, HttpSession session) {
        return cartService.confirmOrder(address, session);
    }
}
