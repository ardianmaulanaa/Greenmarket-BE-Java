package com.greenmarket.dao;

import com.greenmarket.model.KategoriProduk;
import com.greenmarket.model.Produk;
import com.greenmarket.model.ProdukFoto;
import com.greenmarket.model.User;
import com.greenmarket.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdukDAOImpl implements ProdukDAO {

    @Override
    public List<Produk> getAllProduk() {
        List<Produk> list = new ArrayList<>();

        // Gunakan kutip ganda (\") untuk nama tabel yang berawalan huruf kapital
        // Berdasarkan image_043d7a.png, tabelnya adalah "Produk", "Kategori_Produk", dan "User"
        String sql = "SELECT p.id_produk, p.id_user_seller, p.id_kategori, " +
                     "p.nama_produk, p.deskripsi, p.harga, p.stok, " +
                     "k.nama_kategori, " +
                     "u.username, u.email " +
                     "FROM \"Produk\" p " +
                     "LEFT JOIN \"Kategori_Produk\" k ON p.id_kategori = k.id_kategori " +
                     "LEFT JOIN \"User\" u ON p.id_user_seller = u.id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("✅ Koneksi berhasil!");

            while (rs.next()) {
                Produk p = new Produk();
                p.setId_produk(rs.getString("id_produk"));
                p.setId_user_seller(rs.getInt("id_user_seller"));
                p.setId_kategori(rs.getString("id_kategori"));
                p.setNama_produk(rs.getString("nama_produk"));
                p.setDeskripsi(rs.getString("deskripsi"));
                p.setHarga(rs.getInt("harga"));
                p.setStok(rs.getInt("stok"));

                KategoriProduk kat = new KategoriProduk();
                kat.setNama_kategori(rs.getString("nama_kategori"));
                p.setKategori(kat);

                User seller = new User();
                seller.setUsername(rs.getString("username"));
                seller.setEmail(rs.getString("email"));
                p.setSeller(seller);

                p.setFotos(getFotosByProdukId(conn, rs.getString("id_produk")));
                list.add(p);
            }

            System.out.println("📊 Total produk dari DB: " + list.size());

        } catch (SQLException e) {
            System.err.println("❌ Error getAllProduk: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    private List<ProdukFoto> getFotosByProdukId(Connection conn, String id_produk) throws SQLException {
        List<ProdukFoto> fotos = new ArrayList<>();
        // Sesuaikan dengan image_043d7a.png: "Produk_Foto"
        String sql = "SELECT url_foto FROM \"Produk_Foto\" WHERE id_produk = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id_produk);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdukFoto foto = new ProdukFoto();
                    foto.setUrl_foto(rs.getString("url_foto"));
                    fotos.add(foto);
                }
            }
        }
        return fotos;
    }

    @Override
    public Produk getProdukById(String id_produk) {
        Produk p = null;
        String sql = "SELECT p.id_produk, p.id_user_seller, p.id_kategori, " +
                     "p.nama_produk, p.deskripsi, p.harga, p.stok, " +
                     "k.nama_kategori, u.username, u.email " +
                     "FROM \"Produk\" p " +
                     "LEFT JOIN \"Kategori_Produk\" k ON p.id_kategori = k.id_kategori " +
                     "LEFT JOIN \"User\" u ON p.id_user_seller = u.id " +
                     "WHERE p.id_produk = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id_produk);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Produk();
                    p.setId_produk(rs.getString("id_produk"));
                    p.setId_user_seller(rs.getInt("id_user_seller"));
                    p.setId_kategori(rs.getString("id_kategori"));
                    p.setNama_produk(rs.getString("nama_produk"));
                    p.setDeskripsi(rs.getString("deskripsi"));
                    p.setHarga(rs.getInt("harga"));
                    p.setStok(rs.getInt("stok"));

                    KategoriProduk kat = new KategoriProduk();
                    kat.setNama_kategori(rs.getString("nama_kategori"));
                    p.setKategori(kat);

                    User seller = new User();
                    seller.setUsername(rs.getString("username"));
                    seller.setEmail(rs.getString("email"));
                    p.setSeller(seller);

                    p.setFotos(getFotosByProdukId(conn, id_produk));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error getProdukById: " + e.getMessage());
        }

        return p;
    }

    @Override
    public void insertProduk(Produk produk) {
        String sql = "INSERT INTO \"Produk\" (id_user_seller, id_kategori, nama_produk, deskripsi, harga, stok) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, produk.getId_user_seller());
            ps.setString(2, produk.getId_kategori());
            ps.setString(3, produk.getNama_produk());
            ps.setString(4, produk.getDeskripsi());
            ps.setInt(5, produk.getHarga());
            ps.setInt(6, produk.getStok());
            ps.executeUpdate();
            System.out.println("✅ Berhasil insert produk!");
        } catch (SQLException e) {
            System.err.println("--- ERROR DATABASE GREENMARKET ---");
            e.printStackTrace(); 
        }
    }
}