package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.repository.AuthRepository;
import com.example.demo.service.AuthService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

    /**
     * Authenticates the user with the provided credentials in the request.
     * @param request Object containing email and password.
     * @param response HttpServletResponse to set the cookie with the token.
     * @return ResponseEntity with a success message or an error message.
     * @throws Exception in case of authentication error.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) throws Exception {
        boolean isAuthenticated = authService.authenticate(request.getEmail(), request.getPassword());

        if (isAuthenticated) {
            // Fetch the user ID from your database (this is an example; adjust as needed)
            Long userId = authRepository.findByEmail(request.getEmail()).get().getIdUser();
            String token = generateToken(userId.toString());

            // Set the token in an HttpOnly cookie
            Cookie cookie = new Cookie("SESSIONID", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // Set to true if using HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(86400); // 1 day expiration
            response.addCookie(cookie);

            // Create response body with message and user ID
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful.");
            responseBody.put("userId", userId); // Include userId in the response

            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password."));
        }
    }

    // Token generation method
    private String generateToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .sign(Algorithm.HMAC256("your_secret_key")); // Use a strong secret key
    }

    public static class LoginRequest {
        @NotNull
        private String email;
        private String password;

        // Getters and setters
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


/**
     * Registra un nuevo usuario en el sistema con la información proporcionada.
     * @param request Objeto Users que contiene los datos necesarios para el registro.
     * @return ResponseEntity con un mensaje de éxito en caso de registro exitoso,
     *         o un mensaje de error con código 400 si el email ya existe.
     * @throws Exception en caso de error durante el proceso de registro.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users request) throws Exception {
        if(authRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body("El email ingresado ya ha sido registrado.");
        }
        authService.registerUser(request.getEmail(), request.getPassword(), request.getName(), request.getCI(), request.getAge(), request.getSurname(), request.getRole());
        return ResponseEntity.status(201).body("Usuario registrado exitosamente.");
    }
}
