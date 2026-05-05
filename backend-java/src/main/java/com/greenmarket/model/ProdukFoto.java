package com.greenmarket.model;

public class ProdukFoto {
    private String id_foto;
    private String id_produk;
    private String url_foto;

    public ProdukFoto() {
    }

    public ProdukFoto(String id_foto, String id_produk, String url_foto) {
        this.id_foto = id_foto;
        this.id_produk = id_produk;
        this.url_foto = url_foto;
    }

    public String getId_foto() {
        return id_foto;
    }

    public void setId_foto(String id_foto) {
        this.id_foto = id_foto;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }
}