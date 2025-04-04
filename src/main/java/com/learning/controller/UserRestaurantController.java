package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learning.entity.Dish;
import com.learning.entity.Restaurant;
import com.learning.service.DishService;
import com.learning.service.RestaurantService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserRestaurantController {

	@Autowired
    private DishService dishService;
	
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public String showAvailableRestaurants(Model model) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        restaurants = restaurants.stream().filter(restaurant -> dishService.getDishesByRestaurantId(restaurant.getRestaurantId()).size() > 0).collect(Collectors.toList());
        model.addAttribute("restaurants", restaurants);
        return "user/viewRestaurants";
    }
    
    @GetMapping("/restaurants/{restaurantId}/dishes")
    public String showDishes(@PathVariable String restaurantId, Model model) {
        List<Dish> dishes = dishService.getDishesByRestaurantId(restaurantId);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);        
        model.addAttribute("dishes", dishes);
        model.addAttribute("restaurant", restaurant);
        return "user/dishes";
    }
}

