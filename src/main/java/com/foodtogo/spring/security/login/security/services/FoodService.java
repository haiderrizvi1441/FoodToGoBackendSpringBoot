package com.foodtogo.spring.security.login.security.services;

import java.util.List;
import java.util.Optional;

import com.foodtogo.spring.security.login.models.Food;

public interface FoodService {
	
	// Save operation
    Food saveFood(Food food);
 
    // Read operation
    List<Food> fetchFoodList();
 
    // Update operation
    Food updateFood(Food food,Long id);
 
    // Delete operation
    void deleteFoodById(Long id);
    
    Optional<Food> fetchFoodListbyId(Long id);

}
