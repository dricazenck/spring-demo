package com.wcc.springdemo.demo.controller;

import com.wcc.springdemo.demo.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final List<User> users = new ArrayList<>();

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        users.add(user);

        return user;
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return users.stream().filter(user -> user.id().equalsIgnoreCase(id))
                .findFirst().map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}
