package com.wcc.springdemo.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.context.TestSecurityContextHolder.setAuthentication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductControllerIntegrationTest {

  @Autowired private ProductController controller;

  @BeforeEach
  void setUp() {
    setAuthentication(new TestingAuthenticationToken("user", "password", "ROLE_USER"));
  }

  @Test
  void testAll() {
    var response = controller.getAllProducts();
    assertEquals(200, response.getStatusCode().value());
    Assertions.assertNotNull(response.getBody());
    assertEquals(0, response.getBody().size());
  }

  @Test
  void testById() {
    var response = controller.getProductById("1");
    assertEquals(200, response.getStatusCode().value());
    assertNull(response.getBody());
  }
}
