package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        Users existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();  // Return 404 Not Found if user doesn't exist
        }

        // Check if the email is being updated and if it already exists
        if (!existingUser.getEmail().equals(updatedUser.getEmail())) {
            for (Users user : userService.getAllUsers()) {
                if (user.getEmail().equals(updatedUser.getEmail())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
                }
            }
        }

        // Update all user attributes
        existingUser.setCI(updatedUser.getCI());
        existingUser.setName(updatedUser.getName());
        existingUser.setSurname(updatedUser.getSurname());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setAge(updatedUser.getAge());

        Users savedUser = userService.saveUser(existingUser);  // Save and return the updated user
        return ResponseEntity.ok("User updated successfully.");  // Return 200 OK
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


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Users user) {
        // Set default values if necessary
        if (user.getRole() == null) {
            user.setRole("USER");
        }

        // Check if the email already exists
        for (Users existingUser : userService.getAllUsers()) {
            if (Objects.equals(existingUser.getEmail(), user.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
            }
        }

        Users createdUser = userService.saveUser(user);  // Save and return the created user
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);  // Return 201 Created
    }
}
