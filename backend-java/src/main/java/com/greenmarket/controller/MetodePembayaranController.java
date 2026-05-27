package com.greenmarket.controller;

import com.greenmarket.model.Metode_Pembayaran;
import com.greenmarket.service.MetodePembayaranService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/payment-methods/*")
public class MetodePembayaranController extends BaseApiController {

    private final MetodePembayaranService metodePembayaranService = new MetodePembayaranService();

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
            List<Metode_Pembayaran> list = metodePembayaranService.getAllMetodePembayaran();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(list));
            return;
        }

        String idMetode = path.substring(1);
        Metode_Pembayaran metode = metodePembayaranService.getMetodeById(idMetode);

        if (metode == null) {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Metode pembayaran tidak ditemukan", null);
            return;
        }

        sendResponse(response, HttpServletResponse.SC_OK, true, "Metode pembayaran berhasil diambil", metode);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Metode_Pembayaran metode = readRequestBody(request, Metode_Pembayaran.class);
        boolean success = metodePembayaranService.insertMetode(metode);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Metode pembayaran berhasil ditambahkan", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Metode pembayaran gagal ditambahkan", null);
        }
    }
}