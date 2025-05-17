package com.wcc.springdemo.demo.repository;

import com.wcc.springdemo.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    /**
     * Find a product by its name (case-insensitive)
     * 
     * @param name the product name
     * @return an Optional containing the product if found, or empty if not found
     */
    Optional<Product> findByNameIgnoreCase(String name);
}