/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.greenmarket.dao;

/**
 *
 * @author mac
 */
import com.greenmarket.model.Produk;
import java.util.List;

public interface ProdukDAO {
    // Mengambil semua data produk dari Supabase
    List<Produk> getAllProduk();
    
    // Mencari produk berdasarkan ID (UUID)
    Produk getProdukById(String id_produk);
    
    // Menambah produk baru
    void insertProduk(Produk produk);
}
