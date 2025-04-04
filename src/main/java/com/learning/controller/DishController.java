package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.learning.DTO.DishDTO;
import com.learning.entity.Dish;
import com.learning.helper.Validation;
import com.learning.service.DishService;

@Controller
@RequestMapping("/admin/restaurants/{restaurantId}/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping
    public String showDishes(@PathVariable String restaurantId, Model model) {
        List<Dish> dishes = dishService.getDishesByRestaurantId(restaurantId);
        model.addAttribute("dishes", dishes);
        model.addAttribute("restaurantId", restaurantId);
        return "admin/dishes";
    }
    
    @GetMapping("/add")
    public String showAddDishForm(@PathVariable String restaurantId, Model model) {
        model.addAttribute("restaurantId", restaurantId);
        return "admin/addDish";
    }
    
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addDish(@RequestBody DishDTO dishDTO,@PathVariable String restaurantId){
    	Map<String, String> response = new HashMap<>();
    	if(!Validation.priceValidation(dishDTO.getPrice()+"")) {
    		response.put("errorPrice", "Invalid Price");
    		return ResponseEntity.ok(response);
    	}
    	
    	dishService.addDish(dishDTO, restaurantId);
    	response.put("redirectUrl", "/admin/restaurants/" + restaurantId + "/dishes");
    	return ResponseEntity.ok(response);
    }
    
    
    
    @GetMapping("/edit/{dishId}")
    public String showEditDishForm(@PathVariable String restaurantId, @PathVariable String dishId, Model model) {
        Dish dish = dishService.getDishById(dishId);
        model.addAttribute("dish", dish);
        model.addAttribute("restaurantId", restaurantId);
        return "admin/editDish";
    }

    
    @PostMapping("/edit")
    public ResponseEntity<Map<String, String>> editDish(@RequestBody DishDTO dishDTO,@PathVariable String restaurantId){
    	Map<String, String> response = new HashMap<>();
    	if(!Validation.priceValidation(dishDTO.getPrice()+"")) {
    		response.put("errorPrice", "Invalid Price");
    		return ResponseEntity.ok(response);
    	}
    	
    	dishService.updateDish(dishDTO);
    	response.put("redirectUrl", "/admin/restaurants/" + restaurantId + "/dishes");
    	return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{dishId}")
    public String deleteDish(@PathVariable String restaurantId, @PathVariable String dishId) {
        dishService.deleteDish(dishId);
        return "redirect:/admin/restaurants/" + restaurantId + "/dishes";
    }
}

