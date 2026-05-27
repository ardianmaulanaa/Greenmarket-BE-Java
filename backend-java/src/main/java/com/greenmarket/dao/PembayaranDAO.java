package com.greenmarket.dao;

import com.greenmarket.model.Pembayaran;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.UUID;

public class PembayaranDAO {

    private Pembayaran mapResultSetToPembayaran(ResultSet rs) throws SQLException {
        Pembayaran pembayaran = new Pembayaran();
        pembayaran.setId_pembayaran(rs.getString("id_pembayaran"));
        pembayaran.setId_transaksi(rs.getString("id_transaksi"));
        pembayaran.setStatus_pembayaran(rs.getString("status_pembayaran"));
        pembayaran.setTanggal_pembayaran(rs.getTimestamp("tanggal_pembayaran"));
        return pembayaran;
    }

    public boolean createPembayaran(Pembayaran pembayaran) {
        String sql = "INSERT INTO \"Pembayaran\" (id_pembayaran, id_transaksi, status_pembayaran, tanggal_pembayaran) VALUES (?, ?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = pembayaran.getId_pembayaran();
            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
            }

            ps.setString(1, id);
            ps.setString(2, pembayaran.getId_transaksi());
            ps.setString(3, pembayaran.getStatus_pembayaran() == null ? "MENUNGGU_PEMBAYARAN" : pembayaran.getStatus_pembayaran());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] createPembayaran gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public Pembayaran getPembayaranByTransaksi(String idTransaksi) {
        String sql = "SELECT * FROM \"Pembayaran\" WHERE id_transaksi = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idTransaksi);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPembayaran(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getPembayaranByTransaksi gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateStatusPembayaran(String idPembayaran, String statusPembayaran) {
        String sql = "UPDATE \"Pembayaran\" SET status_pembayaran = ?, tanggal_pembayaran = NOW() WHERE id_pembayaran = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statusPembayaran);
            ps.setString(2, idPembayaran);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] updateStatusPembayaran gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}