package com.example.backend.controller;

import com.example.backend.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Temporary in-memory storage (no DB yet)
    private final List<User> users = new ArrayList<>();

    // Registration endpoint
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) return "Username already exists!";
            if (u.getEmail().equals(user.getEmail())) return "Email already exists!";
        }
        user.setRole("USER"); // default role
        user.setCreatedAt(java.time.LocalDateTime.now());
        users.add(user);
        return "User registered successfully!";
    }

    // Login endpoint
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                if (!u.getPassword().equals(user.getPassword())) return "Wrong password!";
                return "Login successful!";
            }
        }
        return "User not found!";
    }
}
