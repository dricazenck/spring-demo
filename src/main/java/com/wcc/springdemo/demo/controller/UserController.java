package com.wcc.springdemo.demo.controller;

import com.wcc.springdemo.demo.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final List<User> users = new ArrayList<>();

    public UserController() {
        users.add(new User("1", "adriana", "Adriana", "Zencke", "Adriana Zencke", "adriana@email.com"));
        users.add(new User("2", "sonali", "Sonali", "Goel", "Sonali Goel", "sonali@email.com"));
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users;
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        users.add(user);

        return user;
    }

    @GetMapping("/user/{identifier}")
    public ResponseEntity<User> getUserByIdentifier(@PathVariable String identifier) {
        return users.stream()
                .filter(user -> user.username()
                        .equalsIgnoreCase(identifier) || user.id().equalsIgnoreCase(identifier))
                .findFirst().map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}
