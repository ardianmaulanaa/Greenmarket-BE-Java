package com.greenmarket.service;

import com.greenmarket.dao.AlamatDAO;
import com.greenmarket.model.Alamat;

import java.util.List;

public class AlamatService {

    private final AlamatDAO alamatDAO = new AlamatDAO();

    public List<Alamat> getAlamatByUser(int idUser) {
        return alamatDAO.getAlamatByUser(idUser);
    }

    public Alamat getAlamatById(String idAlamat) {
        if (idAlamat == null || idAlamat.trim().isEmpty()) {
            return null;
        }

        return alamatDAO.getAlamatById(idAlamat);
    }

    public boolean insertAlamat(Alamat alamat) {
        if (alamat == null) return false;

        if (alamat.getId_user() <= 0) return false;

        if (alamat.getNama_penerima() == null || alamat.getNama_penerima().trim().isEmpty()) {
            return false;
        }

        if (alamat.getNomor_hp() == null || alamat.getNomor_hp().trim().isEmpty()) {
            return false;
        }

        if (alamat.getAlamat_lengkap() == null || alamat.getAlamat_lengkap().trim().isEmpty()) {
            return false;
        }

        return alamatDAO.insertAlamat(alamat);
    }

    public boolean updateAlamat(Alamat alamat) {
        if (alamat == null) return false;

        if (alamat.getId_alamat() == null || alamat.getId_alamat().trim().isEmpty()) {
            return false;
        }

        return alamatDAO.updateAlamat(alamat);
    }

    public boolean deleteAlamat(String idAlamat) {
        if (idAlamat == null || idAlamat.trim().isEmpty()) {
            return false;
        }

        return alamatDAO.deleteAlamat(idAlamat);
    }
}