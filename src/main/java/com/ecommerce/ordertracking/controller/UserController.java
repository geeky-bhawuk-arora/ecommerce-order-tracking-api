package com.ecommerce.ordertracking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ordertracking.dto.UserRegisterRequest;
import com.ecommerce.ordertracking.model.User;
import com.ecommerce.ordertracking.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public ResponseEntity<User> registerUser(@RequestBody UserRegisterRequest req) {
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {   
        return ResponseEntity.ok(userService.getAllUsers());
    
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {    
        return ResponseEntity.ok(userService.getUserById(id));
    }

}