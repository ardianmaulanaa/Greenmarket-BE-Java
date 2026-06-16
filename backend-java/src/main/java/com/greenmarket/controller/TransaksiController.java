package com.greenmarket.controller;

import com.greenmarket.model.Transaksi;
import com.greenmarket.model.CheckoutRequest;
import com.greenmarket.model.CheckoutItem;
import com.greenmarket.model.Detail_Transaksi;
import com.greenmarket.service.TransaksiService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@WebServlet({ "/api/transactions/*", "/api/transaksi/*" })
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
        String sellerParam = request.getParameter("seller");

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

        if (sellerParam != null && !sellerParam.trim().isEmpty()) {
            try {
                int idSeller = Integer.parseInt(sellerParam);
                List<Transaksi> list = transaksiService.getTransaksiBySeller(idSeller);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(list));
                return;

            } catch (NumberFormatException e) {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID seller tidak valid", null);
                return;
            }
        }

        if (path != null && path.startsWith("/user/")) {
            try {
                int idUser = Integer.parseInt(path.substring("/user/".length()));
                List<Transaksi> list = transaksiService.getTransaksiByUser(idUser);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(list));
                return;

            } catch (NumberFormatException e) {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID user tidak valid", null);
                return;
            }
        }

        if (path != null && path.startsWith("/seller/")) {
            try {
                int idSeller = Integer.parseInt(path.substring("/seller/".length()));
                List<Transaksi> list = transaksiService.getTransaksiBySeller(idSeller);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(list));
                return;

            } catch (NumberFormatException e) {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "ID seller tidak valid", null);
                return;
            }
        }

        if (path == null || path.equals("/")) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false,
                    "Parameter user atau ID transaksi wajib dikirim", null);
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

        CheckoutRequest req = readRequestBody(request, CheckoutRequest.class);

        Transaksi transaksi = new Transaksi();
        transaksi.setId_user(req.getId_user());
        transaksi.setId_alamat(req.getId_alamat());
        transaksi.setId_jasa_kirim(req.getId_jasa_kirim());
        transaksi.setId_metode_pembayaran(req.getId_metode_pembayaran());
        transaksi.setStatus_transaksi(req.getStatus_transaksi());

        java.util.ArrayList<Detail_Transaksi> details = new java.util.ArrayList<>();
        if (req.getItems() != null) {
            for (CheckoutItem item : req.getItems()) {
                Detail_Transaksi detail = new Detail_Transaksi();
                detail.setId_produk(item.getId_produk());
                detail.setKuantitas(item.getKuantitas());
                details.add(detail);
            }
        }
        transaksi.setDetail_transaksi(details);

        boolean success = transaksiService.createTransaksiMultiProduk(transaksi);

        if (success) {
            Map<String, Object> data = new HashMap<>();
            data.put("transaksi", transaksi);
            data.put("midtransToken", null);

            sendResponse(response, HttpServletResponse.SC_CREATED, true, "Transaksi berhasil dibuat", data);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Transaksi gagal dibuat", null);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepareJsonResponse(response);

        String path = request.getPathInfo();

        if (path != null && path.endsWith("/konfirmasi-kirim")) {
            String idTransaksi = path.substring(1, path.length() - "/konfirmasi-kirim".length());
            KonfirmasiKirimRequest body = readRequestBody(request, KonfirmasiKirimRequest.class);

            boolean success = transaksiService.konfirmasiKirim(idTransaksi, body.id_seller);

            if (success) {
                sendResponse(response, HttpServletResponse.SC_OK, true, "Pesanan berhasil dikonfirmasi dikirim", null);
            } else {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Gagal konfirmasi kirim", null);
            }

            return;
        }

        Transaksi transaksi = readRequestBody(request, Transaksi.class);

        boolean success = transaksiService.updateStatusTransaksi(
                transaksi.getId_transaksi(),
                transaksi.getStatus_transaksi());

        if (success) {
            sendResponse(response, HttpServletResponse.SC_OK, true, "Status transaksi berhasil diupdate", null);
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, false, "Status transaksi gagal diupdate", null);
        }
    }

    private static class KonfirmasiKirimRequest {
        int id_seller;
    }
}