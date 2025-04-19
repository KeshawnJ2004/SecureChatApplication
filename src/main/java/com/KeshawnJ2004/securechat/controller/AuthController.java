package com.KeshawnJ2004.securechat.controller;

import com.KeshawnJ2004.securechat.model.User;
import com.KeshawnJ2004.securechat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Mark this class as a REST controller
@RestController
@RequestMapping("/auth")
public class AuthController {

    // Spring auto-wires the repository
    @Autowired //Inject the encoder
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Example registration endpoint
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("User already exists!");
        }

        //Encode password
        String hashed = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashed);

        //Save the user
        userRepository.save(user);

        //Return 200 response
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        //Look up the user by email
        User existing = userRepository.findByEmail(loginRequest.getEmail());
        if (existing == null) {
            //Then email doesn't exist
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        //Compare the raw password to the stored hash
        boolean matches = passwordEncoder.matches(loginRequest.getPassword(), existing.getPassword());
        if (!matches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        //Success
        return ResponseEntity.ok("Login successful");
    }

}