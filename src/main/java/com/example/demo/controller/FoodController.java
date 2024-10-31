package com.example.demo.controller;

import com.example.demo.model.Food;
import com.example.demo.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // RestController para respuestas JSON
@RequestMapping("/api/food")  // URL base comienza con /api
@CrossOrigin(origins = "http://localhost:3000")  // Permitir CORS para la app de React
public class FoodController {

    private final FoodService foodService;

    // Constructor que inyecta el servicio FoodService
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * Obtiene la lista completa de alimentos.
     * @return ResponseEntity con la lista de todos los alimentos en formato JSON y código HTTP 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Food>> listFood() {
        List<Food> foodList = foodService.getAllFood();
        return ResponseEntity.ok(foodList);  // Retorna la lista de alimentos con HTTP 200 OK
    }

    /**
     * Obtiene un alimento específico por su ID.
     * @param id ID del alimento a buscar.
     * @return ResponseEntity con el alimento si se encuentra, o código HTTP 404 si no existe.
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Food> viewFood(@PathVariable Long id) {
        Food food = foodService.getFoodById(id);
        if (food == null) {
            return ResponseEntity.notFound().build();  // Retorna HTTP 404 si no se encuentra
        }
        return ResponseEntity.ok(food);  // Retorna el alimento con HTTP 200 OK
    }

    /**
     * Crea un nuevo alimento si se proporciona un nombre y un precio.
     * @param food Objeto Food que contiene los datos del nuevo alimento.
     * @return ResponseEntity con el alimento creado si es exitoso o mensaje de error con código HTTP 400 en caso de datos incompletos.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createFood(@RequestBody Food food) {
        if(food.getName() == null || food.getName().isEmpty() || food.getPrice() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("incomplete set of data");  // Retorna HTTP 400 en caso de datos incompletos
        }
        Food createdFood = foodService.saveFood(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFood);  // Retorna el alimento creado con HTTP 201 Created
    }

    /**
     * Actualiza un alimento existente por su ID.
     * @param id ID del alimento a actualizar.
     * @param food Objeto Food con los nuevos datos para actualizar.
     * @return ResponseEntity con el alimento actualizado si es exitoso o código HTTP 404 si no se encuentra.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody Food food) {
        Food existingFood = foodService.getFoodById(id);
        if (existingFood == null) {
            return ResponseEntity.notFound().build();  // Retorna HTTP 404 si no se encuentra
        }

        // Actualiza los campos del alimento existente
        existingFood.setName(food.getName());
        existingFood.setPrice(food.getPrice());

        // Guarda el alimento actualizado
        Food updatedFood = foodService.saveFood(existingFood);
        return ResponseEntity.ok(updatedFood);  // Retorna el alimento actualizado con HTTP 200 OK
    }

    /**
     * Elimina un alimento por su ID.
     * @param id ID del alimento a eliminar.
     * @return ResponseEntity con código HTTP 204 No Content si la eliminación es exitosa.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();  // Retorna HTTP 204 No Content
    }
}
