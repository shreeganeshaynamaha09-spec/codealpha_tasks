package com.hotel.reservationsystem.service;

import com.hotel.reservationsystem.dto.LoginRequestDto;
import com.hotel.reservationsystem.model.User;
import com.hotel.reservationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticateUser(LoginRequestDto loginRequest) {
        // 1. Find the user by their email address
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found or credentials invalid."));

        // 2. Check if the provided password matches the stored hashed password
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return user; // Passwords match, authentication is successful
        } else {
            // Passwords do not match
            throw new RuntimeException("User not found or credentials invalid.");
        }
    }
}