package com.greenmarket.model;

public class Metode_Pembayaran {
    private String id_metode;
    private String nama_metode;
    private String kode_metode;

    public Metode_Pembayaran() {
    }

    public Metode_Pembayaran(String id_metode, String nama_metode, String kode_metode) {
        this.id_metode = id_metode;
        this.nama_metode = nama_metode;
        this.kode_metode = kode_metode;
    }

    public String getId_metode() {
        return id_metode;
    }

    public void setId_metode(String id_metode) {
        this.id_metode = id_metode;
    }

    public String getNama_metode() {
        return nama_metode;
    }

    public void setNama_metode(String nama_metode) {
        this.nama_metode = nama_metode;
    }

    public String getKode_metode() {
        return kode_metode;
    }

    public void setKode_metode(String kode_metode) {
        this.kode_metode = kode_metode;
    }
}