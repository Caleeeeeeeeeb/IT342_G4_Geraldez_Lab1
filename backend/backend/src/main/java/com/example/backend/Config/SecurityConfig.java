package com.example.backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
            .authorizeHttpRequests(auth -> auth
                // Allow anyone to access these specific URLs
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                
                // (Optional) Allow H2 Console if you use it
                .requestMatchers("/h2-console/**").permitAll()
                
                // Allow the Dashboard endpoint because YOU are manually checking the token in the Controller
                // Note: In a more advanced setup, we would block this here and use a Filter.
                // But for your current code, this is the easiest way to make it work.
                .requestMatchers("/api/auth/dashboard", "/api/auth/profile").permitAll()
                
                // Block anything else
                .anyRequest().authenticated()
            );
            
        return http.build();
    }
}