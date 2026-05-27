package com.greenmarket.dao;

import com.greenmarket.model.Detail_Transaksi;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetailTransaksiDAO {

    private Detail_Transaksi mapResultSetToDetail(ResultSet rs) throws SQLException {
        Detail_Transaksi detail = new Detail_Transaksi();
        detail.setId_detail(rs.getString("id_detail"));
        detail.setId_transaksi(rs.getString("id_transaksi"));
        detail.setId_produk(rs.getString("id_produk"));
        detail.setKuantitas(rs.getInt("kuantitas"));
        detail.setHarga_satuan(rs.getInt("harga_satuan"));
        detail.setSubtotal(rs.getInt("subtotal"));
        return detail;
    }

    public boolean insertDetailTransaksi(Detail_Transaksi detail) {
        String sql = "INSERT INTO \"Detail_Transaksi\" (id_detail, id_transaksi, id_produk, kuantitas, harga_satuan, subtotal) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = detail.getId_detail();
            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
            }

            ps.setString(1, id);
            ps.setString(2, detail.getId_transaksi());
            ps.setString(3, detail.getId_produk());
            ps.setInt(4, detail.getKuantitas());
            ps.setInt(5, detail.getHarga_satuan());
            ps.setInt(6, detail.getSubtotal());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] insertDetailTransaksi gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public List<Detail_Transaksi> getDetailByTransaksi(String idTransaksi) {
        List<Detail_Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Detail_Transaksi\" WHERE id_transaksi = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idTransaksi);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToDetail(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getDetailByTransaksi gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }
}