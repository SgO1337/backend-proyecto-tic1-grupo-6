package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // RestController for JSON responses
@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // List all users
    @GetMapping
    public ResponseEntity<List<Users>> listUsers() {
        List<Users> usersList = userService.getAllUsers();
        return ResponseEntity.ok(usersList);  // Return list of users with 200 OK
    }

    //creacion de usuarios es por registro

    // View a specific user by ID
    @GetMapping("/view/{id}")
    public ResponseEntity<Users> viewUser(@PathVariable Long id) {
        Users user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();  // Return 404 Not Found if user doesn't exist
        }
        return ResponseEntity.ok(user);  // Return the user with 200 OK
    }

    // Update an existing user
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        Users existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();  // Return 404 Not Found if user doesn't exist
        }

        // Update fields
        existingUser.setName(updatedUser.getName());
        for (Users user : userService.getAllUsers()) {
            if (user.getEmail().equals(updatedUser.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");
            }
        }
        existingUser.setEmail(updatedUser.getEmail());

        Users savedUser = userService.saveUser(existingUser);  // Save and return the updated user
        return ResponseEntity.ok("User created successfully.");  // Return the updated user with 200 OK
    }

    // Delete a user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id) == null) {  // Check if user exists before deletion
            return ResponseEntity.notFound().build();  // Return 404 Not Found if user doesn't exist
        }
        userService.deleteUser(id);  // Delete the user by ID
        return ResponseEntity.noContent().build();  // Return 204 No Content after deletion
    }
}
