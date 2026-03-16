package com.example.frauddetection.controller;

import com.example.frauddetection.model.User;
import com.example.frauddetection.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    // SIGNUP API
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {

        boolean success = authService.signup(user);

        if (success) {
            return "User registered successfully";
        } else {
            return "Email already exists";
        }
    }

    // LOGIN API
    @PostMapping("/login")
    public User login(@RequestBody User user) {

        User loggedUser = authService.login(user.getEmail(), user.getPassword());

        if (loggedUser == null) {
            return null;
        }

        return loggedUser;
    }
}