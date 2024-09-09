package com.wcc.springdemo.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record User(
        @JsonProperty("userId") String id,
        String username,
        String firstName,
        String lastName,
        @JsonIgnore String fullName) {
}
