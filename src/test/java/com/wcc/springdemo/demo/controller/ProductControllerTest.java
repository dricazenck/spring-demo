package com.wcc.springdemo.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcc.springdemo.demo.domain.Product;
import com.wcc.springdemo.demo.security.JwtTokenUtil;
import com.wcc.springdemo.demo.service.ProductService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private ProductService productService;
  @MockBean private JwtTokenUtil jwtTokenUtil;

  private Product product1;
  private Product product2;

  @BeforeEach
  void setUp() {
    product1 = new Product("1", "Product1", "description1");
    product2 = new Product("2", "Product2", "description2");
  }

  @Test
  @WithMockUser(
      username = "user",
      roles = {"USER"})
  void shouldReturnAllProducts() throws Exception {
    List<Product> productList = Arrays.asList(product1, product2);
    when(productService.getAllProducts()).thenReturn(productList);

    mockMvc
        .perform(get("/api/v1/product").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2))
        .andExpect(content().json(objectMapper.writeValueAsString(List.of(product1, product2))));

    verify(productService, times(1)).getAllProducts();
  }

  @Test
  @Disabled("To be fixed authentication")
  void shouldCreateProductSuccessfully() throws Exception {
    Product newProduct = new Product("1", "NewProduct", "description1");
    when(productService.createProduct(any(Product.class))).thenReturn(newProduct);

    mockMvc
        .perform(
            post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/v1/product/1"))
        .andExpect(content().json(objectMapper.writeValueAsString(newProduct)));

    verify(productService, times(1)).createProduct(any(Product.class));
  }

  @Test
  void shouldCreateProductBeForbidden() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new Product("1", "NewProduct", "description1"))))
        .andExpect(status().isForbidden());

    verify(productService, never()).createProduct(any(Product.class));
  }
}
