package com.greenmarket.service;

import com.greenmarket.dao.TransaksiDAO;
import com.greenmarket.dao.ITransaksiDAO;
import com.greenmarket.model.Transaksi;

import java.util.List;

public class TransaksiService {

    private final ITransaksiDAO transaksiDAO = new TransaksiDAO();

    public boolean createTransaksi(Transaksi transaksi) {
        if (transaksi == null)
            return false;

        if (transaksi.getId_user() <= 0) {
            return false;
        }

        if (transaksi.getId_alamat() == null || transaksi.getId_alamat().trim().isEmpty()) {
            return false;
        }

        if (transaksi.getId_jasa_kirim() == null || transaksi.getId_jasa_kirim().trim().isEmpty()) {
            return false;
        }

        if (transaksi.getId_metode_pembayaran() == null || transaksi.getId_metode_pembayaran().trim().isEmpty()) {
            return false;
        }

        if (transaksi.getTotal_harga() < 0) {
            return false;
        }

        return transaksiDAO.createTransaksi(transaksi);
    }

    public boolean createTransaksiMultiProduk(Transaksi transaksi) {
        if (transaksi == null) {
            return false;
        }

        if (transaksi.getId_user() <= 0) {
            return false;
        }

        if (transaksi.getId_alamat() == null || transaksi.getId_alamat().trim().isEmpty()) {
            return false;
        }

        if (transaksi.getId_jasa_kirim() == null || transaksi.getId_jasa_kirim().trim().isEmpty()) {
            return false;
        }

        if (transaksi.getId_metode_pembayaran() == null || transaksi.getId_metode_pembayaran().trim().isEmpty()) {
            return false;
        }

        return transaksiDAO.createTransaksiMultiProduk(transaksi);
    }

    public Transaksi getTransaksiById(String idTransaksi) {
        if (idTransaksi == null || idTransaksi.trim().isEmpty()) {
            return null;
        }

        return transaksiDAO.getTransaksiById(idTransaksi);
    }

    public List<Transaksi> getTransaksiByUser(int idUser) {
        if (idUser <= 0) {
            return null;
        }

        return transaksiDAO.getTransaksiByUser(idUser);
    }

    public List<Transaksi> getTransaksiBySeller(int idSeller) {
        if (idSeller <= 0) {
            return null;
        }

        return transaksiDAO.getTransaksiBySeller(idSeller);
    }

    public boolean konfirmasiKirim(String idTransaksi, int idSeller) {
        if (idTransaksi == null || idTransaksi.trim().isEmpty()) {
            return false;
        }

        if (idSeller <= 0) {
            return false;
        }

        return transaksiDAO.konfirmasiKirim(idTransaksi, idSeller);
    }

    public boolean updateStatusTransaksi(String idTransaksi, String statusTransaksi) {
        if (idTransaksi == null || idTransaksi.trim().isEmpty()) {
            return false;
        }

        if (statusTransaksi == null || statusTransaksi.trim().isEmpty()) {
            return false;
        }

        return transaksiDAO.updateStatusTransaksi(idTransaksi, statusTransaksi);
    }
}