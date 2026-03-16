package com.example.frauddetection.service;

import com.example.frauddetection.model.User;
import com.example.frauddetection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // SIGNUP
    public boolean signup(User user) {

        // check if email already exists
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            return false; // email already registered
        }

        int rows = userRepository.save(user);

        return rows > 0;
    }

    // LOGIN
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null; // user not found
        }

        if (!user.getPassword().equals(password)) {
            return null; // wrong password
        }

        return user; // login success
    }
}