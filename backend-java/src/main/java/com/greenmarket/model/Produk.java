package com.greenmarket.model;

import java.util.List;

public class Produk {
    private String id_produk;
    private int id_user_seller;
    private String id_kategori;
    private String nama_produk;
    private String deskripsi;
    private int harga;
    private int stok;
    
    // Relasi - yang dibutuhkan Next.js
    private List<ProdukFoto> fotos;
    private KategoriProduk kategori;
    private User seller;

    public Produk() {}

    public String getId_produk() { return id_produk; }
    public void setId_produk(String id_produk) { this.id_produk = id_produk; }
    public int getId_user_seller() { return id_user_seller; }
    public void setId_user_seller(int id_user_seller) { this.id_user_seller = id_user_seller; }
    public String getId_kategori() { return id_kategori; }
    public void setId_kategori(String id_kategori) { this.id_kategori = id_kategori; }
    public String getNama_produk() { return nama_produk; }
    public void setNama_produk(String nama_produk) { this.nama_produk = nama_produk; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public int getHarga() { return harga; }
    public void setHarga(int harga) { this.harga = harga; }
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
    public List<ProdukFoto> getFotos() { return fotos; }
    public void setFotos(List<ProdukFoto> fotos) { this.fotos = fotos; }
    public KategoriProduk getKategori() { return kategori; }
    public void setKategori(KategoriProduk kategori) { this.kategori = kategori; }
    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }
}