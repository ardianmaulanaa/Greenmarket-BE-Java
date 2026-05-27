package com.greenmarket.model;

import java.sql.Timestamp;
import java.util.List;

public class Produk {
    private String id_produk;
    private int id_user_seller;
    private String id_kategori;
    private String nama_produk;
    private String deskripsi;
    private int harga;
    private int stok;
    private String status_produk;
    private Timestamp created_at;
    private String foto_produk;
    private String konten_deskripsi;
    private String catatan_penjual;
    private List<String> foto_produk_list;

    public Produk() {
    }

    public Produk(
            String id_produk,
            int id_user_seller,
            String id_kategori,
            String nama_produk,
            String deskripsi,
            int harga,
            int stok,
            String status_produk,
            Timestamp created_at,
            String foto_produk,
            String konten_deskripsi,
            String catatan_penjual,
            List<String> foto_produk_list
    ) {
        this.id_produk = id_produk;
        this.id_user_seller = id_user_seller;
        this.id_kategori = id_kategori;
        this.nama_produk = nama_produk;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.stok = stok;
        this.status_produk = status_produk;
        this.created_at = created_at;
        this.foto_produk = foto_produk;
        this.konten_deskripsi = konten_deskripsi;
        this.catatan_penjual = catatan_penjual;
        this.foto_produk_list = foto_produk_list;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public int getId_user_seller() {
        return id_user_seller;
    }

    public void setId_user_seller(int id_user_seller) {
        this.id_user_seller = id_user_seller;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getStatus_produk() {
        return status_produk;
    }

    public void setStatus_produk(String status_produk) {
        this.status_produk = status_produk;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getFoto_produk() {
        return foto_produk;
    }

    public void setFoto_produk(String foto_produk) {
        this.foto_produk = foto_produk;
    }

    public String getKonten_deskripsi() {
        return konten_deskripsi;
    }

    public void setKonten_deskripsi(String konten_deskripsi) {
        this.konten_deskripsi = konten_deskripsi;
    }

    public String getCatatan_penjual() {
        return catatan_penjual;
    }

    public void setCatatan_penjual(String catatan_penjual) {
        this.catatan_penjual = catatan_penjual;
    }

    public List<String> getFoto_produk_list() {
        return foto_produk_list;
    }

    public void setFoto_produk_list(List<String> foto_produk_list) {
        this.foto_produk_list = foto_produk_list;
    }
}