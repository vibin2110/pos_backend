package com.supermarket.pos.entity;

public class LoginResponse {

    private String username;
    private String role;

    public LoginResponse() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}