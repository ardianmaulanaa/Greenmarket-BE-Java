package com.greenmarket.service;

import com.greenmarket.dao.MetodePembayaranDAO;
import com.greenmarket.model.Metode_Pembayaran;

import java.util.List;

public class MetodePembayaranService {

    private final MetodePembayaranDAO metodePembayaranDAO = new MetodePembayaranDAO();

    public List<Metode_Pembayaran> getAllMetodePembayaran() {
        return metodePembayaranDAO.getAllMetodePembayaran();
    }

    public Metode_Pembayaran getMetodeById(String idMetode) {
        if (idMetode == null || idMetode.trim().isEmpty()) {
            return null;
        }

        return metodePembayaranDAO.getMetodeById(idMetode);
    }

    public boolean insertMetode(Metode_Pembayaran metode) {
        if (metode == null) return false;

        if (metode.getNama_metode() == null || metode.getNama_metode().trim().isEmpty()) {
            return false;
        }

        if (metode.getKode_metode() == null || metode.getKode_metode().trim().isEmpty()) {
            return false;
        }

        return metodePembayaranDAO.insertMetode(metode);
    }
}