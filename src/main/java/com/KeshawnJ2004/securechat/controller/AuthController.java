package com.KeshawnJ2004.securechat.controller;

import com.KeshawnJ2004.securechat.model.User;
import com.KeshawnJ2004.securechat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Mark this class as a REST controller
@RestController
@RequestMapping("/auth")
public class AuthController {

    // Spring auto-wires the repository
    @Autowired
    private UserRepository userRepository;

    // Example registration endpoint
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Implementation goes here...
        return ResponseEntity.ok("User registered!");
    }
}