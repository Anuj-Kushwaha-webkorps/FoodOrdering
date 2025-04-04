package com.learning.DTO;

import lombok.Data;

@Data
public class DishDTO {
	private String dishId;
	private String name;
	private String description;
	private String dishType;
	private Double price;
	private String dishSize;
}
