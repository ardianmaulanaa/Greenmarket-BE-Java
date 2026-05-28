package com.greenmarket.model;

public class Detail_Transaksi {
    private String id_detail;
    private String id_transaksi;
    private String id_produk;
    private int kuantitas;
    private int harga_satuan;
    private int subtotal;
    private Produk produk;

    public Detail_Transaksi() {
    }

    public Detail_Transaksi(String id_detail, String id_transaksi, String id_produk, int kuantitas, int harga_satuan, int subtotal) {
        this.id_detail = id_detail;
        this.id_transaksi = id_transaksi;
        this.id_produk = id_produk;
        this.kuantitas = kuantitas;
        this.harga_satuan = harga_satuan;
        this.subtotal = subtotal;
    }

    public String getId_detail() {
        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public int getHarga_satuan() {
        return harga_satuan;
    }

    public void setHarga_satuan(int harga_satuan) {
        this.harga_satuan = harga_satuan;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }
}