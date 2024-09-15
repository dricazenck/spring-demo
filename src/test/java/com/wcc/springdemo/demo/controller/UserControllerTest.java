package com.wcc.springdemo.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.springdemo.demo.domain.User;
import com.wcc.springdemo.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private UserService userService;

  @Test
  public void testGetAllUsersOk() throws Exception {
    var users =
        List.of(
            new User(
                "1",
                "username_1",
                "FirstName_1",
                "LastName_1",
                "FirstName_1 LastName_1",
                "username_1@mail.com"));

    when(userService.getAll()).thenReturn(users);

    mockMvc
        .perform(get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$.[0].username", is("username_1")));
  }

  @Test
  void testCreateUserOk() throws Exception {
    var user =
        new User(
            "2",
            "username_2",
            "FirstName_2",
            "LastName_2",
            "FirstName_2 LastName_2",
            "username_2@mail.com");

    when(userService.addUser(user)).thenReturn(user);

    mockMvc
        .perform(
            post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isOk());
    // .andExpect(content().json(userAsString(user)));
  }

  private String userAsString(final User user) {
    try {
      return objectMapper.writeValueAsString(user);
    } catch (IOException e) {
      return User.class.toString();
    }
  }

  @Test
  void testGetUserByUserNameOk() throws Exception {
    var users =
        List.of(
            new User(
                "1",
                "username_1",
                "FirstName_1",
                "LastName_1",
                "FirstName_1 LastName_1",
                "username_1@mail.com"),
            new User(
                "2",
                "username_2",
                "FirstName_2",
                "LastName_2",
                "FirstName_2 LastName_2",
                "username_2@mail.com"));

    when(userService.getAll()).thenReturn(users);

    mockMvc
        .perform(get("/api/v1/user/username_1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is("username_1")));
  }
}