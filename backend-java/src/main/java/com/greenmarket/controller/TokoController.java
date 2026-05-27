package com.greenmarket.controller;

import com.greenmarket.model.Toko;
import com.greenmarket.service.TokoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/stores/*")
public class TokoController extends BaseApiController {

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

        String userParam = request.getParameter("user");

        if (userParam == null || userParam.trim().isEmpty()) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user wajib dikirim", null);
            return;
        }

        try {
            int idUser = Integer.parseInt(userParam);
            Toko toko = tokoService.getTokoByUser(idUser);

            if (toko == null) {
                sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Toko tidak ditemukan", null);
                return;
            }

            sendResponse(response, HttpServletResponse.SC_OK, true, "Toko berhasil diambil", toko);

        } catch (NumberFormatException e) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user tidak valid", null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Toko toko = readRequestBody(request, Toko.class);
        boolean success = tokoService.insertToko(toko);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Toko berhasil dibuat", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Toko gagal dibuat", null);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Toko toko = readRequestBody(request, Toko.class);
        boolean success = tokoService.updateToko(toko);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Toko berhasil diupdate", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Toko gagal diupdate", null);
        }
    }
}