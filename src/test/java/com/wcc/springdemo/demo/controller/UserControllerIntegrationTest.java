package com.wcc.springdemo.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private UserController controller;

    @Test
    void testAllUsers() {
        var users = controller.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserByIdentifier() {
        var response = controller.getUserByIdentifier("1");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Adriana", response.getBody().firstName());
    }

}
