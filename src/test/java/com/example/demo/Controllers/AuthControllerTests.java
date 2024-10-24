package com.example.demo.Controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import com.example.demo.controller.UserController;
import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@WithMockUser(username = "user", roles = {"ADMIN"})
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private Users user1;
    private Users user2;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    void setUp() {
        // Sample Users
        // Sample Users using setters
        user1 = new Users();
        user1.setId(1L);
        user1.setCI(123456);
        user1.setName("John");
        user1.setSurname("Doe");
        user1.setEmail("john@example.com");
        user1.setPassword("password");
        user1.setRole("USER");
        user1.setAge(25);

        user2 = new Users();
        user2.setId(2L);
        user2.setCI(654321);
        user2.setName("Jane");
        user2.setSurname("Doe");
        user2.setEmail("jane@example.com");
        user2.setPassword("password");
        user2.setRole("ADMIN");
        user2.setAge(28);

        // Mocking UserService behavior
        Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
        Mockito.when(userService.getUserById(1L)).thenReturn(user1);
        Mockito.when(userService.getUserById(2L)).thenReturn(user2);
        Mockito.when(userService.saveUser(any(Users.class))).thenReturn(user1);
        Mockito.doNothing().when(userService).deleteUser(anyLong());
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void listUsers_shouldReturnListOfUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"idUser\":1,\"ci\":123456,\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"john@example.com\",\"password\":\"password\",\"role\":\"USER\",\"age\":25}," +
                        "{\"idUser\":2,\"ci\":654321,\"name\":\"Jane\",\"surname\":\"Doe\",\"email\":\"jane@example.com\",\"password\":\"password\",\"role\":\"ADMIN\",\"age\":28}]"));
    }

    @Test
    void viewUser_shouldReturnUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/view/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"idUser\":1,\"ci\":123456,\"name\":\"John\",\"surname\":\"Doe\",\"email\":\"john@example.com\",\"password\":\"password\",\"role\":\"USER\",\"age\":25}"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void updateUser_shouldUpdateAndReturnSuccess() throws Exception {
        String userJson = "{\"ci\":987654,\"name\":\"Updated John\",\"surname\":\"Doe\",\"email\":\"updatedjohn@example.com\",\"password\":\"newpassword\",\"role\":\"USER\",\"age\":30}";

        // Log the JSON being sent
        System.out.println("Request JSON: " + userJson);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andDo(print()) // This will log the request and response
                .andExpect(status().isOk())
                .andExpect(content().string("User updated successfully."));
    }


    @Test
    void deleteUser_shouldDeleteUserAndReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/delete/1"))
                .andExpect(status().isNoContent());
    }
}
