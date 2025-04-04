package com.learning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Order;
import com.learning.entity.Restaurant;
import com.learning.repository.OrderRepository;
import com.learning.repository.RestaurantRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Order> getOrdersByUserIdAndStatusIn(String userId) {
    	List<String> statusIn = new ArrayList<>();
    	statusIn.add("PENDING");
    	statusIn.add("ACCEPTED");
    	statusIn.add("REJECTED");

        return orderRepository.findByUserUserIdAndStatusIn(userId, statusIn);
    }

    public void cancelOrder(String orderId, String userId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized to cancel this order");
        }

        if (!order.getStatus().equals("PENDING")) {
            throw new RuntimeException("Only pending orders can be canceled");
        }

        order.setStatus("CANCELED");
        orderRepository.save(order);
    }
    
    public List<Order> getOrdersByAdminId(String adminId) {
        List<Restaurant> restaurants = restaurantRepository.findByAdminAdminId(adminId);
        List<String> restaurantIds = restaurants.stream()
                .map(Restaurant::getRestaurantId)
                .collect(Collectors.toList());
        List<String> statusIn = new ArrayList<>();
    	statusIn.add("PENDING");
    	statusIn.add("ACCEPTED");
        return orderRepository.findByRestaurantIdInAndStatusIn(restaurantIds, statusIn);
    }
   

    public void updateOrderStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        orderRepository.save(order);
    }
    
    public Long getPendingOrderCountForAdmin(String adminId) {
        List<Restaurant> restaurants = restaurantRepository.findByAdminAdminId(adminId);
        List<String> restaurantIds = restaurants.stream()
                .map(Restaurant::getRestaurantId)
                .collect(Collectors.toList());

        return orderRepository.countPendingOrders(restaurantIds);
    }
    
    public List<Order> getPastOrdersByAdminId(String adminId) {
        List<Restaurant> restaurants = restaurantRepository.findByAdminAdminId(adminId);
        List<String> restaurantIds = restaurants.stream()
                .map(Restaurant::getRestaurantId)
                .collect(Collectors.toList());

        return orderRepository.findPastOrdersByRestaurantIds(restaurantIds);
    }
    
    public List<Order> getPastOrdersByUserId(String userId) {
    	List<String> statusNotIn = new ArrayList<>();
    	statusNotIn.add("PENDING");
    	statusNotIn.add("ACCEPTED");
        return  orderRepository.findByUserUserIdAndStatusNotInOrderByOrderTimeDesc(userId, statusNotIn);
    }
    
    public Long getAcceptedAndRejectedOrdersByUserId(String userId) {
        return  orderRepository.countAcceptedAndRejectedOrders(userId);
    }
    
    public Optional<Order> getOrderById(String orderId){
    	return orderRepository.findById(orderId);
    }
    
    public void updateRejectedOrders(String userId) {
    	List<Order> acceptedOrders = orderRepository.findByStatusAndUserUserId("REJECTED", userId);
    	
    	acceptedOrders.forEach(order -> {
    	    order.setStatus("REJECTED-SEEN");
    	    orderRepository.save(order);
    	});


    }
}

