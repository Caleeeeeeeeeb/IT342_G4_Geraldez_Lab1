package com.example.backend.Service;

import com.example.backend.Model.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.UtilityToken.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    // Add the Encoder
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User register(User user) {
        // ENCRYPT the password before saving!
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<String> login(String username, String password) {
        // --- SPY MODE: START ---
        System.out.println("=== LOGIN DEBUG ===");
        System.out.println("1. Phone sent Username: '" + username + "'");
        System.out.println("2. Phone sent Password: '" + password + "'");

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            System.out.println("3. Database found User: " + user.get().getUsername());
            System.out.println("4. Database has Password: " + user.get().getPassword());

            boolean isMatch = passwordEncoder.matches(password, user.get().getPassword());
            System.out.println("5. Do they match? " + isMatch);

            if (isMatch) {
                System.out.println("=== LOGIN SUCCESS ===");
                return Optional.of(jwtUtil.generateToken(username));
            }
        } else {
            System.out.println("3. ERROR: User NOT found in database!");
        }
        System.out.println("=== LOGIN FAILED ===");
        // --- SPY MODE: END ---

        return Optional.empty();
    }

    public Optional<User> getProfile(String token) {
        // (Your existing code here is fine)
        if (!jwtUtil.validateToken(token)) return Optional.empty();
        String username = jwtUtil.extractUsername(token);
        return userRepository.findByUsername(username);
    }
}