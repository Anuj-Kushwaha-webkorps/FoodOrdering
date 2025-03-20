package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.entity.CartItem;
import com.learning.entity.Dish;
import com.learning.service.DishService;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private DishService dishService;

    @GetMapping("/add/{dishId}")
    public String addToCart(@PathVariable String dishId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        Dish dish = dishService.getDishById(dishId);
        for (CartItem item : cart) {
            if (item.getDishId().equals(dishId)) {
                item.setQuantity(item.getQuantity() + 1);
                return "redirect:/user/cart/view";
            }
        }
        
        cart.add(new CartItem(dishId, dish.getName(), dish.getPrice(), dish.getDishSize().name(), 1));
        return "redirect:/user/cart/view";
    }

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        model.addAttribute("cart", cart);
        return "user/cart";
    }
    
    @GetMapping("/edit/{dishId}")
    public String editCartItem(@PathVariable String dishId, HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getDishId().equals(dishId)) {
                    model.addAttribute("cartItem", item);
                    return "user/editCartItem";
                }
            }
        }
        return "redirect:/user/cart/view";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("dishId") String dishId,
                                 @RequestParam("quantity") int quantity,
                                 @RequestParam("dishSize") String dishSize,
                                 HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getDishId().equals(dishId)) {
                    item.setQuantity(quantity);
                    item.setDishSize(dishSize);
                    break;
                }
            }
        }
        return "redirect:/user/cart/view";
    }

    @GetMapping("/remove/{dishId}")
    public String removeFromCart(@PathVariable String dishId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getDishId().equals(dishId));
        }
        return "redirect:/user/cart/view";
    }
}
