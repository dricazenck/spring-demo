package com.wcc.springdemo.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Product(
        @JsonProperty("productId") String id,
        String name,
        String description) {
}
