package com.greenmarket.model;

import java.sql.Timestamp;
import java.util.List;

public class Transaksi {
    private String id_transaksi;
    private int id_user;
    private String id_alamat;
    private String id_jasa_kirim;
    private String id_metode_pembayaran;
    private String status_transaksi;
    private Timestamp tanggal_transaksi;
    private int total_harga;
    private List<Detail_Transaksi> detail_transaksi;
    private Alamat alamat;
    private Jasa_Kirim jasa_kirim;
    private Metode_Pembayaran metode_pembayaran;
    private Pembayaran pembayaran;
    private List<TrackingLog> tracking_logs;

    public Transaksi() {
    }

    public Transaksi(
            String id_transaksi,
            int id_user,
            String id_alamat,
            String id_jasa_kirim,
            String id_metode_pembayaran,
            String status_transaksi,
            Timestamp tanggal_transaksi,
            int total_harga
    ) {
        this.id_transaksi = id_transaksi;
        this.id_user = id_user;
        this.id_alamat = id_alamat;
        this.id_jasa_kirim = id_jasa_kirim;
        this.id_metode_pembayaran = id_metode_pembayaran;
        this.status_transaksi = status_transaksi;
        this.tanggal_transaksi = tanggal_transaksi;
        this.total_harga = total_harga;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
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

    public Timestamp getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public void setTanggal_transaksi(Timestamp tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }



    public List<Detail_Transaksi> getDetail_transaksi() {
        return detail_transaksi;
    }

    public void setDetail_transaksi(List<Detail_Transaksi> detail_transaksi) {
        this.detail_transaksi = detail_transaksi;
    }

    public Alamat getAlamat() {
        return alamat;
    }

    public void setAlamat(Alamat alamat) {
        this.alamat = alamat;
    }

    public Jasa_Kirim getJasa_kirim() {
        return jasa_kirim;
    }

    public void setJasa_kirim(Jasa_Kirim jasa_kirim) {
        this.jasa_kirim = jasa_kirim;
    }

    public Metode_Pembayaran getMetode_pembayaran() {
        return metode_pembayaran;
    }

    public void setMetode_pembayaran(Metode_Pembayaran metode_pembayaran) {
        this.metode_pembayaran = metode_pembayaran;
    }

    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Pembayaran pembayaran) {
        this.pembayaran = pembayaran;
    }

    public List<TrackingLog> getTracking_logs() {
        return tracking_logs;
    }

    public void setTracking_logs(List<TrackingLog> tracking_logs) {
        this.tracking_logs = tracking_logs;
    }
}