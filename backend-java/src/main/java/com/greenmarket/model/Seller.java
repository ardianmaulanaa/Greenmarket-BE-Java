package com.greenmarket.model;

public class Seller extends User {
    public Seller() {
        super();
    }

    public Seller(User user) {
        super(user.getId(), user.getUsername(), user.getEmail(),
                user.getPassword(), user.getCreatedAt(), user.getRole());
        setToko(user.getToko());
    }

    public String getTokoName() {
        return getToko() != null ? getToko().getNama_toko() : null;
    }
}
