package com.greenmarket.model;

public class Buyer extends User {
    public Buyer() {
        super();
    }

    public Buyer(User user) {
        super(user.getId(), user.getUsername(), user.getEmail(),
                user.getPassword(), user.getCreatedAt(), user.getRole());
        setToko(user.getToko());
    }

    public boolean isBuyer() {
        return true;
    }
}
