package com.greenmarket.model;

import java.sql.Timestamp;

public class TrackingLog {
    private String id_log;
    private String id_transaksi;
    private String status;
    private Timestamp waktu;

    public TrackingLog() {
    }

    public TrackingLog(String id_log, String id_transaksi, String status, Timestamp waktu) {
        this.id_log = id_log;
        this.id_transaksi = id_transaksi;
        this.status = status;
        this.waktu = waktu;
    }

    public String getId_log() {
        return id_log;
    }

    public void setId_log(String id_log) {
        this.id_log = id_log;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getWaktu() {
        return waktu;
    }

    public void setWaktu(Timestamp waktu) {
        this.waktu = waktu;
    }
}