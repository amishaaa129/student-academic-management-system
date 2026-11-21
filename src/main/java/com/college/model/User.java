package com.college.model;

public class User {

    private int userId;
    private String username;
    private String passwordHash;
    private Role role;
    private String email;

    public User(int userId, String username,
                String passwordHash, Role role,
                String email) {

        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User ID: " + userId +
               "\nUsername: " + username +
               "\nRole: " + role +
               "\nEmail: " + email;
    }
}