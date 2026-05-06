package com.greenmarket.dao;
import com.greenmarket.model.User;
import com.greenmarket.util.DBConnection;
import java.sql.*;

public class UserDAO {

    // Login logic: findUnique email
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM \"User\" WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Register logic: create user
    public boolean registerUser(User user) {
    // Tambah cast ::\"Role\" karena kolom role bertipe ENUM di PostgreSQL
    String sql = "INSERT INTO \"User\" (username, email, password, role) VALUES (?, ?, ?, ?::\"Role\")";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, "BUYER");

        int rowsAffected = ps.executeUpdate();
        System.out.println("[DEBUG] Rows inserted: " + rowsAffected);
        return rowsAffected > 0;

    } catch (SQLException e) {
        System.err.println("[ERROR] registerUser gagal: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
}