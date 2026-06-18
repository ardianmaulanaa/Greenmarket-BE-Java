package com.greenmarket.model;

import java.sql.Timestamp;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Timestamp createdAt;
    private String role;
    private Toko toko;

    public User() {
    }

    public User(int id, String username, String email, String password, Timestamp createdAt, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Toko getToko() {
        return toko;
    }

    public void setToko(Toko toko) {
        this.toko = toko;
    }

    // Default: user biasa tidak punya hak kelola
    public boolean canManageUsers() {
        return false;
    }

    public boolean canManageProducts() {
        return false;
    }

    public static User fromUser(User user) {
        if (user == null) return null;
        String role = user.getRole();
        if (role == null) return user;
        switch (role.toUpperCase()) {
            case "BUYER": return new Buyer(user);
            case "SELLER": return new Seller(user);
            case "ADMIN": return new Admin(user);
            default: return user;
        }
    }
}