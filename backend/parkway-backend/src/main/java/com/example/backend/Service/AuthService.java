package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;

import java.time.LocalDateTime;

public class AuthService {

    private final UserRepository userRepository = new UserRepository();

    public String registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) return "Username already exists!";
        if (userRepository.findByEmail(user.getEmail()) != null) return "Email already exists!";
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String authenticateUser(User user) {
        User existing = userRepository.findByUsername(user.getUsername());
        if (existing == null) return "User not found!";
        if (!existing.getPassword().equals(user.getPassword())) return "Wrong password!";
        return "Login successful!";
    }

    public User getProfile(String username) {
        return userRepository.findByUsername(username);
    }

    public void logout(String username) {
        // For now, do nothing (placeholder for token invalidation)
    }
}
