package com.wcc.springdemo.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record User(
        @JsonProperty("userId")
        String id,
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Lastname is required")
        String lastName,
        @JsonIgnore String fullName,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email) {
}
