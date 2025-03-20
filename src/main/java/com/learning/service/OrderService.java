package com.learning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Order;
import com.learning.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserUserId(userId);
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
}

