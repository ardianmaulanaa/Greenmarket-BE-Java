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

public class Produk {
    private String id_produk; // UUID
    private int id_user_seller;
    private String id_kategori;
    private String nama_produk;
    private String deskripsi;
    private int harga;
    private int stok;
    private String status_produk;
    private Date created_at;
    
    // Relasi Objects
    private KategoriProduk kategori;
    private User seller;
    private ProdukDetail detail;
    private List<ProdukFoto> fotos;
    private List<Wishlist> wishlists;

    // Constructor, Getter, Setter
}