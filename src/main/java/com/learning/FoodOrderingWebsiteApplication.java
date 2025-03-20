package com.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodOrderingWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderingWebsiteApplication.class, args);
	}

}
