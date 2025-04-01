package com.learning.helper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.learning.DTO.UserRegistrationDTO;
import com.learning.entity.Admin;
import com.learning.entity.CartItem;
import com.learning.entity.Dish;
import com.learning.entity.Order;
import com.learning.entity.OrderItem;
import com.learning.entity.Restaurant;
import com.learning.entity.User;
import com.learning.service.DishService;

public class ObjectFactory {

	public static Dish createDishObject(String restaurantId, String name, String description, Double price, String dishType, String dishSize) {
		return Dish.builder()
                .dishId(java.util.UUID.randomUUID().toString())
                .name(name)
                .description(description)
                .price(price)
                .dishType(Dish.DishType.valueOf(dishType))
                .dishSize(Dish.DishSize.valueOf(dishSize))
                .restaurant(Restaurant.builder().restaurantId(restaurantId).build())
                .build();
	}
	
	public static Admin createAdminObject(UserRegistrationDTO userRegistrationDTO) {
    	
    	return Admin.builder()
                .adminId(java.util.UUID.randomUUID().toString())
                .name(userRegistrationDTO.getName())
                .email(userRegistrationDTO.getEmail())
                .password(userRegistrationDTO.getPassword()) 
                .address(userRegistrationDTO.getAddress())
                .phoneNumber(userRegistrationDTO.getPhone())
                .build();
	}
	
	public static Restaurant createRestaurantObject(String name, String address, String contact, String adminId) {
		return Restaurant.builder()
                .restaurantId(java.util.UUID.randomUUID().toString())
                .name(name)
                .address(address)
                .contactNumber(contact)
                .admin(Admin.builder().adminId(adminId).build())
                .build();
	}
	
	public static User createUserObject(UserRegistrationDTO userRegistrationDTO) {
	    	
	    return User.builder()
	                .userId(java.util.UUID.randomUUID().toString())
	                .name(userRegistrationDTO.getName())
	                .email(userRegistrationDTO.getEmail())
	                .password(userRegistrationDTO.getPassword()) 
	                .address(userRegistrationDTO.getAddress())
	                .phoneNumber(userRegistrationDTO.getPhone())
	                .build();
	}
	
	public static Order createOrderObject(User user, String restaurantId, String address, List<CartItem> cart, DishService dishService ) {
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
        return order;
	}
}
