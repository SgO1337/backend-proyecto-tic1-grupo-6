package com.example.demo.Controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.controller.AuthController;
import com.example.demo.controller.UserController;
import com.example.demo.model.Users;
import com.example.demo.repository.AuthRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.antlr.v4.runtime.misc.NotNull;
import org.junit.jupiter.api.BeforeEach;  // Use JUnit 5's @BeforeEach
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(AuthController.class)
@ActiveProfiles("test")  // Use the test profile with PostgreSQL (application-test.properties)
@AutoConfigureMockMvc
public class AuthControllerTests {


    @Autowired
    private AuthController authController;

    @BeforeEach  // Use @BeforeEach for JUnit 5
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserController userscontroller;  // Mock the service

    @MockBean
    private UserService userService;  // Mock the service

    @MockBean
    private AuthRepository authRepository;  // Mock the repository

    @MockBean
    private AuthService authService;  // Mock the service

    @Autowired
    private ObjectMapper objectMapper;  // For converting objects to JSON

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void testRegistrarUsuario() throws Exception {

        // Create a test user object
        Users usuario = new Users();
        usuario.setName("Santi");
        usuario.setCI(12345678);
        usuario.setSurname("Garcia");
        usuario.setEmail("testuser@example.com");
        usuario.setPassword("P@assword123");
        usuario.setRole("user");
        usuario.setAge(20);

        // Convert user to JSON
        String usuarioJson = objectMapper.writeValueAsString(usuario);

        // Simulate the POST request to the registration endpoint
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(status().isCreated())  // Check that it returns 201 CREATED
                .andExpect(content().string("Usuario registrado exitosamente."));


    }

    @Test
    public void testLoginUsuario() throws Exception {
        Users usuario = new Users();
        usuario.setName("Santi");
        usuario.setCI(12345678);
        usuario.setSurname("Garcia");
        usuario.setEmail("testuser@example.com");
        usuario.setPassword("P@assword123");
        usuario.setRole("user");
        usuario.setAge(20);
        authService.registerUser(usuario.getEmail(), usuario.getPassword(), usuario.getName(), usuario.getCI(), usuario.getAge(), usuario.getSurname(), usuario.getRole());
        // Convert user to JSON

        LoginRequest lr = new LoginRequest();
        lr.setEmail("testuser@example.com");
        lr.setPassword("P@assword123");
        String loginUsuarioJson = objectMapper.writeValueAsString(lr);

        // Simulate the POST request to the registration endpoint
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginUsuarioJson))
                .andExpect(status().isOk())  // Check that it returns 200 OK
                .andExpect(content().string("Login exitoso."));
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
}
