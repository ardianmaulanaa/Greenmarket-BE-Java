package com.greenmarket.model;

public class Wishlist {
    private String id_wishlist;
    private String id_user_buyer;
    private String id_produk;

    public Wishlist() {
    }

    public Wishlist(String id_wishlist, String id_user_buyer, String id_produk) {
        this.id_wishlist = id_wishlist;
        this.id_user_buyer = id_user_buyer;
        this.id_produk = id_produk;
    }

    public String getId_wishlist() {
        return id_wishlist;
    }

    public void setId_wishlist(String id_wishlist) {
        this.id_wishlist = id_wishlist;
    }

    public String getId_user_buyer() {
        return id_user_buyer;
    }

    public void setId_user_buyer(String id_user_buyer) {
        this.id_user_buyer = id_user_buyer;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }
}