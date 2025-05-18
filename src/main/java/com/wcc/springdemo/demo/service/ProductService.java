package com.wcc.springdemo.demo.service;

import com.wcc.springdemo.demo.domain.Product;
import com.wcc.springdemo.demo.exception.ProductIdDuplicatedException;
import com.wcc.springdemo.demo.exception.ProductNotFoundException;
import com.wcc.springdemo.demo.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Product createProduct(Product product) {
    if (productRepository.existsById(product.id())) {
      throw new ProductIdDuplicatedException(product.id());
    }

    return productRepository.save(product);
  }

  public Boolean deleteProduct(String id) {
    if (productRepository.existsById(id)) {
      productRepository.deleteById(id);
      return true;
    }

    return false;
  }

  public Product updateProduct(Product product) {
    if (!productRepository.existsById(product.id())) {
      throw new ProductNotFoundException(product.id());
    }

    return productRepository.save(product);
  }

  public Product getProductById(String id) {
    return productRepository.findById(id).orElse(null);
  }

  public Product getProductByName(String name) {
    return productRepository
        .findByNameIgnoreCase(name)
        .orElseThrow(() -> new ProductNotFoundException(name));
  }
}
