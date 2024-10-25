package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Users user1;
    private Users user2;

    @BeforeEach
    void setUp() {
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateUser_shouldReturnConflictIfEmailExists() throws Exception {
        // JSON data for a user attempting to update with an existing email
        String conflictingEmailJson = "{\"ci\":111111,\"name\":\"Conflict\",\"surname\":\"User\",\"email\":\"jane@example.com\",\"password\":\"password123\",\"role\":\"USER\",\"age\":30}";

        // The email "jane@example.com" is already used by user2, so this should trigger a conflict
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(conflictingEmailJson))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already exists."));
    }

    @Test
    void deleteUser_shouldDeleteUserAndReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createUser_shouldCreateAndReturnUser() throws Exception {
        // Set up a user JSON for the request
        String newUserJson = "{\"ci\":789012,\"name\":\"New User\",\"surname\":\"Test\",\"email\":\"newuser@example.com\",\"password\":\"password123\",\"role\":\"USER\",\"age\":22}";

        // Mocking UserService behavior for a new user
        Users newUser = new Users();
        newUser.setId(3L);
        newUser.setCI(789012);
        newUser.setName("New User");
        newUser.setSurname("Test");
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("password123");
        newUser.setRole("USER");
        newUser.setAge(22);

        Mockito.when(userService.saveUser(any(Users.class))).thenReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"idUser\":3,\"ci\":789012,\"name\":\"New User\",\"surname\":\"Test\",\"email\":\"newuser@example.com\",\"password\":\"password123\",\"role\":\"USER\",\"age\":22}"));
    }

    @Test
    void createUser_shouldReturnConflictIfEmailExists() throws Exception {
        // Prepare JSON for a user with an existing email
        String duplicateUserJson = "{\"ci\":111222,\"name\":\"Duplicate\",\"surname\":\"User\",\"email\":\"john@example.com\",\"password\":\"duplicatepass\",\"role\":\"USER\",\"age\":30}";

        // Since `john@example.com` exists, the service should return a conflict
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicateUserJson))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already exists."));
    }

}
