package com.project_management_system.model;

public class User {
    private int userId;
    private String fullName;
    private String cpf;
    private String email;
    private String role;
    private String username;
    private String password;

    public User(int userId, String fullName, String cpf, String email, String role, String username, String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
               "userId=" + userId +
               ", fullName=\'" + fullName + '\'' +
               ", cpf=\'" + cpf + '\'' +
               ", email=\'" + email + '\'' +
               ", role=\'" + role + '\'' +
               ", username=\'" + username + '\'' +
               "}";
    }
}


