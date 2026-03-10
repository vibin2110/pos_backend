package com.supermarket.pos.service;

import com.supermarket.pos.entity.User;
import com.supermarket.pos.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User login(String username, String password) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            if (user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }
}