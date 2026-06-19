package com.greenmarket.dao;

import com.greenmarket.model.User;
import com.greenmarket.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    // Mapping ResultSet ke object User
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreatedAt(rs.getTimestamp("createdAt"));
        user.setRole(rs.getString("role"));

        return user;
    }

    // Login logic: cari user berdasarkan email
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM \"User\" WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getUserByEmail gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Cari user berdasarkan id
    public User getUserById(int id) {
        String sql = "SELECT * FROM \"User\" WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getUserById gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // Ambil semua user
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM \"User\" ORDER BY id ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getAllUsers gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return users;
    }

    // Register user baru
    public boolean registerUser(User user) {
        String sql = "INSERT INTO \"User\" (username, email, password, role) VALUES (?, ?, ?, ?::\"Role\")";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            String role = user.getRole();

            // Kalau role kosong, default jadi BUYER
            if (role == null || role.trim().isEmpty()) {
                role = "BUYER";
            }

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, role);

            int rowsAffected = ps.executeUpdate();
            System.out.println("[DEBUG] Rows inserted: " + rowsAffected);

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] registerUser gagal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Update data user tanpa password
    public boolean updateUser(User user) {
        String sql = "UPDATE \"User\" SET username = ?, email = ?, role = ?::\"Role\" WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] updateUser gagal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Update password user
    public boolean updatePassword(int id, String newPassword) {
        String sql = "UPDATE \"User\" SET password = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, id);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] updatePassword gagal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Hapus user (cascade manual untuk hindari ON DELETE RESTRICT)
    public boolean deleteUser(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            // 1. Hapus Detail_Transaksi untuk semua produk seller ini
            String sqlDetail = "DELETE FROM \"Detail_Transaksi\" WHERE id_produk IN " +
                    "(SELECT id_produk FROM \"Produk\" WHERE id_user_seller = ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // 2. Hapus Keranjang untuk semua produk seller ini
            String sqlKeranjang = "DELETE FROM \"Keranjang\" WHERE id_produk IN " +
                    "(SELECT id_produk FROM \"Produk\" WHERE id_user_seller = ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlKeranjang)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // 3. Hapus semua produk seller ini (karena Produk ON DELETE RESTRICT ke User)
            String sqlProduk = "DELETE FROM \"Produk\" WHERE id_user_seller = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlProduk)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // 4. Baru hapus user (Toko sudah ON DELETE CASCADE)
            String sql = "DELETE FROM \"User\" WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] deleteUser gagal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Cek apakah email sudah terdaftar
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM \"User\" WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] isEmailExists gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean upgradeUserRole(int id) {
        String sql = "UPDATE \"User\" SET role = ?::\"Role\" WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "SELLER");
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] upgradeUserRole gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}