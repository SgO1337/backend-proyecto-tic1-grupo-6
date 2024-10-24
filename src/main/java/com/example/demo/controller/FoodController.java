package com.example.demo.controller;

import com.example.demo.model.Food;
import com.example.demo.service.FoodService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Keep RestController for JSON responses
@RequestMapping("/api/food")  // Base URL starts with /api
@CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for your React app
public class FoodController {

    private final FoodService foodService;

    // Constructor that injects the FoodService
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // List all foods
    @GetMapping
    public ResponseEntity<List<Food>> listFood() {
        List<Food> foodList = foodService.getAllFood();
        return ResponseEntity.ok(foodList);  // Return food list with HTTP 200 OK
    }

    // View a specific food by ID
    @GetMapping("/view/{id}")
    public ResponseEntity<Food> viewFood(@PathVariable Long id) {
        Food food = foodService.getFoodById(id);
        if (food == null) {
            return ResponseEntity.notFound().build();  // Return HTTP 404 if not found
        }
        return ResponseEntity.ok(food);  // Return food with HTTP 200 OK
    }

    // Create a new food
    @PostMapping("/create")
    public ResponseEntity<?> createFood(@RequestBody Food food) {
        if(food.getName() == null || food.getName().isEmpty() || food.getPrice() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("incomplete set of data");  // Return HTTP 400 Bad Request if incomplete data
        }
        Food createdFood = foodService.saveFood(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFood);  // Return created food with HTTP 201 Created
    }

    // Update an existing food
    @PutMapping("/update/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody Food food) {
        Food existingFood = foodService.getFoodById(id);
        if (existingFood == null) {
            return ResponseEntity.notFound().build();  // Return HTTP 404 if not found
        }

        // Update the fields of the existing food
        existingFood.setName(food.getName());
        existingFood.setPrice(food.getPrice());

        // Save the updated food
        Food updatedFood = foodService.saveFood(existingFood);
        return ResponseEntity.ok(updatedFood);  // Return updated food with HTTP 200 OK
    }

    // Delete a food
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();  // Return HTTP 204 No Content
    }
}
