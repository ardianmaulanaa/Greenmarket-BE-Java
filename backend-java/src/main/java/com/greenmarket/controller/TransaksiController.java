package com.greenmarket.controller;

import com.greenmarket.model.Transaksi;
import com.greenmarket.service.TransaksiService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/transactions/*")
public class TransaksiController extends BaseApiController {

    private final TransaksiService transaksiService = new TransaksiService();

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
        String userParam = request.getParameter("user");

        if (userParam != null && !userParam.trim().isEmpty()) {
            try {
                int idUser = Integer.parseInt(userParam);
                List<Transaksi> list = transaksiService.getTransaksiByUser(idUser);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(list));
                return;

            } catch (NumberFormatException e) {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user tidak valid", null);
                return;
            }
        }

        if (path == null || path.equals("/")) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Parameter user atau ID transaksi wajib dikirim", null);
            return;
        }

        String idTransaksi = path.substring(1);
        Transaksi transaksi = transaksiService.getTransaksiById(idTransaksi);

        if (transaksi == null) {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Transaksi tidak ditemukan", null);
            return;
        }

        sendResponse(response, HttpServletResponse.SC_OK, true, "Transaksi berhasil diambil", transaksi);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Transaksi transaksi = readRequestBody(request, Transaksi.class);
        boolean success = transaksiService.createTransaksi(transaksi);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Transaksi berhasil dibuat", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Transaksi gagal dibuat", null);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Transaksi transaksi = readRequestBody(request, Transaksi.class);

        boolean success = transaksiService.updateStatusTransaksi(
                transaksi.getId_transaksi(),
                transaksi.getStatus_transaksi()
        );

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Status transaksi berhasil diupdate", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Status transaksi gagal diupdate", null);
        }
    }
}