package com.wcc.springdemo.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record User(
        @JsonProperty("userId") String id,
        String username,
        String firstName,
        String lastName,
        @JsonIgnore String fullName) {
}
