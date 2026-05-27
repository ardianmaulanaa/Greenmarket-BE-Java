package com.greenmarket.model;

public class Jasa_Kirim {
    private String id_jasa;
    private String nama_jasa;
    private int harga_pengiriman;
    private String estimasi_waktu;

    public Jasa_Kirim() {
    }

    public Jasa_Kirim(String id_jasa, String nama_jasa, int harga_pengiriman, String estimasi_waktu) {
        this.id_jasa = id_jasa;
        this.nama_jasa = nama_jasa;
        this.harga_pengiriman = harga_pengiriman;
        this.estimasi_waktu = estimasi_waktu;
    }

    public String getId_jasa() {
        return id_jasa;
    }

    public void setId_jasa(String id_jasa) {
        this.id_jasa = id_jasa;
    }

    public String getNama_jasa() {
        return nama_jasa;
    }

    public void setNama_jasa(String nama_jasa) {
        this.nama_jasa = nama_jasa;
    }

    public int getHarga_pengiriman() {
        return harga_pengiriman;
    }

    public void setHarga_pengiriman(int harga_pengiriman) {
        this.harga_pengiriman = harga_pengiriman;
    }

    public String getEstimasi_waktu() {
        return estimasi_waktu;
    }

    public void setEstimasi_waktu(String estimasi_waktu) {
        this.estimasi_waktu = estimasi_waktu;
    }
}