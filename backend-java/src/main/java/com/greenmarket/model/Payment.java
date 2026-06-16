package com.greenmarket.model;

import java.sql.Timestamp;

public interface Payment {
    String getId_pembayaran();
    void setId_pembayaran(String id);

    String getId_transaksi();
    void setId_transaksi(String id);

    String getStatus_pembayaran();
    void setStatus_pembayaran(String status);

    Timestamp getTanggal_pembayaran();
    void setTanggal_pembayaran(Timestamp tanggal);

    boolean isConfirmed();
    String getPaymentType();
}
