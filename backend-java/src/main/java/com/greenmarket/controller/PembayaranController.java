package com.greenmarket.controller;

import com.greenmarket.model.Pembayaran;
import com.greenmarket.service.PembayaranService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/payments/*")
public class PembayaranController extends BaseApiController {

    private final PembayaranService pembayaranService = new PembayaranService();

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        String idTransaksi = request.getParameter("transaksi");

        if (idTransaksi == null || idTransaksi.trim().isEmpty()) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID transaksi wajib dikirim", null);
            return;
        }

        Pembayaran pembayaran = pembayaranService.getPembayaranByTransaksi(idTransaksi);

        if (pembayaran == null) {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Pembayaran tidak ditemukan", null);
            return;
        }

        sendResponse(response, HttpServletResponse.SC_OK, true, "Pembayaran berhasil diambil", pembayaran);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Pembayaran pembayaran = readRequestBody(request, Pembayaran.class);
        boolean success = pembayaranService.createPembayaran(pembayaran);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Pembayaran berhasil dibuat", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Pembayaran gagal dibuat", null);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Pembayaran pembayaran = readRequestBody(request, Pembayaran.class);

        boolean success = pembayaranService.updateStatusPembayaran(
                pembayaran.getId_pembayaran(),
                pembayaran.getStatus_pembayaran()
        );

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Status pembayaran berhasil diupdate", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Status pembayaran gagal diupdate", null);
        }
    }
}