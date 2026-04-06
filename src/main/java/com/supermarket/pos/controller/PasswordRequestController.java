package com.supermarket.pos.controller;

import com.supermarket.pos.entity.PasswordRequest;
import com.supermarket.pos.service.PasswordRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/password")
@CrossOrigin(origins = "*")
public class PasswordRequestController {

    @Autowired
    private PasswordRequestService service;

    // USER
    @PostMapping("/forgot/{username}")
    public PasswordRequest forgotPassword(@PathVariable String username) {
        return service.createRequest(username);
    }

    // ADMIN
    @GetMapping("/admin/requests")
    public List<PasswordRequest> getAllRequests() {
        return service.getAllRequests();
    }

    @PostMapping("/admin/accept/{id}")
    public PasswordRequest acceptRequest(@PathVariable Long id) {
        return service.acceptRequest(id);
    }

    @PostMapping("/admin/complete/{id}")
    public PasswordRequest completeRequest(
            @PathVariable Long id,
            @RequestParam String newPassword) {
        return service.completeRequest(id, newPassword);
    }

    // USER
    @GetMapping("/user/{username}")
    public List<PasswordRequest> getUserNotifications(@PathVariable String username) {
        return service.getUserNotifications(username);
    }
    @PostMapping("/admin/reject/{id}")
    public PasswordRequest rejectRequest(@PathVariable Long id) {
        return service.rejectRequest(id);
    }

}