package com.greenmarket.dao;

import com.greenmarket.model.Keranjang;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KeranjangDAO {

    private Keranjang mapResultSetToKeranjang(ResultSet rs) throws SQLException {
        Keranjang keranjang = new Keranjang();

        keranjang.setId_keranjang(rs.getString("id_keranjang"));
        keranjang.setId_user(rs.getLong("id_user"));
        keranjang.setId_produk(rs.getString("id_produk"));
        keranjang.setCreated_at(rs.getTimestamp("created_at"));

        return keranjang;
    }

    public List<Keranjang> getKeranjangByUser(long idUser) {
        List<Keranjang> list = new ArrayList<>();

        String sql = "SELECT * FROM \"Keranjang\" WHERE id_user = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idUser);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToKeranjang(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getKeranjangByUser gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public boolean addToKeranjang(Keranjang keranjang) {
        String sql = "INSERT INTO \"Keranjang\" (id_keranjang, id_user, id_produk, created_at) VALUES (?, ?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = keranjang.getId_keranjang();

            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
            }

            ps.setString(1, id);
            ps.setLong(2, keranjang.getId_user());
            ps.setString(3, keranjang.getId_produk());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] addToKeranjang gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean isProdukInKeranjang(long idUser, String idProduk) {
        String sql = "SELECT COUNT(*) FROM \"Keranjang\" WHERE id_user = ? AND id_produk = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idUser);
            ps.setString(2, idProduk);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] isProdukInKeranjang gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteFromKeranjang(String idKeranjang) {
        String sql = "DELETE FROM \"Keranjang\" WHERE id_keranjang = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idKeranjang);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] deleteFromKeranjang gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean clearKeranjangByUser(long idUser) {
        String sql = "DELETE FROM \"Keranjang\" WHERE id_user = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idUser);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] clearKeranjangByUser gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}