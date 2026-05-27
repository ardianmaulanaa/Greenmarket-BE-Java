package com.greenmarket.service;

import com.greenmarket.dao.KeranjangDAO;
import com.greenmarket.model.Keranjang;

import java.util.List;

public class KeranjangService {

    private final KeranjangDAO keranjangDAO = new KeranjangDAO();

    public List<Keranjang> getKeranjangByUser(long idUser) {
        return keranjangDAO.getKeranjangByUser(idUser);
    }

    public boolean addToKeranjang(Keranjang keranjang) {
        if (keranjang == null) return false;

        if (keranjang.getId_user() <= 0) {
            return false;
        }

        if (keranjang.getId_produk() == null || keranjang.getId_produk().trim().isEmpty()) {
            return false;
        }

        boolean alreadyExist = keranjangDAO.isProdukInKeranjang(
                keranjang.getId_user(),
                keranjang.getId_produk()
        );

        if (alreadyExist) {
            return false;
        }

        return keranjangDAO.addToKeranjang(keranjang);
    }

    public boolean isProdukInKeranjang(long idUser, String idProduk) {
        if (idUser <= 0 || idProduk == null || idProduk.trim().isEmpty()) {
            return false;
        }

        return keranjangDAO.isProdukInKeranjang(idUser, idProduk);
    }

    public boolean deleteFromKeranjang(String idKeranjang) {
        if (idKeranjang == null || idKeranjang.trim().isEmpty()) {
            return false;
        }

        return keranjangDAO.deleteFromKeranjang(idKeranjang);
    }

    public boolean clearKeranjangByUser(long idUser) {
        if (idUser <= 0) {
            return false;
        }

        return keranjangDAO.clearKeranjangByUser(idUser);
    }
}