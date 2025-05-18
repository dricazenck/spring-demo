package com.wcc.springdemo.demo.service;

import com.wcc.springdemo.demo.domain.User;
import com.wcc.springdemo.demo.domain.User.Role;
import com.wcc.springdemo.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostConstruct
  public void init() {
    // Only for testing
    if (userRepository.count() == 0) {
      User admin =
          new User(
              "1",
              "adriana",
              "Adriana",
              "Zencke",
              "Adriana Zencke",
              "adriana@email.com",
              passwordEncoder.encode("admin"));
      admin.addRole(Role.ADMIN);
      userRepository.save(admin);

      User user =
          new User(
              "2",
              "maryjane",
              "Mary",
              "Jane",
              "Mary Jane",
              "maryjane@email.com",
              passwordEncoder.encode("password"));
      userRepository.save(user);
    }
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User addUser(User user) {
    return userRepository.save(user);
  }

  public User getUserById(String id) {
    return userRepository.findById(id).orElse(null);
  }

  public User getUserByUsername(String username) {
    return userRepository.findByUsernameIgnoreCase(username);
  }

  /**
   * Register a new user with encoded password
   *
   * @param user the user to register
   * @return the registered user
   * @throws IllegalArgumentException if the username is already taken
   */
  public User registerUser(User user) {
    if (userRepository.existsByUsernameIgnoreCase(user.getUsername())) {
      throw new IllegalArgumentException("Username already taken: " + user.getUsername());
    }

    // Encode password
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Ensure user has at least USER
    if (user.getRoles() == null || user.getRoles().isEmpty()) {
      user.addRole(Role.USER);
    }

    return userRepository.save(user);
  }

  /**
   * Check if the provided credentials are valid
   *
   * @param username the username
   * @param password the raw password
   * @return the authenticated user if credentials are valid, null otherwise
   */
  public User authenticate(String username, String password) {
    User user = userRepository.findByUsernameIgnoreCase(username);
    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
      return user;
    }
    return null;
  }
}
