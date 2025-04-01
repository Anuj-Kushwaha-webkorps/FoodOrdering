package com.learning.helper;

import com.learning.entity.Dish;
import com.learning.entity.Restaurant;

public class UpdateObjectFactory {

	public static Dish updateDishObject(Dish dish, String dishId, String name, String description, Double price, String dishType, String dishSize) {
		dish.setName(name);
        dish.setDescription(description);
        dish.setPrice(price);
        dish.setDishType(Dish.DishType.valueOf(dishType));
        dish.setDishSize(Dish.DishSize.valueOf(dishSize));
        return dish;
	}
	
	public static Restaurant updateRestaurantObject(Restaurant restaurant, String name, String address, String contact) {
		restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setContactNumber(contact);
        return restaurant;
	}
}
