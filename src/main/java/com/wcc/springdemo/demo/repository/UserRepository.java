package com.wcc.springdemo.demo.repository;

import com.wcc.springdemo.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    /**
     * Find a user by username (case-insensitive)
     * 
     * @param username the username
     * @return the user if found, or null if not found
     */
    User findByUsernameIgnoreCase(String username);
}