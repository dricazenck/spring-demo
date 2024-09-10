package com.wcc.springdemo.demo.service;

import com.wcc.springdemo.demo.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("1", "adriana", "Adriana", "Zencke", "Adriana Zencke", "adriana@email.com"));
        users.add(new User("2", "sonali", "Sonali", "Goel", "Sonali Goel", "sonali@email.com"));
    }

    public List<User> getAll() {
        return users;
    }

    public User addUser(User user) {
        users.add(user);

        return user;
    }

}
