package com.wcc.springdemo.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.wcc.springdemo.demo.domain.Product;
import com.wcc.springdemo.demo.exception.ProductIdDuplicatedException;
import com.wcc.springdemo.demo.exception.ProductNotFoundException;
import com.wcc.springdemo.demo.repository.ProductRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Component test for ProductService without using Spring container. This test demonstrates pure
 * class-to-class testing by manually creating the service and mocking its dependencies.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceComponentTest {

  private static final String PRODUCT_ID = "1";
  private static final String PRODUCT_NAME = "Test Product";
  private static final String PRODUCT_DESCRIPTION = "Test Description";

  @Mock private ProductRepository productRepository;

  private ProductService productService;
  private Product testProduct;

  @BeforeEach
  void setUp() {
    productService = new ProductService(productRepository);
    testProduct = new Product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION);
  }

  @Test
  void shouldCreateProductSuccessfully() {
    when(productRepository.existsById(PRODUCT_ID)).thenReturn(false);
    when(productRepository.save(testProduct)).thenReturn(testProduct);

    Product createdProduct = productService.createProduct(testProduct);

    assertEquals(testProduct, createdProduct);
    verify(productRepository).existsById(PRODUCT_ID);
    verify(productRepository).save(testProduct);
  }

  @Test
  void shouldThrowExceptionWhenCreatingProductWithDuplicateId() {
    when(productRepository.existsById(PRODUCT_ID)).thenReturn(true);

    assertThrows(
        ProductIdDuplicatedException.class, () -> productService.createProduct(testProduct));
    verify(productRepository).existsById(PRODUCT_ID);
    verify(productRepository, never()).save(any(Product.class));
  }

  @Test
  void shouldGetAllProducts() {
    List<Product> expectedProducts =
        Arrays.asList(testProduct, new Product("2", "Another Product", "Another Description"));
    when(productRepository.findAll()).thenReturn(expectedProducts);

    List<Product> actualProducts = productService.getAllProducts();

    assertEquals(expectedProducts, actualProducts);
    verify(productRepository).findAll();
  }

  @Test
  void shouldDeleteProductSuccessfully() {
    when(productRepository.existsById(PRODUCT_ID)).thenReturn(true);
    doNothing().when(productRepository).deleteById(PRODUCT_ID);

    Boolean result = productService.deleteProduct(PRODUCT_ID);

    assertTrue(result);
    verify(productRepository).existsById(PRODUCT_ID);
    verify(productRepository).deleteById(PRODUCT_ID);
  }

  @Test
  void shouldReturnFalseWhenDeletingNonExistentProduct() {
    when(productRepository.existsById(PRODUCT_ID)).thenReturn(false);

    Boolean result = productService.deleteProduct(PRODUCT_ID);

    assertFalse(result);
    verify(productRepository).existsById(PRODUCT_ID);
    verify(productRepository, never()).deleteById(anyString());
  }

  @Test
  void shouldUpdateProductSuccessfully() {
    when(productRepository.existsById(PRODUCT_ID)).thenReturn(true);
    when(productRepository.save(testProduct)).thenReturn(testProduct);

    Product updatedProduct = productService.updateProduct(testProduct);

    // Assert
    assertEquals(testProduct, updatedProduct);
    verify(productRepository).existsById(PRODUCT_ID);
    verify(productRepository).save(testProduct);
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
    when(productRepository.existsById(PRODUCT_ID)).thenReturn(false);

    assertThrows(
        ProductNotFoundException.class,
        () -> {
          productService.updateProduct(testProduct);
        });
    verify(productRepository).existsById(PRODUCT_ID);
    verify(productRepository, never()).save(any(Product.class));
  }

  @Test
  void shouldGetProductByIdSuccessfully() {
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(testProduct));

    Product foundProduct = productService.getProductById(PRODUCT_ID);

    assertEquals(testProduct, foundProduct);
    verify(productRepository).findById(PRODUCT_ID);
  }

  @Test
  void shouldReturnNullWhenProductNotFoundById() {
    // Arrange
    when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

    // Act
    Product foundProduct = productService.getProductById(PRODUCT_ID);

    // Assert
    assertNull(foundProduct);
    verify(productRepository).findById(PRODUCT_ID);
  }

  @Test
  void shouldGetProductByNameSuccessfully() {
    // Arrange
    when(productRepository.findByNameIgnoreCase(PRODUCT_NAME)).thenReturn(Optional.of(testProduct));

    // Act
    Product foundProduct = productService.getProductByName(PRODUCT_NAME);

    // Assert
    assertEquals(testProduct, foundProduct);
    verify(productRepository).findByNameIgnoreCase(PRODUCT_NAME);
  }

  @Test
  void shouldThrowExceptionWhenProductNotFoundByName() {
    // Arrange
    when(productRepository.findByNameIgnoreCase(PRODUCT_NAME)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        ProductNotFoundException.class,
        () -> {
          productService.getProductByName(PRODUCT_NAME);
        });
    verify(productRepository).findByNameIgnoreCase(PRODUCT_NAME);
  }
}
