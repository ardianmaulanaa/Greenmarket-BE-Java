package com.greenmarket.controller;

import com.google.gson.Gson;
import com.greenmarket.model.Produk;
import com.greenmarket.service.ProdukService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProdukController", urlPatterns = {"/api/produk"})
public class ProdukController extends HttpServlet {
    

    private final ProdukService produkService = new ProdukService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // CORS - izinkan Next.js akses
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Ambil data dari Supabase via Service
        List<Produk> listProduk = produkService.dapatkanSemuaProduk();

        // Convert ke JSON dan kirim ke Next.js
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(listProduk));
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle preflight CORS request dari browser
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}