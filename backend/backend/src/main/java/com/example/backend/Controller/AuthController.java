package com.example.backend.Controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Model.User;
import com.example.backend.Service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:5174"})
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        Optional<String> token = authService.login(body.get("username"), body.get("password"));
        if (token.isPresent()) {
            return ResponseEntity.ok(Map.of("token", token.get()));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Optional<User> user = authService.getProfile(token);
        if (user.isEmpty()) return ResponseEntity.status(401).body("Unauthorized");
        return ResponseEntity.ok("Welcome to Dashboard, " + user.get().getUsername());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Optional<User> user = authService.getProfile(token);
        if (user.isEmpty()) return ResponseEntity.status(401).body("Unauthorized");
        return ResponseEntity.ok(user.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logged out");
    }
}
