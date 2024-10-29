package com.example.demo.controller;
import com.example.demo.model.Food;
import com.example.demo.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

    public class FoodControllerTests {

        @Mock
        private FoodService foodService;

        @InjectMocks
        private FoodController foodController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void listFood_ShouldReturnListOfFood() {
            Food food = new Food();
            when(foodService.getAllFood()).thenReturn(Arrays.asList(food));

            ResponseEntity<List<Food>> response = foodController.listFood();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, response.getBody().size());
            verify(foodService, times(1)).getAllFood();
        }

        @Test
        void viewFood_ShouldReturnFoodIfFound() {
            Food food = new Food();
            when(foodService.getFoodById(1L)).thenReturn(food);

            ResponseEntity<Food> response = foodController.viewFood(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(food, response.getBody());
        }

        @Test
        void viewFood_ShouldReturnNotFoundIfNotFound() {
            when(foodService.getFoodById(1L)).thenReturn(null);

            ResponseEntity<Food> response = foodController.viewFood(1L);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        void createFood_ShouldCreateFoodSuccessfully() {
            Food food = new Food();
            food.setName("Pizza");
            food.setPrice(15.99);

            when(foodService.saveFood(food)).thenReturn(food);

            ResponseEntity<?> response = foodController.createFood(food);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(food, response.getBody());
            verify(foodService, times(1)).saveFood(food);
        }

        @Test
        void createFood_ShouldReturnBadRequestIfDataIncomplete() {
            Food food = new Food();
            food.setName(""); // No name set

            ResponseEntity<?> response = foodController.createFood(food);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("incomplete set of data", response.getBody());
            verify(foodService, never()).saveFood(any(Food.class));
        }

        @Test
        void updateFood_ShouldUpdateFoodSuccessfully() {
            Food existingFood = new Food();
            existingFood.setIdFood(1L);
            existingFood.setName("Burger");
            existingFood.setPrice(10.99);

            Food updatedFood = new Food();
            updatedFood.setName("Cheeseburger");
            updatedFood.setPrice(11.99);

            when(foodService.getFoodById(1L)).thenReturn(existingFood);
            when(foodService.saveFood(existingFood)).thenReturn(existingFood);

            ResponseEntity<Food> response = foodController.updateFood(1L, updatedFood);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(existingFood, response.getBody());
            assertEquals("Cheeseburger", existingFood.getName());
            assertEquals(11.99, existingFood.getPrice());
            verify(foodService, times(1)).saveFood(existingFood);
        }

        @Test
        void updateFood_ShouldReturnNotFoundIfFoodNotFound() {
            Food updatedFood = new Food();
            updatedFood.setName("Cheeseburger");
            updatedFood.setPrice(11.99);

            when(foodService.getFoodById(1L)).thenReturn(null);

            ResponseEntity<Food> response = foodController.updateFood(1L, updatedFood);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(foodService, never()).saveFood(any(Food.class));
        }

        @Test
        void deleteFood_ShouldDeleteFoodIfExists() {
            doNothing().when(foodService).deleteFood(1L);

            ResponseEntity<Void> response = foodController.deleteFood(1L);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(foodService, times(1)).deleteFood(1L);
        }
    }


