package com.wcc.springdemo.demo.exception;

public class ProductIdDuplicatedException extends RuntimeException {
    public ProductIdDuplicatedException(String message) {
        super(message);
    }
}
