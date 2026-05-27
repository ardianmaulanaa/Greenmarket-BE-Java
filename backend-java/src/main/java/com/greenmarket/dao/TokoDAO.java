package com.greenmarket.dao;

import com.greenmarket.model.Toko;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.UUID;

public class TokoDAO {

    private Toko mapResultSetToToko(ResultSet rs) throws SQLException {
        Toko toko = new Toko();
        toko.setId_toko(rs.getString("id_toko"));
        toko.setId_user(rs.getInt("id_user"));
        toko.setNama_toko(rs.getString("nama_toko"));
        toko.setEmail_bisnis(rs.getString("email_bisnis"));
        toko.setAlamat_toko(rs.getString("alamat_toko"));
        toko.setCreated_at(rs.getTimestamp("created_at"));
        return toko;
    }

    public Toko getTokoByUser(int idUser) {
        String sql = "SELECT * FROM \"Toko\" WHERE id_user = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToToko(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getTokoByUser gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertToko(Toko toko) {
        String sql = "INSERT INTO \"Toko\" (id_toko, id_user, nama_toko, email_bisnis, alamat_toko, created_at) VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = toko.getId_toko();
            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
            }

            ps.setString(1, id);
            ps.setInt(2, toko.getId_user());
            ps.setString(3, toko.getNama_toko());
            ps.setString(4, toko.getEmail_bisnis());
            ps.setString(5, toko.getAlamat_toko());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] insertToko gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateToko(Toko toko) {
        String sql = "UPDATE \"Toko\" SET nama_toko = ?, email_bisnis = ?, alamat_toko = ? WHERE id_toko = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, toko.getNama_toko());
            ps.setString(2, toko.getEmail_bisnis());
            ps.setString(3, toko.getAlamat_toko());
            ps.setString(4, toko.getId_toko());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] updateToko gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}