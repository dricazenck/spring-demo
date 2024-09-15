package com.wcc.springdemo.demo.controller;

import com.wcc.springdemo.demo.domain.User;
import com.wcc.springdemo.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "APIs for Users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    @Operation(summary = "API to retrieve all users")
    public List<User> getAllUsers() {
        return service.getAll();
    }

    @PostMapping("/user")
    @Operation(summary = "API to create user")
    public User createUser(@Valid @RequestBody User user) {
        return service.addUser(user);
    }

    @GetMapping("/user/{identifier}")
    @Operation(summary = "API to get user by identifier")
    public ResponseEntity<User> getUserByIdentifier(@PathVariable String identifier) {
        var users = service.getAll();

        return users.stream()
                .filter(user -> user.username()
                        .equalsIgnoreCase(identifier) || user.id().equalsIgnoreCase(identifier))
                .findFirst().map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}
