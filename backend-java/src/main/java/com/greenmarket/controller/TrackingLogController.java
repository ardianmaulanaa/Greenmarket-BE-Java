package com.greenmarket.controller;

import com.greenmarket.model.TrackingLog;
import com.greenmarket.service.TrackingLogService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/tracking-logs/*")
public class TrackingLogController extends BaseApiController {

    private final TrackingLogService trackingLogService = new TrackingLogService();

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

        List<TrackingLog> list = trackingLogService.getTrackingByTransaksi(transaksiParam);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(list));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        TrackingLog log = readRequestBody(request, TrackingLog.class);
        boolean success = trackingLogService.insertTrackingLog(log);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Tracking log berhasil ditambahkan", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Tracking log gagal ditambahkan", null);
        }
    }
}