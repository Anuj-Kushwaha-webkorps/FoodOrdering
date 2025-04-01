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
    	if(cartService.addToCart(dishId, session)) {
            return "redirect:/user/cart/view";
    	}else {
        	return "redirect:/user/cart/view?error=Restaurant+Owner+is+different";
    	}
    }

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        cartService.viewCart(session, model);
        return "user/cart";
    }
    
    @GetMapping("/edit/{dishId}")
    public String editCartItem(@PathVariable String dishId, HttpSession session, Model model) {
        cartService.editCartItem(dishId, session, model);
            return "user/editCartItem";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("dishId") String dishId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session) {
        cartService.updateCartItem(dishId, quantity, session);
        return "redirect:/user/cart/view";

    }

    @GetMapping("/remove/{dishId}")
    public String removeFromCart(@PathVariable String dishId, HttpSession session) {
        cartService.removeFromCart(dishId, session);
        return "redirect:/user/cart/view";
    }
    
    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {
        cartService.checkoutPage(session, model);
        return "user/checkout";
    }
    
    @PostMapping("/confirm-order")
    public String confirmOrder(@RequestParam("address") String address, HttpSession session) {
        cartService.confirmOrder(address, session);
        return "redirect:/user/orders";
    }
}
