package com.learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, String> {

	public List<Dish> findByRestaurantRestaurantId(String restaurantId);
}
