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

import com.learning.DTO.RestaurantDTO;
import com.learning.entity.Admin;
import com.learning.entity.Restaurant;
import com.learning.helper.Validation;
import com.learning.service.RestaurantService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
 
    
    @GetMapping
    public String showRestaurants(Model model, HttpSession session) {
        Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
        List<Restaurant> restaurants = restaurantService.getRestaurantsByAdminId(loggedInAdmin.getAdminId());
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("restaurantCount", restaurants.size());
        return "admin/restaurant";
    }
    
    @GetMapping("/add")
    public String showAddRestaurantForm() {
        return "admin/addRestaurant";
    }
    
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addRestaurant(@RequestBody RestaurantDTO restaurantDTO,HttpSession session) {
    	Map<String, String> response = new HashMap<>();
        Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
    
        if(!Validation.isValidPhoneNumber(restaurantDTO.getContact())) {
        	response.put("errorContact", "Invalid phone number");
        	return ResponseEntity.ok(response);
        }
        
        restaurantService.addRestaurant(restaurantDTO, loggedInAdmin.getAdminId());
        response.put("redirectUrl", "/admin/restaurants");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/edit/{restaurantId}")
    public String showEditRestaurantForm(@PathVariable String restaurantId, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant", restaurant);
        return "admin/editRestaurant";
    }

    @PostMapping("/edit")
    public ResponseEntity<Map<String, String>> editRestaurant(@RequestBody RestaurantDTO restaurantDTO ) {
    	Map<String, String> response = new HashMap<>();

    	if(!Validation.isValidPhoneNumber(restaurantDTO.getContact())) {
    		response.put("errorContact", "Invalid phone number");
        	return ResponseEntity.ok(response);
        }
        restaurantService.updateRestaurant(restaurantDTO);
        response.put("redirectUrl", "/admin/restaurants");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{restaurantId}")
    public String deleteRestaurant(@PathVariable String restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return "redirect:/admin/restaurants";
    }
    
}

