package com.greenmarket.service;

import com.greenmarket.dao.PembayaranDAO;
import com.greenmarket.model.Pembayaran;

public class PembayaranService {

    private final PembayaranDAO pembayaranDAO = new PembayaranDAO();

    public boolean createPembayaran(Pembayaran pembayaran) {
        if (pembayaran == null) return false;

        if (pembayaran.getId_transaksi() == null || pembayaran.getId_transaksi().trim().isEmpty()) {
            return false;
        }

        return pembayaranDAO.createPembayaran(pembayaran);
    }

    public Pembayaran getPembayaranByTransaksi(String idTransaksi) {
        if (idTransaksi == null || idTransaksi.trim().isEmpty()) {
            return null;
        }

        return pembayaranDAO.getPembayaranByTransaksi(idTransaksi);
    }

    public boolean updateStatusPembayaran(String idPembayaran, String statusPembayaran) {
        if (idPembayaran == null || idPembayaran.trim().isEmpty()) {
            return false;
        }

        if (statusPembayaran == null || statusPembayaran.trim().isEmpty()) {
            return false;
        }

        return pembayaranDAO.updateStatusPembayaran(idPembayaran, statusPembayaran);
    }
}