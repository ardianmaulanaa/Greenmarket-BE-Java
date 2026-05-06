package com.greenmarket.controller;

import com.google.gson.Gson;
import com.greenmarket.service.UserService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/register")
public class RegisterController extends HttpServlet {
    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    // 1. Tambahkan doOptions untuk menangani Preflight Request dari Browser
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        setAccessControlHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 2. Setup Header
        setAccessControlHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        Map<String, Object> responseData = new HashMap<>();

        try {
            // 3. Baca Body JSON mentah
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            // 4. Ubah String JSON jadi Map
            Map<String, String> data = gson.fromJson(sb.toString(), Map.class);

            String username = (data != null) ? data.get("username") : null;
            String email = (data != null) ? data.get("email") : null;
            String password = (data != null) ? data.get("password") : null;

            // 5. Validasi & Service Logic
            if (username == null || email == null || password == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseData.put("status", "error");
                responseData.put("message", "Field tidak lengkap");
            } else {
                String result = userService.register(username, email, password);

                if ("Register berhasil".equals(result)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    responseData.put("status", "success");
                    responseData.put("message", result);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    responseData.put("status", "error");
                    responseData.put("message", result);
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseData.put("status", "error");
            responseData.put("message", "Server Error: " + e.getMessage());
        }

        out.print(gson.toJson(responseData));
        out.flush();
    }

    // Fungsi pembantu agar tidak menulis ulang header CORS
    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
    }
}