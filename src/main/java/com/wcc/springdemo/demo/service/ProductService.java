package com.wcc.springdemo.demo.service;

import com.wcc.springdemo.demo.domain.Product;
import com.wcc.springdemo.demo.exception.ProductIdDuplicatedException;
import com.wcc.springdemo.demo.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public List<Product> getAllProducts() {
        return products;
    }

    public Product createProduct(Product product) {

        if (getProductById(product.id()) != null) {
            throw new ProductIdDuplicatedException(product.id());
        }

        products.add(product);

        return product;
    }

    public Boolean deleteProduct(String id) {
        Product product = getProductById(id);
        if (product != null) {
            return products.remove(product);
        }

        return false;
    }

    public Product updateProduct(Product product) {
        if (getProductById(product.id()) == null) {
            throw new ProductNotFoundException(product.id());
        }

        products.add(product);

        return product;
    }

    public Product getProductById(@PathVariable String id) {
        return products.stream().filter(user -> user.id().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    @GetMapping("/{name}")
    public Product getProductByName(@PathVariable String name) {
        return products.stream().filter(user -> user.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(name));

    }
}
