package com.greenmarket.controller;

import com.greenmarket.model.User;
import com.greenmarket.model.Toko;
import com.greenmarket.service.UserService;
import com.greenmarket.service.TokoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/users/*")
public class UserController extends BaseApiController {

    private final UserService userService = new UserService();
    private final TokoService tokoService = new TokoService();

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            String role = request.getParameter("role");

            java.util.List<User> users = userService.getAllUsers();

            if (role != null && !role.trim().isEmpty()) {
                String selectedRole = role.trim().toUpperCase();

                users.removeIf(user -> user.getRole() == null || !user.getRole().equalsIgnoreCase(selectedRole));
            }

            sendResponse(response, HttpServletResponse.SC_OK, true, "User berhasil diambil", users);
            return;
        }

        try {
            int id = Integer.parseInt(path.substring(1));
            User user = userService.getUserById(id);

            if (user == null) {
                sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "User tidak ditemukan", null);
                return;
            }

            user.setPassword(null);
            sendResponse(response, HttpServletResponse.SC_OK, true, "User berhasil diambil", user);

        } catch (NumberFormatException e) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user tidak valid", null);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user wajib dikirim", null);
            return;
        }

        try {
            if (path.startsWith("/upgrade/")) {
                int id = Integer.parseInt(path.substring("/upgrade/".length()));
                Toko toko = readRequestBody(request, Toko.class);

                boolean success = userService.upgradeUserRole(id);

                if (success) {
                    if (toko != null && toko.getNama_toko() != null && !toko.getNama_toko().trim().isEmpty()) {
                        toko.setId_user(id);
                        tokoService.upsertToko(toko);
                    }

                    User updatedUser = userService.getUserById(id);
                    sendResponse(response, HttpServletResponse.SC_OK, true, "User berhasil menjadi seller",
                            updatedUser);
                } else {
                    sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Gagal upgrade user", null);
                }

                return;
            }

            int id = Integer.parseInt(path.substring(1));
            User user = readRequestBody(request, User.class);
            user.setId(id);

            boolean success = userService.updateUser(user);

            if (success) {
                User updatedUser = userService.getUserById(id);
                sendResponse(response, HttpServletResponse.SC_OK, true, "User berhasil diupdate", updatedUser);
            } else {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "User gagal diupdate", null);
            }

        } catch (NumberFormatException e) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user tidak valid", null);
        }
    }
}
