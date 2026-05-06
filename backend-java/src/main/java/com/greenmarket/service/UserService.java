package com.greenmarket.service;
import com.greenmarket.dao.UserDAO;
import com.greenmarket.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public String register(String username, String email, String password) {
        // 1. Cek Field Lengkap
        if (username == null || email == null || password == null) {
            return "Field tidak lengkap";
        }
        // 2. Cek Email Terdaftar
        if (userDAO.getUserByEmail(email) != null) {
            return "Email sudah terdaftar";
        }
        // 3. Hash Password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        
        // 4. Create User
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);

        // 5. Simpan ke DB & cek hasilnya
        boolean success = userDAO.registerUser(newUser);
        if (!success) {
            return "Gagal menyimpan user ke database";
        }
        return "Register berhasil";
    }

    public User login(String email, String password) {
    User user = userDAO.getUserByEmail(email);
    if (user == null) return null;
    
    try {
        String storedHash = user.getPassword();
        
        // Node.js pakai prefix $2b$, jBCrypt Java hanya bisa baca $2a$
        // Konversi prefix agar kompatibel
        if (storedHash != null && storedHash.startsWith("$2b$")) {
            storedHash = "$2a$" + storedHash.substring(4);
        }
        
        if (BCrypt.checkpw(password, storedHash)) {
            return user;
        }
    } catch (IllegalArgumentException e) {
        // Fallback untuk plain text (user manual dari Supabase)
        if (password.equals(user.getPassword())) {
            return user;
        }
    }
    
    return null;
}


}