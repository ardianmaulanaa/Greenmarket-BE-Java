package com.greenmarket.dao;

import com.greenmarket.model.Alamat;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlamatDAO {

    private Alamat mapResultSetToAlamat(ResultSet rs) throws SQLException {
        Alamat alamat = new Alamat();
        alamat.setId_alamat(rs.getString("id_alamat"));
        alamat.setId_user(rs.getInt("id_user"));
        alamat.setNama_penerima(rs.getString("nama_penerima"));
        alamat.setNomor_hp(rs.getString("nomor_hp"));
        alamat.setAlamat_lengkap(rs.getString("alamat_lengkap"));
        return alamat;
    }

    public List<Alamat> getAlamatByUser(int idUser) {
        List<Alamat> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Alamat\" WHERE id_user = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToAlamat(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getAlamatByUser gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Alamat getAlamatById(String idAlamat) {
        String sql = "SELECT * FROM \"Alamat\" WHERE id_alamat = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idAlamat);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAlamat(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getAlamatById gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertAlamat(Alamat alamat) {
        String sql = "INSERT INTO \"Alamat\" (id_alamat, id_user, nama_penerima, nomor_hp, alamat_lengkap) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = alamat.getId_alamat();
            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
            }

            ps.setString(1, id);
            ps.setInt(2, alamat.getId_user());
            ps.setString(3, alamat.getNama_penerima());
            ps.setString(4, alamat.getNomor_hp());
            ps.setString(5, alamat.getAlamat_lengkap());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] insertAlamat gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateAlamat(Alamat alamat) {
        String sql = "UPDATE \"Alamat\" SET nama_penerima = ?, nomor_hp = ?, alamat_lengkap = ? WHERE id_alamat = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, alamat.getNama_penerima());
            ps.setString(2, alamat.getNomor_hp());
            ps.setString(3, alamat.getAlamat_lengkap());
            ps.setString(4, alamat.getId_alamat());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] updateAlamat gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteAlamat(String idAlamat) {
        String sql = "DELETE FROM \"Alamat\" WHERE id_alamat = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idAlamat);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] deleteAlamat gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}