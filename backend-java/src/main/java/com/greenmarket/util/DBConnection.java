package com.greenmarket.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // 1. URL harus diawali jdbc:postgresql:// dan tanpa kredensial di dalamnya
    private static final String URL = "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres";
    
    // 2. Username adalah bagian sebelum tanda titik dua (:) di string kamu
    private static final String USER = "postgres.iobibwvdnejxhesekrne";
    
    // 3. Password adalah 'impal2026infoloker' (sesuai string terbaru kamu)
    private static final String PASS = "impal2026infoloker";

    public static Connection getConnection() throws SQLException {
        try {
            // Memanggil driver PostgreSQL
            Class.forName("org.postgresql.Driver");
            
            // Mencoba melakukan koneksi
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL tidak ditemukan! Pastikan file .jar ada di folder lib Tomcat. Error: " + e.getMessage());
        }
    }
}