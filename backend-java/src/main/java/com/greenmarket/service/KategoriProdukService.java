package com.greenmarket.service;

import com.greenmarket.dao.KategoriProdukDAO;
import com.greenmarket.model.KategoriProduk;

import java.util.List;

public class KategoriProdukService {

    private final KategoriProdukDAO kategoriProdukDAO = new KategoriProdukDAO();

    public List<KategoriProduk> getAllKategori() {
        return kategoriProdukDAO.getAllKategori();
    }

    public KategoriProduk getKategoriById(String idKategori) {
        if (idKategori == null || idKategori.trim().isEmpty()) {
            return null;
        }

        return kategoriProdukDAO.getKategoriById(idKategori);
    }

    public boolean insertKategori(KategoriProduk kategori) {
        if (kategori == null) return false;

        if (kategori.getId_kategori() == null || kategori.getId_kategori().trim().isEmpty()) {
            return false;
        }

        if (kategori.getNama_kategori() == null || kategori.getNama_kategori().trim().isEmpty()) {
            return false;
        }

        return kategoriProdukDAO.insertKategori(kategori);
    }

    public boolean updateKategori(KategoriProduk kategori) {
        if (kategori == null) return false;

        if (kategori.getId_kategori() == null || kategori.getId_kategori().trim().isEmpty()) {
            return false;
        }

        if (kategori.getNama_kategori() == null || kategori.getNama_kategori().trim().isEmpty()) {
            return false;
        }

        return kategoriProdukDAO.updateKategori(kategori);
    }

    public boolean deleteKategori(String idKategori) {
        if (idKategori == null || idKategori.trim().isEmpty()) {
            return false;
        }

        return kategoriProdukDAO.deleteKategori(idKategori);
    }
}