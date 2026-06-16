package com.greenmarket.model;

import java.sql.Timestamp;

public class Keranjang {
    private String id_keranjang;
    private long id_user;
    private String id_produk;
    private int kuantitas;
    private Timestamp created_at;
    private Produk produk;

    public Keranjang() {
    }

    public Keranjang(String id_keranjang, long id_user, String id_produk, int kuantitas, Timestamp created_at) {
        this.id_keranjang = id_keranjang;
        this.id_user = id_user;
        this.id_produk = id_produk;
        this.kuantitas = kuantitas;
        this.created_at = created_at;
    }

    public String getId_keranjang() {
        return id_keranjang;
    }

    public void setId_keranjang(String id_keranjang) {
        this.id_keranjang = id_keranjang;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }
}