package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * UserController is a REST controller that handles requests related to user management.
 * It provides endpoints for creating, viewing, updating, and deleting users.
 */
@RestController  // RestController for JSON responses
@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    /**
     * Constructs a UserController with the specified UserService.
     *
     * @param userService the UserService to be used by this controller
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Lists all users.
     *
     * @return a ResponseEntity containing the list of users and a 200 OK status
     */
    @GetMapping
    public ResponseEntity<List<Users>> listUsers() {
        List<Users> usersList = userService.getAllUsers();
        return ResponseEntity.ok(usersList);  // Return list of users with 200 OK
    }

    // Creation of users is done through registration.

    /**
     * Views a specific user by their ID.
     *
     * @param id the ID of the user to be viewed
     * @return a ResponseEntity containing the user if found, or a 404 Not Found status if not
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Users> viewUser(@PathVariable Long id) {
        Users user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();  // Return 404 Not Found if user doesn't exist
        }
        return ResponseEntity.ok(user);  // Return the user with 200 OK
    }

    /**
     * Updates an existing user by their ID.
     *
     * @param id the ID of the user to be updated
     * @param updatedUser the updated user data
     * @return a ResponseEntity indicating the outcome of the update operation
     */
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

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted
     * @return a ResponseEntity indicating the outcome of the delete operation
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id) == null) {  // Check if user exists before deletion
            return ResponseEntity.notFound().build();  // Return 404 Not Found if user doesn't exist
        }
        userService.deleteUser(id);  // Delete the user by ID
        return ResponseEntity.noContent().build();  // Return 204 No Content after deletion
    }

    /**
     * Creates a new user.
     *
     * @param user the user data to be created
     * @return a ResponseEntity containing the created user and a 201 Created status
     */
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