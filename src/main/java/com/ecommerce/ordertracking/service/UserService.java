package com.ecommerce.ordertracking.service;

import org.springframework.stereotype.Service;

import com.ecommerce.ordertracking.dto.UserRegisterRequest;
import com.ecommerce.ordertracking.model.User;
import com.ecommerce.ordertracking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
            .password(req.getPassword())
            .build();
            
        return userRepository.save(user);
    }    
}