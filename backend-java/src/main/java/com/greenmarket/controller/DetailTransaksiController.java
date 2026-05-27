package com.greenmarket.controller;

import com.greenmarket.model.Detail_Transaksi;
import com.greenmarket.service.DetailTransaksiService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/transaction-details/*")
public class DetailTransaksiController extends BaseApiController {

    private final DetailTransaksiService detailTransaksiService = new DetailTransaksiService();

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        String transaksiParam = request.getParameter("transaksi");

        if (transaksiParam == null || transaksiParam.trim().isEmpty()) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID transaksi wajib dikirim", null);
            return;
        }

        List<Detail_Transaksi> detailList = detailTransaksiService.getDetailByTransaksi(transaksiParam);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(detailList));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Detail_Transaksi detail = readRequestBody(request, Detail_Transaksi.class);

        boolean success = detailTransaksiService.insertDetailTransaksi(detail);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Detail transaksi berhasil ditambahkan", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Detail transaksi gagal ditambahkan", null);
        }
    }
}