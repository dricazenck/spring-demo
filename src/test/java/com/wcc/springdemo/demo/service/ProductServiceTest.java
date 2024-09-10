package com.wcc.springdemo.demo.service;

import com.wcc.springdemo.demo.domain.Product;
import com.wcc.springdemo.demo.exception.ProductIdDuplicatedException;
import com.wcc.springdemo.demo.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private final Product product = new Product("1", "Product1", "description1");
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
    }

    @Test
    void shouldReturnAllProductsWhenNoneExist() {
        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    void shouldCreateNewProduct() {
        Product createdProduct = productService.createProduct(product);

        assertEquals("1", createdProduct.id());
        assertEquals("Product1", createdProduct.name());
        assertEquals("description1", createdProduct.description());
        assertTrue(productService.getAllProducts().contains(createdProduct));
    }

    @Test
    void shouldThrowExceptionWhenDuplicateProductIdIsUsed() {
        productService.createProduct(product);

        var product2 = new Product("1", "Product2", "description1");

        assertThrows(ProductIdDuplicatedException.class, () -> {
            productService.createProduct(product2);
        });
    }

    @Test
    void shouldReturnProductById() {
        productService.createProduct(product);

        Product foundProduct = productService.getProductById("1");

        assertNotNull(foundProduct);
        assertEquals("Product1", foundProduct.name());
    }

    @Test
    void shouldReturnNullIfProductByIdDoesNotExist() {
        Product foundProduct = productService.getProductById("999");
        assertNull(foundProduct);
    }

    @Test
    void shouldUpdateExistingProduct() {
        productService.createProduct(product);

        Product updatedProduct = new Product("1", "UpdatedProduct", product.description());
        Product result = productService.updateProduct(updatedProduct);

        assertEquals("UpdatedProduct", result.name());
        assertEquals(product.description(), result.description());
        assertTrue(productService.getAllProducts().contains(result));
    }

    @Test
    void shouldThrowExceptionIfProductToUpdateDoesNotExist() {
        Product product = new Product("999", "NonExistingProduct", "");

        assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(product);
        });
    }

    @Test
    void shouldDeleteExistingProduct() {
        productService.createProduct(product);

        Boolean isDeleted = productService.deleteProduct("1");

        assertTrue(isDeleted);
        assertNull(productService.getProductById("1"));
    }

    @Test
    void shouldReturnFalseIfProductToDeleteDoesNotExist() {
        Boolean isDeleted = productService.deleteProduct("999");

        assertFalse(isDeleted);
    }

    @Test
    void shouldReturnProductByName() {
        productService.createProduct(product);

        Product foundProduct = productService.getProductByName("Product1");

        assertNotNull(foundProduct);
        assertEquals("Product1", foundProduct.name());
    }

    @Test
    void shouldThrowExceptionIfProductByNameDoesNotExist() {
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductByName("NonExistingProduct");
        });
    }
}