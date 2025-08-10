package com.ecommerce.ordertracking.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.ordertracking.dto.UserRegisterRequest;
import com.ecommerce.ordertracking.model.User;
import com.ecommerce.ordertracking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    public User registerUser(UserRegisterRequest req) {
        if(userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("User already exists!");
        }
        if(userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        User user = User.builder()
            .username((req.getUsername()))
            .email(req.getEmail())
            .password(passwordEncoder.encode(req.getPassword())) // Encode the password
            .build();

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}