package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.learning.entity.Dish;
import com.learning.service.DishService;

import java.util.List;

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
    public String addDish(@PathVariable String restaurantId,
                          @RequestParam("name") String name,
                          @RequestParam("description") String description,
                          @RequestParam("price") Double price,
                          @RequestParam("dishType") String dishType,
                          @RequestParam("dishSize") String dishSize) {
        dishService.addDish(restaurantId, name, description, price, dishType, dishSize);
        return "redirect:/admin/restaurants/" + restaurantId + "/dishes";
    }
    
    @GetMapping("/edit/{dishId}")
    public String showEditDishForm(@PathVariable String restaurantId, @PathVariable String dishId, Model model) {
        Dish dish = dishService.getDishById(dishId);
        model.addAttribute("dish", dish);
        model.addAttribute("restaurantId", restaurantId);
        return "admin/editDish";
    }

    @PostMapping("/edit")
    public String editDish(@PathVariable String restaurantId,
                           @RequestParam("dishId") String dishId,
                           @RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("price") Double price,
                           @RequestParam("dishType") String dishType,
                           @RequestParam("dishSize") String dishSize) {
        dishService.updateDish(dishId, name, description, price, dishType, dishSize);
        return "redirect:/admin/restaurants/" + restaurantId + "/dishes";
    }

    @GetMapping("/delete/{dishId}")
    public String deleteDish(@PathVariable String restaurantId, @PathVariable String dishId) {
        dishService.deleteDish(dishId);
        return "redirect:/admin/restaurants/" + restaurantId + "/dishes";
    }
}

