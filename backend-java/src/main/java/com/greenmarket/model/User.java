/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.greenmarket.model;

/**
 *
 * @author mac
 */
import java.util.List;
import java.util.Date;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Date createdAt;
    private Role role; // Menggunakan Enum Role
    private List<Produk> products;
    private List<Wishlist> wishlists;

    // Constructor, Getter, Setter
}