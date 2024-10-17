package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.repository.AuthRepository;
import com.example.demo.service.AuthService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3000")

public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) throws Exception {
        boolean isAuthenticated = authService.authenticate(request.getEmail(), request.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok("Login exitoso.");
        } else {
            return ResponseEntity.status(401).body("Email o contraseña incorrectos.");
        }
    }

    public static class LoginRequest {
        @NotNull
        private String email;


        private String password;


        // Getters y setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users request) throws Exception {
        if(authRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body("El email ingresado ya ha sido registrado.");
        }
        authService.registerUser(request.getEmail(), request.getPassword(), request.getName(), request.getCI(), request.getAge(), request.getSurname(), request.getRole());
        return ResponseEntity.status(201).body("Usuario registrado exitosamente.");
    }
}