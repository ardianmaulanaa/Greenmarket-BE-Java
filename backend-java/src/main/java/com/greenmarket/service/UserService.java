package com.greenmarket.service;

import com.greenmarket.dao.UserDAO;
import com.greenmarket.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    // Login user
    public User login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            return null;
        }

        boolean passwordMatch = BCrypt.checkpw(password, user.getPassword());

        if (!passwordMatch) {
            return null;
        }

        // Supaya password tidak ikut dikirim ke frontend
        user.setPassword(null);

        return user;
    }

    // Register user baru
    public boolean register(User user) {
        if (user == null) {
            return false;
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return false;
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return false;
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return false;
        }

        // Cek email sudah dipakai atau belum
        if (userDAO.isEmailExists(user.getEmail())) {
            return false;
        }

        // Hash password sebelum masuk database
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        // Default role jika kosong
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("BUYER");
        } else {
            user.setRole(user.getRole().toUpperCase());
        }

        return userDAO.registerUser(user);
    }

    // Ambil user berdasarkan email
    public User getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        User user = userDAO.getUserByEmail(email);

        if (user != null) {
            user.setPassword(null);
        }

        return user;
    }

    // Ambil user berdasarkan id
    public User getUserById(int id) {
        if (id <= 0) {
            return null;
        }

        User user = userDAO.getUserById(id);

        if (user != null) {
            user.setPassword(null);
        }

        return user;
    }

    // Ambil semua user
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAllUsers();

        for (User user : users) {
            user.setPassword(null);
        }

        return users;
    }

    // Update user
    public boolean updateUser(User user) {
        if (user == null) {
            return false;
        }

        if (user.getId() <= 0) {
            return false;
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return false;
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return false;
        }

        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("BUYER");
        } else {
            user.setRole(user.getRole().toUpperCase());
        }

        return userDAO.updateUser(user);
    }

    // Update password
    public boolean updatePassword(int id, String oldPassword, String newPassword) {
        if (id <= 0) {
            return false;
        }

        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            return false;
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }

        User user = userDAO.getUserById(id);

        if (user == null) {
            return false;
        }

        boolean oldPasswordMatch = BCrypt.checkpw(oldPassword, user.getPassword());

        if (!oldPasswordMatch) {
            return false;
        }

        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        return userDAO.updatePassword(id, hashedNewPassword);
    }

    // Hapus user
    public boolean deleteUser(int id) {
        if (id <= 0) {
            return false;
        }

        return userDAO.deleteUser(id);
    }

    // Cek email sudah ada atau belum
    public boolean isEmailExists(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        return userDAO.isEmailExists(email);
    }
}