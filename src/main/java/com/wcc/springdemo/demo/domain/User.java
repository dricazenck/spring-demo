package com.wcc.springdemo.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record User(
        @JsonProperty("userId")
        String id,
        @NotNull
        String username,
        String firstName,
        String lastName,
        @JsonIgnore String fullName,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email) {
}
