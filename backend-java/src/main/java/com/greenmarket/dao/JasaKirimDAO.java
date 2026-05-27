package com.greenmarket.dao;

import com.greenmarket.model.Jasa_Kirim;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JasaKirimDAO {

    private Jasa_Kirim mapResultSetToJasa(ResultSet rs) throws SQLException {
        Jasa_Kirim jasa = new Jasa_Kirim();
        jasa.setId_jasa(rs.getString("id_jasa"));
        jasa.setNama_jasa(rs.getString("nama_jasa"));
        jasa.setHarga_pengiriman(rs.getInt("harga_pengiriman"));
        jasa.setEstimasi_waktu(rs.getString("estimasi_waktu"));
        return jasa;
    }

    public List<Jasa_Kirim> getAllJasaKirim() {
        List<Jasa_Kirim> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Jasa_Kirim\" ORDER BY harga_pengiriman ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToJasa(rs));
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getAllJasaKirim gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Jasa_Kirim getJasaKirimById(String idJasa) {
        String sql = "SELECT * FROM \"Jasa_Kirim\" WHERE id_jasa = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idJasa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToJasa(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getJasaKirimById gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertJasaKirim(Jasa_Kirim jasa) {
        String sql = "INSERT INTO \"Jasa_Kirim\" (id_jasa, nama_jasa, harga_pengiriman, estimasi_waktu) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = jasa.getId_jasa();
            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
            }

            ps.setString(1, id);
            ps.setString(2, jasa.getNama_jasa());
            ps.setInt(3, jasa.getHarga_pengiriman());
            ps.setString(4, jasa.getEstimasi_waktu());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] insertJasaKirim gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}