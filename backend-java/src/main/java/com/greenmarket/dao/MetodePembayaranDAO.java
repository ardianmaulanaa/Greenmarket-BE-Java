package com.greenmarket.dao;

import com.greenmarket.model.Metode_Pembayaran;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MetodePembayaranDAO {

    private Metode_Pembayaran mapResultSetToMetode(ResultSet rs) throws SQLException {
        Metode_Pembayaran metode = new Metode_Pembayaran();
        metode.setId_metode(rs.getString("id_metode"));
        metode.setNama_metode(rs.getString("nama_metode"));
        metode.setKode_metode(rs.getString("kode_metode"));
        return metode;
    }

    public List<Metode_Pembayaran> getAllMetodePembayaran() {
        List<Metode_Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Metode_Pembayaran\"";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToMetode(rs));
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getAllMetodePembayaran gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Metode_Pembayaran getMetodeById(String idMetode) {
        String sql = "SELECT * FROM \"Metode_Pembayaran\" WHERE id_metode = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idMetode);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMetode(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getMetodeById gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertMetode(Metode_Pembayaran metode) {
        String sql = "INSERT INTO \"Metode_Pembayaran\" (id_metode, nama_metode, kode_metode) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = metode.getId_metode();
            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
            }

            ps.setString(1, id);
            ps.setString(2, metode.getNama_metode());
            ps.setString(3, metode.getKode_metode());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] insertMetode gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}