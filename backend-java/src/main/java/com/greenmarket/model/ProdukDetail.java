package com.greenmarket.model;

/**
 *
 * @author mac
 */
public class ProdukDetail {
    // Atribut disesuaikan dengan skema database (UUID menggunakan String di Java)
    private String id_detail; 
    private String id_produk;
    private String konten_deskripsi;
    private String catatan_penjual;
    
    // Objek Produk untuk relasi (Join)
    private Produk produk;

    // 1. Constructor Kosong (Wajib untuk Gson/JSON)
    public ProdukDetail() {
    }

    // 2. Constructor Lengkap
    public ProdukDetail(String id_detail, String id_produk, String konten_deskripsi, String catatan_penjual) {
        this.id_detail = id_detail;
        this.id_produk = id_produk;
        this.konten_deskripsi = konten_deskripsi;
        this.catatan_penjual = catatan_penjual;
    }

    // 3. Getter dan Setter
    public String getId_detail() {
        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
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

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }
}