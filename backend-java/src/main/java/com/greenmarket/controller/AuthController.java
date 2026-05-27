package com.greenmarket.controller;

import com.google.gson.Gson;
import com.greenmarket.dao.UserDAO;
import com.greenmarket.model.User;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/auth/*")
public class AuthController extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();
    private final Gson gson = new Gson();

    // Untuk CORS supaya bisa diakses dari Next.js localhost:3000
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    setCorsHeaders(response);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    String path = request.getPathInfo();

    if (path == null || path.equals("/")) {
        Map<String, Object> data = new HashMap<>();
        data.put("availableEndpoints", new String[]{
                "GET /api/auth/check",
                "POST /api/auth/login",
                "POST /api/auth/register"
        });

        sendResponse(response, HttpServletResponse.SC_OK, true, "Auth controller aktif", data);
        return;
    }

    switch (path) {
        case "/check":
            Map<String, Object> data = new HashMap<>();
            data.put("status", "OK");
            data.put("controller", "AuthController");
            data.put("message", "Endpoint auth berhasil diakses");

            sendResponse(response, HttpServletResponse.SC_OK, true, "Backend auth aktif", data);
            break;

        default:
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Endpoint GET tidak ditemukan", null);
            break;
    }
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();

        if (path == null) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Endpoint tidak valid", null);
            return;
        }

        switch (path) {
            case "/login":
                handleLogin(request, response);
                break;

            case "/register":
                handleRegister(request, response);
                break;

            default:
                sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Endpoint tidak ditemukan", null);
                break;
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = readRequestBody(request);

        if (authRequest.email == null || authRequest.password == null) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Email dan password wajib diisi", null);
            return;
        }

        User user = userDAO.getUserByEmail(authRequest.email);

        if (user == null) {
            sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, false, "Email tidak ditemukan", null);
            return;
        }

        boolean passwordMatch = BCrypt.checkpw(authRequest.password, user.getPassword());

        if (!passwordMatch) {
            sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED, false, "Password salah", null);
            return;
        }

        // Jangan kirim password ke frontend
        user.setPassword(null);

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);

        sendResponse(response, HttpServletResponse.SC_OK, true, "Login berhasil", data);
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = readRequestBody(request);

        if (authRequest.username == null || authRequest.email == null || authRequest.password == null) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Username, email, dan password wajib diisi", null);
            return;
        }

        if (userDAO.isEmailExists(authRequest.email)) {
            sendResponse(response, HttpServletResponse.SC_CONFLICT, false, "Email sudah terdaftar", null);
            return;
        }

        String hashedPassword = BCrypt.hashpw(authRequest.password, BCrypt.gensalt());

        User user = new User();
        user.setUsername(authRequest.username);
        user.setEmail(authRequest.email);
        user.setPassword(hashedPassword);

        if (authRequest.role == null || authRequest.role.trim().isEmpty()) {
            user.setRole("BUYER");
        } else {
            user.setRole(authRequest.role.toUpperCase());
        }

        boolean success = userDAO.registerUser(user);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Register berhasil", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, false, "Register gagal", null);
        }
    }

    private AuthRequest readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        BufferedReader reader = request.getReader();

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return gson.fromJson(sb.toString(), AuthRequest.class);
    }

    private void sendResponse(
            HttpServletResponse response,
            int status,
            boolean success,
            String message,
            Object data
    ) throws IOException {

        response.setStatus(status);

        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", message);

        if (data != null) {
            result.put("data", data);
        }

        response.getWriter().write(gson.toJson(result));
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private static class AuthRequest {
        String username;
        String email;
        String password;
        String role;
    }
}