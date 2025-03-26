package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learning.entity.Admin;
import com.learning.entity.Restaurant;
import com.learning.helper.Validation;
import com.learning.service.RestaurantService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/admin/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public String showRestaurants(Model model, HttpSession session) {
        Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (loggedInAdmin == null) {
            return "redirect:/admin/login?error=Unauthorized+Access";
        }
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
    public String addRestaurant(@RequestParam("name") String name,
                                @RequestParam("address") String address,
                                @RequestParam("contact") String contact,
                                HttpSession session) {
        Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (loggedInAdmin == null) {
            return "redirect:/admin/login?error=Unauthorized+Access";
        }
        
        if(!Validation.isValidPhoneNumber(contact)) {
        	return "redirect:/admin/restaurants/add?error=Invalid+Phone+number";
        }
        
        restaurantService.addRestaurant(name, address, contact, loggedInAdmin.getAdminId());
        return "redirect:/admin/restaurants";
    }
    
    @GetMapping("/edit/{restaurantId}")
    public String showEditRestaurantForm(@PathVariable String restaurantId, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant", restaurant);
        return "admin/editRestaurant";
    }

    @PostMapping("/edit")
    public String editRestaurant(@RequestParam("restaurantId") String restaurantId,
                                 @RequestParam("name") String name,
                                 @RequestParam("address") String address,
                                 @RequestParam("contact") String contact) {
    	if(!Validation.isValidPhoneNumber(contact)) {
        	return "redirect:/admin/restaurants/edit/"+restaurantId+"?error=Invalid+Phone+number";
        }
        restaurantService.updateRestaurant(restaurantId, name, address, contact);
        return "redirect:/admin/restaurants";
    }

    @GetMapping("/delete/{restaurantId}")
    public String deleteRestaurant(@PathVariable String restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return "redirect:/admin/restaurants";
    }
    
}

