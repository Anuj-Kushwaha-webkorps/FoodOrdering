package com.learning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.learning.entity.Order;
import com.learning.repository.OrderRepository;

@Service
public class OrderScheduler {

    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(fixedRate = 30000) 
    public void cancelUnacceptedOrders() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(1);
        List<Order> pendingOrders = orderRepository.findByStatusAndOrderTimeBefore("PENDING", fiveMinutesAgo);

        for (Order order : pendingOrders) {
            order.setStatus("REJECTED");
            orderRepository.save(order);
            System.out.println("Order " + order.getOrderId() + " auto-canceled after 5 minutes.");
        }
    }
    
    @Scheduled(fixedRate = 20000)
    public void updateAcceptedOrders() {
    	List<Order> acceptedOrders = orderRepository.findByStatus("ACCEPTED");
    	
    	for(Order order : acceptedOrders) {
    		order.setStatus("COMPLETED");
    		orderRepository.save(order);
    	}
    }
}

