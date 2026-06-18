package com.greenmarket.controller;

import com.greenmarket.model.Produk;
import com.greenmarket.service.ProdukService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import com.greenmarket.model.User;
import java.util.List;

@WebServlet("/api/products/*")
public class ProdukController extends BaseApiController {

    private final ProdukService produkService = new ProdukService();

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

        String kategori = request.getParameter("kategori");
        String seller = request.getParameter("seller");
        String search = request.getParameter("search");

        if (search != null && !search.trim().isEmpty()) {
            List<Produk> produkList = produkService.searchProduk(search);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(produkList));
            return;
        }

        if (kategori != null && !kategori.trim().isEmpty()) {
            List<Produk> produkList;

            if (kategori.contains(",")) {
                produkList = produkService.getProdukByKategoriList(kategori);
            } else {
                produkList = produkService.getProdukByKategori(kategori);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(produkList));
            return;
        }

        if (seller != null && !seller.trim().isEmpty()) {
            try {
                int idSeller = Integer.parseInt(seller);
                List<Produk> produkList = produkService.getProdukBySeller(idSeller);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(produkList));
                return;
            } catch (NumberFormatException e) {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID seller tidak valid", null);
                return;
            }
        }

        if (path == null || path.equals("/")) {
            List<Produk> produkList = produkService.getAllProduk();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(produkList));
            return;
        }

        String idProduk = path.substring(1);
        Produk produk = produkService.getProdukById(idProduk);

        if (produk == null) {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Produk tidak ditemukan", null);
            return;
        }

        sendResponse(response, HttpServletResponse.SC_OK, true, "Produk berhasil diambil", produk);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Produk produk = readRequestBody(request, Produk.class);

        boolean success = produkService.insertProduk(produk);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Produk berhasil ditambahkan", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Produk gagal ditambahkan", null);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Produk produk = readRequestBody(request, Produk.class);

        boolean success = produkService.updateProduk(produk);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Produk berhasil diupdate", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Produk gagal diupdate", null);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        // Authorization check: hanya Admin yang boleh hapus produk
        String requesterRole = request.getHeader("Authorization");
        User requester = new User();
        requester.setRole(requesterRole);
        User adminCheck = User.fromUser(requester);

        if (adminCheck == null || !adminCheck.canManageProducts()) {
            sendResponse(response, HttpServletResponse.SC_FORBIDDEN, false,
                    "Akses ditolak! Hanya Admin yang dapat menghapus produk.", null);
            return;
        }

        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID produk wajib dikirim", null);
            return;
        }

        String idProduk = path.substring(1);

        boolean success = produkService.deleteProduk(idProduk);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Produk berhasil dihapus", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Produk gagal dihapus", null);
        }
    }
}