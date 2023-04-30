package com.foodtogo.spring.security.login.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.foodtogo.spring.security.login.models.Food;
import com.foodtogo.spring.security.login.pdfexport.FoodPdfExporter;
import com.foodtogo.spring.security.login.repository.FoodRepository;
import com.foodtogo.spring.security.login.security.services.FoodService;
import com.lowagie.text.DocumentException;



/*import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
*/
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api")
//@Api(value="Employee Management System", description="Operations pertaining to FoodController  System")
public class FoodController {

	@Autowired
	FoodService foodService;

	@Autowired
	FoodRepository foodRepository;
    
	//@ApiOperation(value = "View a list of Foods  Available", response = List.class)
	//@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
		//	@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			//@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			//@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/foods")
	@ResponseBody
	@Throttling(timeFrameInSeconds = 60, calls = 10)
	public ResponseEntity<List<Food>> getAllFoods(@RequestParam(required = false) Long id) {
		try {
			List<Food> foods = new ArrayList<Food>();

			if (id == null)
				foodService.fetchFoodList().forEach(foods::add);
			else
				foodService.fetchFoodListbyId(id);// .forEach(foods::add);

			return new ResponseEntity<>(foods, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//@ApiOperation(value = "Get an Food by Id")
	@Throttling(timeFrameInSeconds = 60, calls = 10)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/foods/{id}")
	@ResponseBody
	public ResponseEntity<Food> getFoodById(@PathVariable("id") long id) {
		Optional<Food> fooddata = Optional.ofNullable(foodRepository.findById(id));

		if (fooddata.isPresent()) {
			return new ResponseEntity<>(fooddata.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	//@ApiOperation(value = "Insert the Food Items")
	@Throttling(timeFrameInSeconds = 60, calls = 10)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/foods")
	@ResponseBody
	public ResponseEntity<Food> createTutorial(@RequestBody Food food) {
		try {

			foodService.saveFood(food);

			return new ResponseEntity<>(food, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//@ApiOperation(value = "update the Food Items by Id")
	@Throttling(timeFrameInSeconds = 60, calls = 10)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/foods/{id}")
	public ResponseEntity<Food> updateTutorial(@PathVariable("id") long id, @RequestBody Food food) {
		Optional<Food> foods = Optional.ofNullable(foodRepository.findById(id));
		
		if (foods.isPresent()) {
			try
			{
			foodService.updateFood(food, id);
			
			return new ResponseEntity<Food>(HttpStatus.OK);
	      	}
		
		    catch(Exception e)
		     {
			 return new  ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		      }
		
		      }
		else
		   {
				 return new  ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			}
	    }
	
	
	//@ApiOperation(value = "Delete an employee by Id")
	@Throttling(timeFrameInSeconds = 60, calls = 10)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/foods/{id}")
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteFood(@PathVariable("id") long id) {
		try {
			foodService.deleteFoodById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/users/export/foodpdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        List<Food> listFood = foodService.fetchFoodList();
         
        FoodPdfExporter exporter = new FoodPdfExporter(listFood);
        exporter.export(response);
         
    }

}
