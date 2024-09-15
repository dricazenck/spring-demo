package com.wcc.springdemo.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.springdemo.demo.domain.Product;
import com.wcc.springdemo.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product("1", "Product1", "description1");
        product2 = new Product("2", "Product2", "description2");
    }

    @Test
    void shouldReturnAllProducts() throws Exception {
        List<Product> productList = Arrays.asList(product1, product2);
        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(product1, product2))));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void shouldCreateProductSuccessfully() throws Exception {
        Product newProduct = new Product("1", "NewProduct", "description1");
        when(productService.createProduct(any(Product.class))).thenReturn(newProduct);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/product/1"))
                .andExpect(content().json(objectMapper.writeValueAsString(newProduct)));

        verify(productService, times(1)).createProduct(any(Product.class));
    }
}