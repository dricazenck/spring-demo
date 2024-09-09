package com.wcc.springdemo.demo.controller;

import com.wcc.springdemo.demo.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users;
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        users.add(user);

        return user;
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable String id) {
        return users.stream().filter(user -> user.username().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String username) {
        return users.stream().filter(user -> user.username().equalsIgnoreCase(username))
                .findFirst().map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}
