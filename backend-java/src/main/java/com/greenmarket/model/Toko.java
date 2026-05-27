package com.greenmarket.model;

import java.sql.Timestamp;

public class Toko {
    private String id_toko;
    private int id_user;
    private String nama_toko;
    private String email_bisnis;
    private String alamat_toko;
    private Timestamp created_at;

    public Toko() {
    }

    public Toko(String id_toko, int id_user, String nama_toko, String email_bisnis, String alamat_toko, Timestamp created_at) {
        this.id_toko = id_toko;
        this.id_user = id_user;
        this.nama_toko = nama_toko;
        this.email_bisnis = email_bisnis;
        this.alamat_toko = alamat_toko;
        this.created_at = created_at;
    }

    public String getId_toko() {
        return id_toko;
    }

    public void setId_toko(String id_toko) {
        this.id_toko = id_toko;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNama_toko() {
        return nama_toko;
    }

    public void setNama_toko(String nama_toko) {
        this.nama_toko = nama_toko;
    }

    public String getEmail_bisnis() {
        return email_bisnis;
    }

    public void setEmail_bisnis(String email_bisnis) {
        this.email_bisnis = email_bisnis;
    }

    public String getAlamat_toko() {
        return alamat_toko;
    }

    public void setAlamat_toko(String alamat_toko) {
        this.alamat_toko = alamat_toko;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}