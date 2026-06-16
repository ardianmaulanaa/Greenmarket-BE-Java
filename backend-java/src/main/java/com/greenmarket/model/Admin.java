package com.greenmarket.model;

public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(User user) {
        super(user.getId(), user.getUsername(), user.getEmail(),
                user.getPassword(), user.getCreatedAt(), user.getRole());
    }

    public boolean canManageUsers() {
        return true;
    }

    public boolean canManageProducts() {
        return true;
    }
}
