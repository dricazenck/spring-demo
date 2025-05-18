package com.wcc.springdemo.demo.controller;

import com.wcc.springdemo.demo.domain.User;
import com.wcc.springdemo.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
  public User createUser(@Validated @RequestBody User user) {
    return service.addUser(user);
  }

  @GetMapping("/user/{username}")
  @Operation(summary = "API to get user by username")
  public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
    var user = service.getUserByUsername(username);

    if (user != null) {
      return ResponseEntity.ok(user);
    }

    return ResponseEntity.notFound().build();
  }
}
