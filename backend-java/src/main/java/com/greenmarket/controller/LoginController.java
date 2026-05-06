package com.greenmarket.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greenmarket.model.User;
import com.greenmarket.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/login")
public class LoginController extends HttpServlet {
    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        setAccessControlHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        setAccessControlHeaders(response);
        response.setContentType("application/json");
        
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            Type mapType = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> data = gson.fromJson(sb.toString(), mapType);
            
            String email = data.get("email");
            String password = data.get("password");

            User user = userService.login(email, password);

            Map<String, Object> responseData = new HashMap<>();
            if (user != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                responseData.put("status", "success");
                responseData.put("message", "Login berhasil!");
                responseData.put("user", user); // Mengirim objek user lengkap
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                responseData.put("status", "error");
                responseData.put("message", "Email atau password salah.");
            }

            response.getWriter().print(gson.toJson(responseData));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> errorData = new HashMap<>();
            errorData.put("message", "Server error: " + e.getMessage());
            response.getWriter().print(gson.toJson(errorData));
        }
    }

    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
    }
}