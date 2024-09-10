package com.wcc.springdemo.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @Autowired
    private ProductController controller;

    @Test
    void testAll() {
        var response = controller.getAllProducts();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void testById() {
        var response = controller.getProductById("1");
        assertEquals(200, response.getStatusCode().value());
        assertNull(response.getBody());
    }

}
