package com.greenmarket.dao;

import com.greenmarket.model.TrackingLog;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrackingLogDAO {

    private TrackingLog mapResultSetToTrackingLog(ResultSet rs) throws SQLException {
        TrackingLog log = new TrackingLog();
        log.setId_log(rs.getString("id_log"));
        log.setId_transaksi(rs.getString("id_transaksi"));
        log.setStatus(rs.getString("status"));
        log.setWaktu(rs.getTimestamp("waktu"));
        return log;
    }

    public boolean insertTrackingLog(TrackingLog log) {
        String sql = "INSERT INTO \"TrackingLog\" (id_log, id_transaksi, status, waktu) VALUES (?, ?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = log.getId_log();
            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
            }

            ps.setString(1, id);
            ps.setString(2, log.getId_transaksi());
            ps.setString(3, log.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] insertTrackingLog gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public List<TrackingLog> getTrackingByTransaksi(String idTransaksi) {
        List<TrackingLog> list = new ArrayList<>();
        String sql = "SELECT * FROM \"TrackingLog\" WHERE id_transaksi = ? ORDER BY waktu ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idTransaksi);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToTrackingLog(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getTrackingByTransaksi gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }
}