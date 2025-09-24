package com.hotel.reservationsystem.controller;

import com.hotel.reservationsystem.dto.LoginRequestDto;
import com.hotel.reservationsystem.model.User;
import com.hotel.reservationsystem.service.AuthService;
import com.hotel.reservationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService; // Inject the new AuthService

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            registeredUser.setPassword(null);
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // UPDATED LOGIN METHOD
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequest) {
//    	System.out.println("credentials "+loginRequest);
        try {
            User authenticatedUser = authService.authenticateUser(loginRequest);
            // Don't send the password back
            authenticatedUser.setPassword(null);
            return ResponseEntity.ok(authenticatedUser);
        } catch (RuntimeException e) {
            // Return 401 Unauthorized for bad credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}