package com.greenmarket.model;

import java.util.List;

public class CheckoutRequest {
    private int id_user;
    private String id_alamat;
    private String id_jasa_kirim;
    private String id_metode_pembayaran;
    private String status_transaksi;
    private int total_harga;
    private List<CheckoutItem> items;

    public CheckoutRequest() {
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getId_alamat() {
        return id_alamat;
    }

    public void setId_alamat(String id_alamat) {
        this.id_alamat = id_alamat;
    }

    public String getId_jasa_kirim() {
        return id_jasa_kirim;
    }

    public void setId_jasa_kirim(String id_jasa_kirim) {
        this.id_jasa_kirim = id_jasa_kirim;
    }

    public String getId_metode_pembayaran() {
        return id_metode_pembayaran;
    }

    public void setId_metode_pembayaran(String id_metode_pembayaran) {
        this.id_metode_pembayaran = id_metode_pembayaran;
    }

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(String status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public List<CheckoutItem> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItem> items) {
        this.items = items;
    }
}
