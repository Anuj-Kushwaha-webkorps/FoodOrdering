package com.learning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.entity.CartItem;
import com.learning.entity.Dish;
import com.learning.entity.Order;
import com.learning.entity.User;
import com.learning.helper.ObjectFactory;
import com.learning.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {
	@Autowired
    private DishService dishService;
	
	@Autowired
    private OrderRepository orderRepository;

    @SuppressWarnings("unchecked")
	public Boolean addToCart(String dishId, HttpSession session) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        Dish dish = dishService.getDishById(dishId);
        
        if (dish == null) {
            return false;
        }
        
        String newAdminId = dish.getRestaurant().getAdmin().getAdminId();
        
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }else {
        	String existingAdminId = dishService.getDishById(cart.get(0).getDishId()).getRestaurant().getAdmin().getAdminId();
			if (!existingAdminId.equals(newAdminId)) {
				return false; 
			}
        }
        
        Optional<CartItem> existingItem = cart.stream()
                .filter(item -> item.getDishId().equals(dishId))
                .findAny();

		if (existingItem.isPresent()) {
			 existingItem.get().setQuantity(existingItem.get().getQuantity() + 1);
		} else {
			cart.add(new CartItem(dishId, dish.getName(), dish.getPrice(), dish.getDishSize().name(), 1));
		}
		     return true;
	}
	
    @SuppressWarnings("unchecked")
	public void viewCart(HttpSession session, Model model) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        model.addAttribute("cart", cart);
	}

    @SuppressWarnings("unchecked")
	public void editCartItem(String dishId, HttpSession session, Model model) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

		cart.stream()
	    .filter(item -> item.getDishId().equals(dishId))
	    .findAny()
	    .ifPresent(item -> model.addAttribute("cartItem", item));
 
	}
	
    @SuppressWarnings("unchecked")
	public void updateCartItem(String dishId,int quantity, HttpSession session) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		cart.stream()
	    .filter(item -> item.getDishId().equals(dishId))
	    .findAny() 
	    .ifPresent(item -> item.setQuantity(quantity));
    
	}

    @SuppressWarnings("unchecked")
	public void removeFromCart(String dishId, HttpSession session) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getDishId().equals(dishId));
        }
    }
	
    @SuppressWarnings("unchecked")
	public void checkoutPage(HttpSession session, Model model) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        double totalPrice = cart.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
    }
	
    @SuppressWarnings("unchecked")
	public void confirmOrder(@RequestParam("address") String address, HttpSession session) {
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        User user = (User) session.getAttribute("loggedInUser");
        
        String restaurantId = dishService.getDishById(cart.get(0).getDishId()).getRestaurant().getRestaurantId();

        Order order = ObjectFactory.createOrderObject(user, restaurantId, address, cart, dishService);

        orderRepository.save(order);
        session.removeAttribute("cart");
    }
}
