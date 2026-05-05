/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.greenmarket.service;

/**
 *
 * @author mac
 */
import com.greenmarket.dao.ProdukDAO;
import com.greenmarket.dao.ProdukDAOImpl;
import com.greenmarket.model.Produk;
import java.util.List;

public class ProdukService {
    private final ProdukDAO produkDAO = new ProdukDAOImpl();

    public List<Produk> dapatkanSemuaProduk() {
        return produkDAO.getAllProduk();
    }


    public void tambahProduk(Produk produk) {
        // Validasi sederhana: pastikan harga tidak negatif
        if (produk.getHarga() >= 0) {
            produkDAO.insertProduk(produk);
        } else {
            System.out.println("Gagal tambah: Harga tidak boleh negatif.");
        }
    }
}