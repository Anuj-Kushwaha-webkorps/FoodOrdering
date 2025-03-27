package com.learning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.entity.CartItem;
import com.learning.entity.Dish;
import com.learning.entity.Order;
import com.learning.entity.OrderItem;
import com.learning.entity.User;
import com.learning.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {
	@Autowired
    private DishService dishService;
	
	@Autowired
    private OrderRepository orderRepository;

	public String addToCart(String dishId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        Dish dish = dishService.getDishById(dishId);
        String adminId2 = dishService.getDishById(dish.getDishId()).getRestaurant().getAdmin().getAdminId();
        String adminId1 = "";
        
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
            adminId1 = adminId2;
        }else {
        	 adminId1 = dishService.getDishById(cart.get(0).getDishId()).getRestaurant().getAdmin().getAdminId();
        }

        
        for (CartItem item : cart) {
            if (item.getDishId().equals(dishId)) {
                item.setQuantity(item.getQuantity() + 1);
                return "redirect:/user/cart/view";
            }
        }
        
        
        
        if(!adminId1.equals(adminId2)) {
        	return "redirect:/user/cart/view?error=Restaurant+Owner+is+different";
        }
        cart.add(new CartItem(dishId, dish.getName(), dish.getPrice(), dish.getDishSize().name(), 1));
        return "redirect:/user/cart/view";
	}
	
	public String viewCart(HttpSession session, Model model) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        model.addAttribute("cart", cart);
        return "user/cart";
	}

	public String editCartItem(String dishId, HttpSession session, Model model) {
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
	
	public String updateCartItem(String dishId,int quantity, HttpSession session) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getDishId().equals(dishId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
        }
        return "redirect:/user/cart/view";
	}

	public String removeFromCart(String dishId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getDishId().equals(dishId));
        }
        return "redirect:/user/cart/view";
    }
	
	public String checkoutPage(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/user/cart/view"; 
        }

        double totalPrice = cart.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        
        return "user/checkout";
    }

	public String confirmOrder(@RequestParam("address") String address, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/user/cart/view";
        }

        User user = (User) session.getAttribute("loggedInUser");
        
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
