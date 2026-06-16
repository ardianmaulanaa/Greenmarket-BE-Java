package com.greenmarket.model;

import java.sql.Timestamp;

public class Pembayaran implements Payment {
    private String id_pembayaran;
    private String id_transaksi;
    private String status_pembayaran;
    private Timestamp tanggal_pembayaran;

    public Pembayaran() {
    }

    public Pembayaran(String id_pembayaran, String id_transaksi, String status_pembayaran, Timestamp tanggal_pembayaran) {
        this.id_pembayaran = id_pembayaran;
        this.id_transaksi = id_transaksi;
        this.status_pembayaran = status_pembayaran;
        this.tanggal_pembayaran = tanggal_pembayaran;
    }

    public String getId_pembayaran() {
        return id_pembayaran;
    }

    public void setId_pembayaran(String id_pembayaran) {
        this.id_pembayaran = id_pembayaran;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getStatus_pembayaran() {
        return status_pembayaran;
    }

    public void setStatus_pembayaran(String status_pembayaran) {
        this.status_pembayaran = status_pembayaran;
    }

    public Timestamp getTanggal_pembayaran() {
        return tanggal_pembayaran;
    }

    public void setTanggal_pembayaran(Timestamp tanggal_pembayaran) {
        this.tanggal_pembayaran = tanggal_pembayaran;
    }

    @Override
    public boolean isConfirmed() {
        return "SUCCESS".equalsIgnoreCase(status_pembayaran)
                || "PAID".equalsIgnoreCase(status_pembayaran);
    }

    @Override
    public String getPaymentType() {
        return "GENERAL";
    }
}