package com.greenmarket.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.greenmarket.model.Transaksi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MidTransService {

    private final Gson gson = new Gson();
    private static final String SNAP_URL = "https://app.sandbox.midtrans.com/snap/v1/transactions";

    public String createSnapToken(Transaksi transaksi) throws IOException {
        String serverKey = getServerKey();

        if (serverKey == null || serverKey.trim().isEmpty()) {
            throw new IOException("Midtrans server key belum diatur");
        }

        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", transaksi.getId_transaksi());
        transactionDetails.put("gross_amount", transaksi.getTotal_harga());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("transaction_details", transactionDetails);

        // Midtrans Sandbox QRIS untuk testing / tugas kuliah
        requestBody.put("enabled_payments", Arrays.asList("other_qris"));

        String jsonBody = gson.toJson(requestBody);

        URL url = new URL(SNAP_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        String auth = Base64.getEncoder()
                .encodeToString((serverKey + ":").getBytes(StandardCharsets.UTF_8));

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Basic " + auth);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        responseCode >= 200 && responseCode < 300
                                ? conn.getInputStream()
                                : conn.getErrorStream(),
                        StandardCharsets.UTF_8));

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        conn.disconnect();

        if (responseCode < 200 || responseCode >= 300) {
            throw new IOException("Midtrans error: " + response);
        }

        JsonObject json = gson.fromJson(response.toString(), JsonObject.class);

        if (json != null && json.has("token")) {
            return json.get("token").getAsString();
        }

        return null;
    }

    private String getServerKey() throws IOException {
        String serverKey = System.getenv("MIDTRANS_SERVER_KEY");

        if (serverKey != null && !serverKey.trim().isEmpty()) {
            return serverKey;
        }

        serverKey = System.getProperty("MIDTRANS_SERVER_KEY");

        if (serverKey != null && !serverKey.trim().isEmpty()) {
            return serverKey;
        }

        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("midtrans.properties")) {

            if (inputStream == null) {
                return null;
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            return properties.getProperty("midtrans.server.key");
        }
    }
}