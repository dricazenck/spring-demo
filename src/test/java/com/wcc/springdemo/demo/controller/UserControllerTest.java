package com.wcc.springdemo.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.springdemo.demo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllUsersOk() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].username", is("adriana")));
    }

    @Test
    void testCreateUserOk() throws Exception {
        mockMvc.perform(post("/api/v1/user")
                        .content(objectMapper.writeValueAsString(new User("3", "username", "FirstName", "LastName", "FirstName LastName", "username@email.com")))  // Sending the new user name in the body
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        // Verify that the new user is added to the list via GET request
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[2].username", is("username")));;
      }

    @Test
    void getUserById() {
      }

    @Test
    void getUserByUserName() {
      }
}