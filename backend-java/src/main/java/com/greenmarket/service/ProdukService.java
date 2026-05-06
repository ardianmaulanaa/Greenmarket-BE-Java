/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.greenmarket.service;

import com.greenmarket.dao.ProdukDAO; // Import tetap ada
// import com.greenmarket.dao.ProdukDAOImpl; // HAPUS Baris Ini karena file sudah tidak ada
import com.greenmarket.model.Produk;
import java.util.List;

public class ProdukService {
    // 1. Ubah inisialisasi agar langsung memanggil ProdukDAO
    private final ProdukDAO produkDAO = new ProdukDAO();

    public List<Produk> dapatkanSemuaProduk() {
        // 2. Ubah nama metode dari getAllProduk() menjadi getProduct() 
        // Sesuai dengan nama di diagram kelas image_03d855.png
        return produkDAO.getProduct();
    }

    public void tambahProduk(Produk produk) {
        // Validasi sederhana: pastikan harga tidak negatif
        if (produk.getHarga() >= 0) {
            // 3. Ubah nama metode dari insertProduk() menjadi addProduct()
            // Sesuai dengan nama di diagram kelas image_03d855.png
            produkDAO.addProduct(produk);
        } else {
            System.out.println("Gagal tambah: Harga tidak boleh negatif.");
        }
    }
}