
package com.foodtogo.spring.security.login.security.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.foodtogo.spring.security.login.models.Food;
import com.foodtogo.spring.security.login.repository.FoodRepository;
import com.foodtogo.spring.security.login.security.services.FoodService;
import com.foodtogo.spring.security.login.security.services.OrderService;


//import com.food.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

	private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);
	@Autowired
	private FoodRepository foodRepository;

	@Override
	public Food saveFood(Food food) {
        logger.info("{},{},Food is saved",FoodServiceImpl.class.getName());
		return foodRepository.save(food);

	}

	@Override
	@Cacheable(value = "foodcaching",key = "'foodList'")
	public List<Food> fetchFoodList() {
		 logger.info("{}{},Food is fetched method name fetchFoodList ",FoodServiceImpl.class.getName());
		return (List<Food>) foodRepository.findAll();
	}

	@CachePut(value="foodcaching")
	@Override public Food updateFood(Food food, Long id)
	{
		
		 logger.info("{},{},Food item  is updated,method name updateFood",FoodServiceImpl.class.getName());
        Food foodservice = foodRepository.findById(id).get();
  
       if (Objects.nonNull( foodservice.getFood_id()) && !"".equalsIgnoreCase(
       foodservice.getFood_id()))
       { 
    	   foodservice.setFood_id(foodservice.getFood_id());
    	  
       }
  
       if (Objects.nonNull( foodservice.getFood_name()) && !"".equalsIgnoreCase(
    	       foodservice.getFood_name()))
    	       { 
    	    	   foodservice.setFood_name(foodservice.getFood_name());
    	       }
       
          foodservice.setFood_price(foodservice.getFood_price());
          
          if (Objects.nonNull( foodservice.getFood_category()) && !"".equalsIgnoreCase(
       	       foodservice.getFood_category()))
       	       { 
       	    	   foodservice.setFood_category(foodservice.getFood_category());
       	       }
          
             foodservice.setFood_price(foodservice.getFood_price());      
             
          return foodRepository.save(foodservice);
  
     }
	@Cacheable(value="foodcaching", key="#id")
	@Override public void deleteFoodById(Long id) { 
		 logger.info("{},{},Food id {} is Deleted ,Method Name deleteFoodById ",FoodServiceImpl.class.getName(),id);
		foodRepository.deleteById(id);
	}

	@CacheEvict(value = "foodcaching", key = "#id")
	@Override
	public Optional<Food> fetchFoodListbyId(Long id) {
		 logger.info("{},{},Food id {} is fetched method name fetchFoodListbyId",FoodService.class.getName(),id);
		return  foodRepository.findById(id);
	}

  
  }


