package com.greenmarket.controller;

import com.greenmarket.model.Keranjang;
import com.greenmarket.service.KeranjangService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/carts/*")
public class KeranjangController extends BaseApiController {

    private final KeranjangService keranjangService = new KeranjangService();

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
        String productParam = request.getParameter("product");

        if (userParam == null || userParam.trim().isEmpty()) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user wajib dikirim", null);
            return;
        }

        try {
            long idUser = Long.parseLong(userParam);

            if (productParam != null && !productParam.trim().isEmpty()) {
                boolean exists = keranjangService.isProdukInKeranjang(idUser, productParam);
                sendResponse(response, HttpServletResponse.SC_OK, true, "Status produk di keranjang berhasil dicek", exists);
                return;
            }

            List<Keranjang> list = keranjangService.getKeranjangByUser(idUser);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(list));

        } catch (NumberFormatException e) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user tidak valid", null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        Keranjang keranjang = readRequestBody(request, Keranjang.class);
        boolean success = keranjangService.addToKeranjang(keranjang);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Produk berhasil ditambahkan ke keranjang", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Produk gagal ditambahkan atau sudah ada di keranjang", null);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        String userParam = request.getParameter("user");
        String path = request.getPathInfo();

        if (userParam != null && !userParam.trim().isEmpty()) {
            try {
                long idUser = Long.parseLong(userParam);
                boolean success = keranjangService.clearKeranjangByUser(idUser);

                if (success) {
                    sendResponse(response, HttpServletResponse.SC_OK, true, "Keranjang user berhasil dikosongkan", null);
                } else {
                    sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Keranjang user gagal dikosongkan", null);
                }

                return;
            } catch (NumberFormatException e) {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user tidak valid", null);
                return;
            }
        }

        if (path == null || path.equals("/")) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID keranjang wajib dikirim", null);
            return;
        }

        String idKeranjang = path.substring(1);
        boolean success = keranjangService.deleteFromKeranjang(idKeranjang);

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Produk berhasil dihapus dari keranjang", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_NOT_FOUND, false, "Produk gagal dihapus dari keranjang", null);
        }
    }
}