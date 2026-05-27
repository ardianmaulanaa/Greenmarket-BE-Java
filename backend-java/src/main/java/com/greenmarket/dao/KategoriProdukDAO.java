package com.greenmarket.dao;

import com.greenmarket.model.KategoriProduk;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriProdukDAO {

    private KategoriProduk mapResultSetToKategori(ResultSet rs) throws SQLException {
        KategoriProduk kategori = new KategoriProduk();
        kategori.setId_kategori(rs.getString("id_kategori"));
        kategori.setNama_kategori(rs.getString("nama_kategori"));
        return kategori;
    }

    public List<KategoriProduk> getAllKategori() {
        List<KategoriProduk> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Kategori_Produk\" ORDER BY id_kategori ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToKategori(rs));
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getAllKategori gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public KategoriProduk getKategoriById(String idKategori) {
        String sql = "SELECT * FROM \"Kategori_Produk\" WHERE id_kategori = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idKategori);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToKategori(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getKategoriById gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertKategori(KategoriProduk kategori) {
        String sql = "INSERT INTO \"Kategori_Produk\" (id_kategori, nama_kategori) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kategori.getId_kategori());
            ps.setString(2, kategori.getNama_kategori());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] insertKategori gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateKategori(KategoriProduk kategori) {
        String sql = "UPDATE \"Kategori_Produk\" SET nama_kategori = ? WHERE id_kategori = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kategori.getNama_kategori());
            ps.setString(2, kategori.getId_kategori());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] updateKategori gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteKategori(String idKategori) {
        String sql = "DELETE FROM \"Kategori_Produk\" WHERE id_kategori = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idKategori);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] deleteKategori gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}