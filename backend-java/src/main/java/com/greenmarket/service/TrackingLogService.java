package com.greenmarket.service;

import com.greenmarket.dao.TrackingLogDAO;
import com.greenmarket.model.TrackingLog;

import java.util.List;

public class TrackingLogService {

    private final TrackingLogDAO trackingLogDAO = new TrackingLogDAO();

    public boolean insertTrackingLog(TrackingLog log) {
        if (log == null) return false;

        if (log.getId_transaksi() == null || log.getId_transaksi().trim().isEmpty()) {
            return false;
        }

        if (log.getStatus() == null || log.getStatus().trim().isEmpty()) {
            return false;
        }

        return trackingLogDAO.insertTrackingLog(log);
    }

    public List<TrackingLog> getTrackingByTransaksi(String idTransaksi) {
        if (idTransaksi == null || idTransaksi.trim().isEmpty()) {
            return null;
        }

        return trackingLogDAO.getTrackingByTransaksi(idTransaksi);
    }
}