package com.greenmarket.dao;

import com.greenmarket.model.Produk;
import java.util.List;

public interface IProdukDAO {
    List<Produk> getAllProduk();
    Produk getProdukById(String idProduk);
    List<Produk> getProdukBySeller(int idSeller);
    List<Produk> getProdukByKategori(String idKategori);
    List<Produk> getProdukByKategoriList(String kategoriParam);
    List<Produk> searchProduk(String keyword);
    boolean insertProduk(Produk produk);
    boolean updateProduk(Produk produk);
    boolean deleteProduk(String idProduk);
}
