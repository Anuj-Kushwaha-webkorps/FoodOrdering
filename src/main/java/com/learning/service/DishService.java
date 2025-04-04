package com.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.DTO.DishDTO;
import com.learning.entity.Dish;
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
    
    public void addDish(DishDTO dishDTO, String restaurantId) {
        Dish dish = ObjectFactory.createDishObject(restaurantId, dishDTO.getName(), dishDTO.getDescription(), dishDTO.getPrice(), dishDTO.getDishType(), dishDTO.getDishSize());
        dishRepository.save(dish);
    }
    
    public Dish getDishById(String dishId) {
        Optional<Dish> dish = dishRepository.findById(dishId);
        return dish.orElse(null);
    }

    public void updateDish(DishDTO dishDTO) {
        Optional<Dish> dishOptional = dishRepository.findById(dishDTO.getDishId());
        if (dishOptional.isPresent()) {
            Dish dish = dishOptional.get();
            dish = UpdateObjectFactory.updateDishObject(dish, dishDTO.getDishId(), dishDTO.getName(), dishDTO.getDescription(), dishDTO.getPrice(), dishDTO.getDishType(), dishDTO.getDishSize());
            dishRepository.save(dish);
        }
    }

    public void deleteDish(String dishId) {
        dishRepository.deleteById(dishId);
    }
}

