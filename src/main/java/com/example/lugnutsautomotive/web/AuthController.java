package com.example.lugnutsautomotive.controller;

import com.example.lugnutsautomotive.domain.Admin;
import com.example.lugnutsautomotive.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String user_name, @RequestParam String password) {
        Optional<Admin> admin = adminRepository.findByuser_name(user_name);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Implement logout logic if needed (e.g., invalidate session)
        return ResponseEntity.ok("Logout successful");
    }
}