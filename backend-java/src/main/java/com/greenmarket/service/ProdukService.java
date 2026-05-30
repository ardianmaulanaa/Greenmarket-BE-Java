package com.greenmarket.service;

import com.greenmarket.dao.ProdukDAO;
import com.greenmarket.model.Produk;

import java.util.List;

public class ProdukService {

    private final ProdukDAO produkDAO = new ProdukDAO();

    public List<Produk> getAllProduk() {
        return produkDAO.getAllProduk();
    }

    public Produk getProdukById(String idProduk) {
        if (idProduk == null || idProduk.trim().isEmpty()) {
            return null;
        }

        return produkDAO.getProdukById(idProduk);
    }

    public List<Produk> getProdukBySeller(int idSeller) {
        if (idSeller <= 0) {
            return getAllProduk();
        }

        return produkDAO.getProdukBySeller(idSeller);
    }

    public List<Produk> getProdukByKategori(String idKategori) {
        if (idKategori == null || idKategori.trim().isEmpty()) {
            return getAllProduk();
        }

        return produkDAO.getProdukByKategori(idKategori);
    }

    public List<Produk> getProdukByKategoriList(String kategoriParam) {
        if (kategoriParam == null || kategoriParam.trim().isEmpty()) {
            return getAllProduk();
        }

        return produkDAO.getProdukByKategoriList(kategoriParam);
    }

    public List<Produk> searchProduk(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProduk();
        }

        return produkDAO.searchProduk(keyword);
    }

    public boolean insertProduk(Produk produk) {
        if (produk == null)
            return false;

        if (produk.getId_user_seller() <= 0)
            return false;

        if (produk.getNama_produk() == null || produk.getNama_produk().trim().isEmpty()) {
            return false;
        }

        if (produk.getHarga() < 0 || produk.getStok() < 0) {
            return false;
        }

        return produkDAO.insertProduk(produk);
    }

    public boolean updateProduk(Produk produk) {
        if (produk == null)
            return false;

        if (produk.getId_produk() == null || produk.getId_produk().trim().isEmpty()) {
            return false;
        }

        return produkDAO.updateProduk(produk);
    }

    public boolean deleteProduk(String idProduk) {
        if (idProduk == null || idProduk.trim().isEmpty()) {
            return false;
        }

        return produkDAO.deleteProduk(idProduk);
    }
}