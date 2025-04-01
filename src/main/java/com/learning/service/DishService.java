package com.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Dish;
import com.learning.entity.Restaurant;
import com.learning.helper.ObjectFactory;
import com.learning.helper.UpdateObjectFactory;
import com.learning.repository.DishRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    public List<Dish> getDishesByRestaurantId(String restaurantId) {
        return dishRepository.findByRestaurantRestaurantId(restaurantId);
    }
    
    public void addDish(String restaurantId, String name, String description, Double price, String dishType, String dishSize) {
        Dish dish = ObjectFactory.createDishObject(restaurantId, name, description, price, dishType, dishSize);
        dishRepository.save(dish);
    }
    
    public Dish getDishById(String dishId) {
        Optional<Dish> dish = dishRepository.findById(dishId);
        return dish.orElse(null);
    }

    public void updateDish(String dishId, String name, String description, Double price, String dishType, String dishSize) {
        Optional<Dish> dishOptional = dishRepository.findById(dishId);
        if (dishOptional.isPresent()) {
            Dish dish = dishOptional.get();
            dish = UpdateObjectFactory.updateDishObject(dish, dishId, name, description, price, dishType, dishSize);
            dishRepository.save(dish);
        }
    }

    public void deleteDish(String dishId) {
        dishRepository.deleteById(dishId);
    }
}

