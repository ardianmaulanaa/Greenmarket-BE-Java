package com.greenmarket.controller;

import com.greenmarket.model.Jasa_Kirim;
import com.greenmarket.service.JasaKirimService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/shipping-services/*")
public class JasaKirimController extends BaseApiController {

    private final JasaKirimService jasaKirimService = new JasaKirimService();

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
            List<Jasa_Kirim> list = jasaKirimService.getAllJasaKirim();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(list));
            return;
        }

        String idJasa = path.substring(1);
        Jasa_Kirim jasa = jasaKirimService.getJasaKirimById(idJasa);

        if (jasa == null) {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Jasa kirim tidak ditemukan", null);
            return;
        }

        sendResponse(response, HttpServletResponse.SC_OK, true, "Jasa kirim berhasil diambil", jasa);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Jasa_Kirim jasa = readRequestBody(request, Jasa_Kirim.class);
        boolean success = jasaKirimService.insertJasaKirim(jasa);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Jasa kirim berhasil ditambahkan", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Jasa kirim gagal ditambahkan", null);
        }
    }
}