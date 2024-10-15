package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Fetch all users
    public List<Users> getAllUsers() {
        return userRepository.findAll();  // Returns a list of users in JSON format when used by REST API
    }

    // Fetch a single user by ID
    public Users getUserById(Long id) {
        // Return the user or handle if the user is not found (Optional)
        return userRepository.findById(id).orElse(null);
    }


    // Save a new user or update an existing one
    @Transactional
    public Users saveUser(Users user) {
        // Save the user and return the updated user entity
        return userRepository.save(user);
    }

    // Delete a user by ID
    @Transactional
    public void deleteUser(Long id) {
        // Optionally, you can check if the user exists before deletion
        Optional<Users> user = userRepository.findById(id);
        user.ifPresent(u -> userRepository.deleteById(id));
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

}