package com.greenmarket.controller;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseApiController extends HttpServlet {

    protected final Gson gson = new Gson();

    protected void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    protected void prepareJsonResponse(HttpServletResponse response) {
        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    protected <T> T readRequestBody(HttpServletRequest request, Class<T> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        BufferedReader reader = request.getReader();

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return gson.fromJson(sb.toString(), clazz);
    }

    protected void sendResponse(
            HttpServletResponse response,
            int status,
            boolean success,
            String message,
            Object data
    ) throws IOException {

        response.setStatus(status);

        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", message);

        if (data != null) {
            result.put("data", data);
        }

        response.getWriter().write(gson.toJson(result));
    }
}