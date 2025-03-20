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
import com.learning.entity.Order;
import com.learning.entity.OrderItem;
import com.learning.entity.User;
import com.learning.repository.OrderRepository;
import com.learning.service.DishService;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private DishService dishService;
    
    @Autowired
    private OrderRepository orderRepository;

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
    
    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/user/cart/view"; 
        }

        double totalPrice = cart.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        
        
        
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        
        return "/user/checkout";
    }
    
    @PostMapping("/confirm-order")
    public String confirmOrder(@RequestParam("address") String address, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/user/cart/view";
        }

        User user = (User) session.getAttribute("loggedInUser");
        
        // Get restaurantId from first dish in cart
        String restaurantId = dishService.getDishById(cart.get(0).getDishId()).getRestaurant().getRestaurantId();

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUser(user);
        order.setRestaurantId(restaurantId);
        order.setDeliveryAddress(address);
        order.setStatus("PENDING");
        order.setTotalPrice(cart.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum());

        List<OrderItem> orderItems = cart.stream().map(item -> {
            Dish dish = dishService.getDishById(item.getDishId());
            return OrderItem.builder()
                    .orderItemId(UUID.randomUUID().toString())
                    .order(order)
                    .dish(dish)
                    .dishName(item.getDishName())
                    .quantity(item.getQuantity())
                    .pricePerItem(item.getPrice())
                    .build();
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        orderRepository.save(order);
        session.removeAttribute("cart");

        return "redirect:/user/orders";
    }
}
