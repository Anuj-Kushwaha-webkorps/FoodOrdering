package com.learning.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.learning.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
	List<Order> findByUserUserId(String userId);
	
	List<Order> findByUserUserIdAndStatusNot(String userId, String status);
	
    List<Order> findByStatusAndOrderTimeBefore(String status, LocalDateTime orderTime);

    List<Order> findByRestaurantIdIn(List<String> restaurantIds);
    
    List<Order> findByStatus(String status);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.restaurantId IN :restaurantIds AND o.status = 'PENDING'")
    Long countPendingOrders(@Param("restaurantIds") List<String> restaurantIds);
    
    @Query("SELECT o FROM Order o WHERE o.restaurantId IN :restaurantIds AND o.status IN ('ACCEPTED', 'COMPLETED', 'REJECTED')")
    List<Order> findPastOrdersByRestaurantIds(@Param("restaurantIds") List<String> restaurantIds);
}
