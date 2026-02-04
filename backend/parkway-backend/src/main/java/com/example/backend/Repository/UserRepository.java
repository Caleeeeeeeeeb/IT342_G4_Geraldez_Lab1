package com.example.backend.repository;

import com.example.backend.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final List<User> users = new ArrayList<>();
    private Long currentId = 1L;

    public User save(User user) {
        user.setId(currentId++);
        users.add(user);
        return user;
    }

    public User findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    public User findByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    public List<User> getAllUsers() {
        return users;
    }
}
