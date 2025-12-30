package com.financeapp.controller;

import com.financeapp.entity.User;
import com.financeapp.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final UserService UserService;

    public AdminController(UserService UserService) {
        this.UserService = UserService;
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        // Implémentation à faire
        return ResponseEntity.ok(List.of());
    }
    
    @PutMapping("/users/{userId}/status")
    public ResponseEntity<User> updateUserStatus(@PathVariable String userId, @RequestParam User.UserStatus status) {
        // Implémentation à faire
        return ResponseEntity.ok().build();
    }
}