package com.supermarket.pos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String status; // PENDING, ACCEPTED, COMPLETED
    private String newPassword;
    private LocalDateTime requestTime;

    public PasswordRequest() {
        this.requestTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Long getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public LocalDateTime getRequestTime() { return requestTime; }
}