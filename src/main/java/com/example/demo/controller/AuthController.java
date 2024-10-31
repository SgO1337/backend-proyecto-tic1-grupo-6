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

    /**
     * Autentica al usuario con las credenciales proporcionadas en el request.
     * @param request Objeto LoginRequest con los campos de email y password.
     * @return ResponseEntity con un mensaje de éxito en caso de autenticación exitosa,
     *         o un mensaje de error con código 401 si las credenciales no son válidas.
     * @throws Exception en caso de un error de autenticación.
     */
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

        /**
         * Obtiene el email del usuario.
         * @return email del usuario.
         */
        public String getEmail() {
            return email;
        }

        /**
         * Establece el email del usuario.
         * @param email email del usuario.
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * Obtiene la contraseña del usuario.
         * @return password del usuario.
         */
        public String getPassword() {
            return password;
        }

        /**
         * Establece la contraseña del usuario.
         * @param password contraseña del usuario.
         */
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
