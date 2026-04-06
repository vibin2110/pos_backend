package com.supermarket.pos.controller;

import com.supermarket.pos.entity.LoginResponse;
import com.supermarket.pos.entity.User;
import com.supermarket.pos.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import com.supermarket.pos.service.AuthService;
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {

        System.out.println("LOGIN HIT");
        System.out.println("Entered Username: " + loginRequest.getUsername());
        System.out.println("Entered Password: '" + loginRequest.getPassword() + "'");

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (user.isEmpty()) {
            System.out.println("User not found");
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        System.out.println("DB Password: '" + user.get().getPassword() + "'");
        if (!user.get().getPassword().trim()
                .equals(loginRequest.getPassword().trim())) {

            System.out.println("Password mismatch");
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        LoginResponse response = new LoginResponse();

        response.setUsername(user.get().getUsername());
        response.setRole(user.get().getRole());

        System.out.println("LOGIN SUCCESS");

        return ResponseEntity.ok(response);
    }
    @PostMapping("/forgot")
    public ResponseEntity<?> forgot(@RequestParam String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Only allow admin
        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Only admin can reset password");
        }

        String response = authService.forgotPassword(email);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/reset")
    public ResponseEntity<?> reset(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String newPassword) {

        String response = authService.resetPassword(email, otp, newPassword);
        return ResponseEntity.ok(response);
    }
}