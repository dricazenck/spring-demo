package com.wcc.springdemo.demo.controller;

import com.wcc.springdemo.demo.domain.Product;
import com.wcc.springdemo.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Tag(name = "APIs for Products")
@RequestMapping("/api/v1/product")
public class ProductController {

  private final ProductService service;

  @Autowired
  public ProductController(ProductService service) {
    this.service = service;
  }

  @GetMapping
  @Operation(summary = "API to retrieve all products")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<Product>> getAllProducts() {
    return ResponseEntity.ok(service.getAllProducts());
  }

  @GetMapping("/{id}")
  @Operation(summary = "API to retrieve information about product by id")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Product> getProductById(@PathVariable String id) {
    return ResponseEntity.ok(service.getProductById(id));
  }

  @PostMapping
  @Operation(summary = "API to create product")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    var productSaved = service.createProduct(product);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(productSaved.id())
            .toUri();

    return ResponseEntity.created(location).body(productSaved);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Product> updateProduct(
      @Validated @PathVariable String id, @RequestBody Product product) {
    return ResponseEntity.ok(
        service.updateProduct(new Product(id, product.name(), product.description())));
  }

  @PatchMapping("/{id}")
  @Operation(summary = "API to update not all attributes")
  @ApiResponse(responseCode = "200", description = "Product updated")
  @ApiResponse(responseCode = "404", description = "Product not found")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Product> patchProduct(
      @PathVariable String id, @RequestBody Product product) {
    return ResponseEntity.ok(
        service.updateProduct(new Product(id, product.name(), product.description())));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "API to delete product")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
    boolean isDeleted = service.deleteProduct(id);

    if (isDeleted) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/by/{name}")
  @Operation(summary = "API to retrieve information about product by name")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Product> getUserByUserName(@PathVariable String name) {
    return ResponseEntity.ok(service.getProductByName(name));
  }
}
