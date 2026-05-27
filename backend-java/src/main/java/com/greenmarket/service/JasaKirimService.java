package com.greenmarket.service;

import com.greenmarket.dao.JasaKirimDAO;
import com.greenmarket.model.Jasa_Kirim;

import java.util.List;

public class JasaKirimService {

    private final JasaKirimDAO jasaKirimDAO = new JasaKirimDAO();

    public List<Jasa_Kirim> getAllJasaKirim() {
        return jasaKirimDAO.getAllJasaKirim();
    }

    public Jasa_Kirim getJasaKirimById(String idJasa) {
        if (idJasa == null || idJasa.trim().isEmpty()) {
            return null;
        }

        return jasaKirimDAO.getJasaKirimById(idJasa);
    }

    public boolean insertJasaKirim(Jasa_Kirim jasa) {
        if (jasa == null) return false;

        if (jasa.getNama_jasa() == null || jasa.getNama_jasa().trim().isEmpty()) {
            return false;
        }

        if (jasa.getHarga_pengiriman() < 0) {
            return false;
        }

        return jasaKirimDAO.insertJasaKirim(jasa);
    }
}