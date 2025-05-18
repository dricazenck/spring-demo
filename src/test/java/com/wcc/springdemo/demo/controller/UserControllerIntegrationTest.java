package com.wcc.springdemo.demo.controller;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.context.TestSecurityContextHolder.setAuthentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.springdemo.demo.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private UserController controller;

  @BeforeEach
  void setUp() {
    setAuthentication(new TestingAuthenticationToken("user", "password", "ROLE_ADMIN"));
  }

  @Test
  void shouldFailValidationWhenEmailFormatIsInvalid() throws Exception {
    var invalidEmail = "invalid-email";
    var user = new User("test-id", "testuser", "Test", "User", "Test User", invalidEmail);

    mockMvc
        .perform(
            post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Invalid email format"));
  }

  @Test
  void shouldFailValidationWhenMandatoryFieldsAreMissing() throws Exception {
    User user = new User("test-id", EMPTY, EMPTY, EMPTY, "Test User", EMPTY);

    mockMvc
        .perform(
            post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.username").value("Username is required"))
        .andExpect(jsonPath("$.firstName").value("First name is required"))
        .andExpect(jsonPath("$.lastName").value("Lastname is required"))
        .andExpect(jsonPath("$.email").value("Email is required"));
  }

  @Test
  void shouldFailValidationWhenEmailIsNull() throws Exception {
    User user =
        new User(
            "test-id", "testuser", "Test", "User", "Test User", null // Null email
            );

    mockMvc
        .perform(
            post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Email is required"));
  }

  @Test
  void testAllUsers() {
    var users = controller.getAllUsers();
    assertEquals(2, users.size());
  }

  @Test
  void testGetUserByUserName() {
    var response = controller.getUserByUsername("Adriana");
    assertEquals(200, response.getStatusCode().value());
    Assertions.assertNotNull(response.getBody());
    assertEquals("Adriana", response.getBody().getFirstName());
  }
}
