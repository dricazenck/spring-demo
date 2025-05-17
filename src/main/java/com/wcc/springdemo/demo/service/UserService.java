package com.wcc.springdemo.demo.service;

import com.wcc.springdemo.demo.domain.User;
import com.wcc.springdemo.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.annotation.PostConstruct;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        // Only add default users if the repository is empty
        if (userRepository.count() == 0) {
            userRepository.save(new User("1", "adriana", "Adriana", "Zencke", "Adriana Zencke", "adriana@email.com"));
            userRepository.save(new User("2", "sonali", "Sonali", "Goel", "Sonali Goel", "sonali@email.com"));
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
}
