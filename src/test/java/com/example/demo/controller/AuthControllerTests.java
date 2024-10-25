package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.repository.AuthRepository;
import com.example.demo.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthRepository authRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private AuthController.LoginRequest loginRequest;
    private Users user;

    @BeforeEach
    void setUp() {
        loginRequest = new AuthController.LoginRequest();
        loginRequest.setEmail("john@example.com");
        loginRequest.setPassword("password");

        user = new Users();
        user.setId(1L);
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setName("John");
        user.setSurname("Doe");
        user.setRole("USER");
        user.setCI(123456);
        user.setAge(25);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void login_shouldReturnOkForValidCredentials() throws Exception {
        // Mock successful authentication
        Mockito.when(authService.authenticate("john@example.com", "password")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login exitoso."));
    }

    @Test
    void login_shouldReturnUnauthorizedForInvalidCredentials() throws Exception {
        // Mock failed authentication
        Mockito.when(authService.authenticate("john@example.com", "wrongpassword")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john@example.com\",\"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Email o contraseña incorrectos."));
    }

    @Test
    void register_shouldReturnCreatedForNewUser() throws Exception {
        // Mock successful registration
        Mockito.when(authRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        Mockito.doNothing().when(authService).registerUser(any(String.class), any(String.class), any(String.class), any(Integer.class), any(Integer.class), any(String.class), any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"newuser@example.com\",\"password\":\"password123\",\"name\":\"New\",\"surname\":\"User\",\"ci\":789012,\"age\":22,\"role\":\"USER\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Usuario registrado exitosamente."));
    }

    @Test
    void register_shouldReturnBadRequestForExistingEmail() throws Exception {
        // Mock email conflict
        Mockito.when(authRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john@example.com\",\"password\":\"password\",\"name\":\"John\",\"surname\":\"Doe\",\"ci\":123456,\"age\":25,\"role\":\"USER\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El email ingresado ya ha sido registrado."));
    }
}
