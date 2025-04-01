package com.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Admin;
import com.learning.entity.Restaurant;
import com.learning.helper.ObjectFactory;
import com.learning.helper.UpdateObjectFactory;
import com.learning.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getRestaurantsByAdminId(String adminId) {
        return restaurantRepository.findByAdminAdminId(adminId);
    }
    
    public void addRestaurant(String name, String address, String contact, String adminId) {
        Restaurant restaurant = ObjectFactory.createRestaurantObject(name, address, contact, adminId);
        restaurantRepository.save(restaurant);
    }
    
    public Restaurant getRestaurantById(String restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        return restaurant.orElse(null);
    }

    public void updateRestaurant(String restaurantId, String name, String address, String contact) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            restaurant = UpdateObjectFactory.updateRestaurantObject(restaurant, name, address, contact);
            restaurantRepository.save(restaurant);
        }
    }

    public void deleteRestaurant(String restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
