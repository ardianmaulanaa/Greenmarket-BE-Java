package com.greenmarket.service;

import com.greenmarket.dao.DetailTransaksiDAO;
import com.greenmarket.model.Detail_Transaksi;

import java.util.List;

public class DetailTransaksiService {

    private final DetailTransaksiDAO detailTransaksiDAO = new DetailTransaksiDAO();

    public boolean insertDetailTransaksi(Detail_Transaksi detail) {
        if (detail == null) return false;

        if (detail.getId_transaksi() == null || detail.getId_transaksi().trim().isEmpty()) {
            return false;
        }

        if (detail.getId_produk() == null || detail.getId_produk().trim().isEmpty()) {
            return false;
        }

        if (detail.getKuantitas() <= 0) {
            return false;
        }

        if (detail.getHarga_satuan() < 0 || detail.getSubtotal() < 0) {
            return false;
        }

        return detailTransaksiDAO.insertDetailTransaksi(detail);
    }

    public List<Detail_Transaksi> getDetailByTransaksi(String idTransaksi) {
        if (idTransaksi == null || idTransaksi.trim().isEmpty()) {
            return null;
        }

        return detailTransaksiDAO.getDetailByTransaksi(idTransaksi);
    }
}