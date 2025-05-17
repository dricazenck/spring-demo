package com.wcc.springdemo.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.wcc.springdemo.demo.domain.User;
import com.wcc.springdemo.demo.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

  @Autowired private UserService userService;

  @Autowired private UserRepository userRepository;

  private User testUser;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
    testUser = new User("test-id", "testuser", "Test", "User", "Test User", "test@example.com");
  }

  @Test
  void shouldInitializeWithDefaultUsers() {
    // Clear the repository and manually call init to ensure default users are added
    userRepository.deleteAll();
    userService.init();

    // The init method should have added the default users
    List<User> users = userService.getAll();

    // Should have the two default users
    assertEquals(2, users.size());
    assertTrue(users.stream().anyMatch(u -> u.username().equals("adriana")));
    assertTrue(users.stream().anyMatch(u -> u.username().equals("sonali")));
  }

  @Test
  void shouldAddUser() {
    User addedUser = userService.addUser(testUser);

    assertNotNull(addedUser);
    assertEquals("test-id", addedUser.id());
    assertEquals("testuser", addedUser.username());

    // Verify it's in the repository
    assertTrue(userRepository.findById("test-id").isPresent());
  }

  @Test
  void shouldGetAllUsers() {
    // Clear the repository and add our test user
    userRepository.deleteAll();
    userService.addUser(testUser);

    // Add the default users
    userService.init();

    List<User> users = userService.getAll();

    // Should have at least our test user
    assertFalse(users.isEmpty());
    assertTrue(users.stream().anyMatch(u -> u.id().equals("test-id")));

    // Verify the count is at least 1 (our test user)
    assertTrue(!users.isEmpty());
  }

  @Test
  void shouldGetUserById() {
    userService.addUser(testUser);

    User foundUser = userService.getUserById("test-id");

    assertNotNull(foundUser);
    assertEquals("testuser", foundUser.username());
  }

  @Test
  void shouldReturnNullForNonExistentUserId() {
    User foundUser = userService.getUserById("non-existent-id");

    assertNull(foundUser);
  }

  @Test
  void shouldGetUserByUsername() {
    userService.addUser(testUser);

    User foundUser = userService.getUserByUsername("testuser");

    assertNotNull(foundUser);
    assertEquals("test-id", foundUser.id());
  }

  @Test
  void shouldReturnNullForNonExistentUsername() {
    User foundUser = userService.getUserByUsername("non-existent-username");

    assertNull(foundUser);
  }
}
