package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.security.NoSuchAlgorithmException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Date;
import java.util.Optional;

import com.example.demo.repository.UserRepository;
import com.example.demo.model.Users;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UserRepository UserRepository;


    public boolean authenticate(String email, String password) {
        Optional<Users> userOpt = UserRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            Users user = userOpt.get();

            // Compare the hashed password
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }


    public String generateToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .sign(Algorithm.HMAC256("your_secret_key")); // Use a strong secret key
    }



    public void registerUser(String email, String password, String name, Integer ci, Integer age, String surname, String role) {
        // Check if the email is already registered
        if (UserRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Email already registered");
        }

        // Create new user with hashed password
        Users newUser = new Users();
        newUser.setEmail(email);
        newUser.setPassword(password); // Store the hashed password as is
        newUser.setName(name);
        newUser.setCI(ci);
        newUser.setRole(role);
        newUser.setAge(age);
        newUser.setSurname(surname);
        UserRepository.save(newUser);
    }

}
