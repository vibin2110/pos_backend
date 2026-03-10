package com.supermarket.pos.controller;

import com.supermarket.pos.entity.LoginResponse;
import com.supermarket.pos.entity.User;
import com.supermarket.pos.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody User loginRequest) {

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        LoginResponse response = new LoginResponse();

        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {

            response.setUsername(user.get().getUsername());

            int roleId = user.get().getRole_id();

            if (roleId == 1) {
                response.setRole("ADMIN");
            } else if (roleId == 2) {
                response.setRole("CASHIER");
            } else if (roleId == 3) {
                response.setRole("MANAGER");
            }
        }

        return response;
    }
}