package com.greenmarket.dao;

import com.greenmarket.model.Produk;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ProdukDAO {

    private Produk mapResultSetToProduk(ResultSet rs) throws SQLException {
        Produk produk = new Produk();

        produk.setId_produk(rs.getString("id_produk"));
        produk.setId_user_seller(rs.getInt("id_user_seller"));
        produk.setId_kategori(rs.getString("id_kategori"));
        produk.setNama_produk(rs.getString("nama_produk"));
        produk.setDeskripsi(rs.getString("deskripsi"));
        produk.setHarga(rs.getInt("harga"));
        produk.setStok(rs.getInt("stok"));
        produk.setStatus_produk(rs.getString("status_produk"));
        produk.setCreated_at(rs.getTimestamp("created_at"));
        produk.setFoto_produk(rs.getString("foto_produk"));
        produk.setKonten_deskripsi(rs.getString("konten_deskripsi"));
        produk.setCatatan_penjual(rs.getString("catatan_penjual"));

        Array fotoArray = rs.getArray("foto_produk_list");
        if (fotoArray != null) {
            String[] fotoList = (String[]) fotoArray.getArray();
            produk.setFoto_produk_list(Arrays.asList(fotoList));
        }

        return produk;
    }

    public List<Produk> getAllProduk() {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Produk\" ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToProduk(rs));
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getAllProduk gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Produk getProdukById(String idProduk) {
        String sql = "SELECT * FROM \"Produk\" WHERE id_produk = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idProduk);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduk(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getProdukById gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Produk> getProdukBySeller(int idSeller) {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Produk\" WHERE id_user_seller = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSeller);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduk(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getProdukBySeller gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Produk> getProdukByKategori(String idKategori) {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Produk\" WHERE id_kategori = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idKategori);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduk(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getProdukByKategori gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Produk> searchProduk(String keyword) {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Produk\" WHERE LOWER(nama_produk) LIKE LOWER(?) OR LOWER(deskripsi) LIKE LOWER(?) ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduk(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] searchProduk gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public boolean insertProduk(Produk produk) {
        String sql = "INSERT INTO \"Produk\" " +
                "(id_produk, id_user_seller, id_kategori, nama_produk, deskripsi, harga, stok, status_produk, created_at, foto_produk, konten_deskripsi, catatan_penjual, foto_produk_list) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = produk.getId_produk();
            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
            }

            ps.setString(1, id);
            ps.setInt(2, produk.getId_user_seller());
            ps.setString(3, produk.getId_kategori());
            ps.setString(4, produk.getNama_produk());
            ps.setString(5, produk.getDeskripsi());
            ps.setInt(6, produk.getHarga());
            ps.setInt(7, produk.getStok());
            ps.setString(8, produk.getStatus_produk() == null ? "AKTIF" : produk.getStatus_produk());
            ps.setString(9, produk.getFoto_produk());
            ps.setString(10, produk.getKonten_deskripsi());
            ps.setString(11, produk.getCatatan_penjual());

            if (produk.getFoto_produk_list() != null) {
                Array fotoArray = conn.createArrayOf("text", produk.getFoto_produk_list().toArray());
                ps.setArray(12, fotoArray);
            } else {
                ps.setNull(12, Types.ARRAY);
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] insertProduk gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateProduk(Produk produk) {
        String sql = "UPDATE \"Produk\" SET id_kategori = ?, nama_produk = ?, deskripsi = ?, harga = ?, stok = ?, status_produk = ?, foto_produk = ?, konten_deskripsi = ?, catatan_penjual = ?, foto_produk_list = ? WHERE id_produk = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produk.getId_kategori());
            ps.setString(2, produk.getNama_produk());
            ps.setString(3, produk.getDeskripsi());
            ps.setInt(4, produk.getHarga());
            ps.setInt(5, produk.getStok());
            ps.setString(6, produk.getStatus_produk());
            ps.setString(7, produk.getFoto_produk());
            ps.setString(8, produk.getKonten_deskripsi());
            ps.setString(9, produk.getCatatan_penjual());

            if (produk.getFoto_produk_list() != null) {
                Array fotoArray = conn.createArrayOf("text", produk.getFoto_produk_list().toArray());
                ps.setArray(10, fotoArray);
            } else {
                ps.setNull(10, Types.ARRAY);
            }

            ps.setString(11, produk.getId_produk());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] updateProduk gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteProduk(String idProduk) {
        String sql = "DELETE FROM \"Produk\" WHERE id_produk = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idProduk);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] deleteProduk gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}