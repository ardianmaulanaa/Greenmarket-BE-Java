package com.greenmarket.dao;

import com.greenmarket.model.Transaksi;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransaksiDAO {

    private Transaksi mapResultSetToTransaksi(ResultSet rs) throws SQLException {
        Transaksi transaksi = new Transaksi();
        transaksi.setId_transaksi(rs.getString("id_transaksi"));
        transaksi.setId_user(rs.getInt("id_user"));
        transaksi.setId_alamat(rs.getString("id_alamat"));
        transaksi.setId_jasa_kirim(rs.getString("id_jasa_kirim"));
        transaksi.setId_metode_pembayaran(rs.getString("id_metode_pembayaran"));
        transaksi.setStatus_transaksi(rs.getString("status_transaksi"));
        transaksi.setTanggal_transaksi(rs.getTimestamp("tanggal_transaksi"));
        transaksi.setTotal_harga(rs.getInt("total_harga"));
        return transaksi;
    }

    public boolean createTransaksi(Transaksi transaksi) {
        String sql = "INSERT INTO \"Transaksi\" " +
                "(id_transaksi, id_user, id_alamat, id_jasa_kirim, id_metode_pembayaran, status_transaksi, tanggal_transaksi, total_harga) " +
                "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = transaksi.getId_transaksi();
            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
            }

            ps.setString(1, id);
            ps.setInt(2, transaksi.getId_user());
            ps.setString(3, transaksi.getId_alamat());
            ps.setString(4, transaksi.getId_jasa_kirim());
            ps.setString(5, transaksi.getId_metode_pembayaran());
            ps.setString(6, transaksi.getStatus_transaksi() == null ? "BELUM_BAYAR" : transaksi.getStatus_transaksi());
            ps.setInt(7, transaksi.getTotal_harga());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] createTransaksi gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public Transaksi getTransaksiById(String idTransaksi) {
        String sql = "SELECT * FROM \"Transaksi\" WHERE id_transaksi = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idTransaksi);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransaksi(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getTransaksiById gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Transaksi> getTransaksiByUser(int idUser) {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Transaksi\" WHERE id_user = ? ORDER BY tanggal_transaksi DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToTransaksi(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getTransaksiByUser gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateStatusTransaksi(String idTransaksi, String statusTransaksi) {
        String sql = "UPDATE \"Transaksi\" SET status_transaksi = ? WHERE id_transaksi = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statusTransaksi);
            ps.setString(2, idTransaksi);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] updateStatusTransaksi gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}