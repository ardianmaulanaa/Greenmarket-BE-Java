package com.greenmarket.service;

import com.greenmarket.dao.TokoDAO;
import com.greenmarket.model.Toko;

public class TokoService {

    private final TokoDAO tokoDAO = new TokoDAO();

    public Toko getTokoByUser(int idUser) {
        if (idUser <= 0) {
            return null;
        }

        return tokoDAO.getTokoByUser(idUser);
    }

    public boolean insertToko(Toko toko) {
        if (toko == null) return false;

        if (toko.getId_user() <= 0) {
            return false;
        }

        if (toko.getNama_toko() == null || toko.getNama_toko().trim().isEmpty()) {
            return false;
        }

        return tokoDAO.insertToko(toko);
    }

    public boolean updateToko(Toko toko) {
        if (toko == null) return false;

        if (toko.getId_toko() == null || toko.getId_toko().trim().isEmpty()) {
            return false;
        }

        return tokoDAO.updateToko(toko);
    }
}