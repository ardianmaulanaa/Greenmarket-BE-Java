package com.greenmarket.dao;

import com.greenmarket.model.Transaksi;
import java.util.List;

public interface ITransaksiDAO {
    List<Transaksi> getTransaksiByUser(int idUser);
    List<Transaksi> getTransaksiBySeller(int idSeller);
    Transaksi getTransaksiById(String idTransaksi);
    boolean createTransaksi(Transaksi transaksi);
    boolean createTransaksiMultiProduk(Transaksi transaksi);
    boolean updateStatusTransaksi(String idTransaksi, String status);
    boolean konfirmasiKirim(String idTransaksi, int idSeller);
}
