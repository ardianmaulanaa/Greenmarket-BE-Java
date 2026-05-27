package com.greenmarket.controller;

import com.greenmarket.model.KategoriProduk;
import com.greenmarket.service.KategoriProdukService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/categories/*")
public class KategoriProdukController extends BaseApiController {

    private final KategoriProdukService kategoriProdukService = new KategoriProdukService();

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
            List<KategoriProduk> kategoriList = kategoriProdukService.getAllKategori();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(kategoriList));
            return;
        }

        String idKategori = path.substring(1);
        KategoriProduk kategori = kategoriProdukService.getKategoriById(idKategori);

        if (kategori == null) {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Kategori tidak ditemukan", null);
            return;
        }

        sendResponse(response, HttpServletResponse.SC_OK, true, "Kategori berhasil diambil", kategori);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        KategoriProduk kategori = readRequestBody(request, KategoriProduk.class);

        boolean success = kategoriProdukService.insertKategori(kategori);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Kategori berhasil ditambahkan", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Kategori gagal ditambahkan", null);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        KategoriProduk kategori = readRequestBody(request, KategoriProduk.class);

        boolean success = kategoriProdukService.updateKategori(kategori);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Kategori berhasil diupdate", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Kategori gagal diupdate", null);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID kategori wajib dikirim", null);
            return;
        }

        String idKategori = path.substring(1);

        boolean success = kategoriProdukService.deleteKategori(idKategori);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Kategori berhasil dihapus", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Kategori gagal dihapus", null);
        }
    }
}