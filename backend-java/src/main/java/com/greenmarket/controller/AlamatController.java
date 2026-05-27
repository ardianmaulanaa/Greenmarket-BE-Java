package com.greenmarket.controller;

import com.greenmarket.model.Alamat;
import com.greenmarket.service.AlamatService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/addresses/*")
public class AlamatController extends BaseApiController {

    private final AlamatService alamatService = new AlamatService();

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
        String userIdParam = request.getParameter("user");

        if (userIdParam != null && !userIdParam.trim().isEmpty()) {
            try {
                int idUser = Integer.parseInt(userIdParam);
                List<Alamat> alamatList = alamatService.getAlamatByUser(idUser);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(alamatList));
                return;
            } catch (NumberFormatException e) {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user tidak valid", null);
                return;
            }
        }

        if (path != null && !path.equals("/")) {
            String idAlamat = path.substring(1);
            Alamat alamat = alamatService.getAlamatById(idAlamat);

            if (alamat == null) {
                sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Alamat tidak ditemukan", null);
                return;
            }

            sendResponse(response, HttpServletResponse.SC_OK, true, "Alamat berhasil diambil", alamat);
            return;
        }

        sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Parameter user atau ID alamat wajib dikirim", null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Alamat alamat = readRequestBody(request, Alamat.class);

        boolean success = alamatService.insertAlamat(alamat);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Alamat berhasil ditambahkan", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Alamat gagal ditambahkan", null);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Alamat alamat = readRequestBody(request, Alamat.class);

        boolean success = alamatService.updateAlamat(alamat);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Alamat berhasil diupdate", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Alamat gagal diupdate", null);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID alamat wajib dikirim", null);
            return;
        }

        String idAlamat = path.substring(1);

        boolean success = alamatService.deleteAlamat(idAlamat);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Alamat berhasil dihapus", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Alamat gagal dihapus", null);
        }
    }
}