package com.greenmarket.model;

public class Alamat {
    private String id_alamat; 
    private int id_user;
    private String nama_penerima;
    private String nomor_hp;
    private String alamat_lengkap;

    public Alamat() {
    }

    public Alamat(String id_alamat, int id_user, String nama_penerima, String nomor_hp, String alamat_lengkap) {
        this.id_alamat = id_alamat;
        this.id_user = id_user;
        this.nama_penerima = nama_penerima;
        this.nomor_hp = nomor_hp;
        this.alamat_lengkap = alamat_lengkap;
    }

    public String getId_alamat() {
        return id_alamat;
    }

    public void setId_alamat(String id_alamat) {
        this.id_alamat = id_alamat;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNama_penerima() {
        return nama_penerima;
    }

    public void setNama_penerima(String nama_penerima) {
        this.nama_penerima = nama_penerima;
    }

    public String getNomor_hp() {
        return nomor_hp;
    }

    public void setNomor_hp(String nomor_hp) {
        this.nomor_hp = nomor_hp;
    }

    public String getAlamat_lengkap() {
        return alamat_lengkap;
    }

    public void setAlamat_lengkap(String alamat_lengkap) {
        this.alamat_lengkap = alamat_lengkap;
    }
}