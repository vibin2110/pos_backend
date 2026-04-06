package com.supermarket.pos.service;

import com.supermarket.pos.entity.PasswordRequest;
import com.supermarket.pos.entity.User;
import com.supermarket.pos.repository.PasswordRequestRepository;
import com.supermarket.pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordRequestService {

    @Autowired
    private PasswordRequestRepository repository;

    @Autowired
    private UserRepository userRepository;

    public PasswordRequest createRequest(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PasswordRequest request = new PasswordRequest();
        request.setUsername(username);

        return repository.save(request);
    }

    // ADMIN → view all
    public List<PasswordRequest> getAllRequests() {
        return repository.findAll();
    }

    // ADMIN → ACCEPT
    public PasswordRequest acceptRequest(Long id) {
        PasswordRequest request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus("ACCEPTED");
        return repository.save(request);
    }

    // ADMIN → COMPLETE (change password)
    public PasswordRequest completeRequest(Long id, String newPassword) {

        PasswordRequest request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getStatus().equals("ACCEPTED")) {
            throw new RuntimeException("Request not accepted yet");
        }

        request.setStatus("COMPLETED");
        request.setNewPassword(newPassword);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(newPassword);
        userRepository.save(user);

        return repository.save(request);
    }

    // USER → notification
    public List<PasswordRequest> getUserNotifications(String username) {
        return repository.findByUsername(username);
    }
    public PasswordRequest rejectRequest(Long id) {

        PasswordRequest req = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        req.setStatus("REJECTED");

        return repository.save(req);
    }
}