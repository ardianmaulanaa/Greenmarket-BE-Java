package com.greenmarket.dao;

import com.greenmarket.util.DBConnection;
import com.greenmarket.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransaksiDAO implements ITransaksiDAO {

    private Transaksi mapResultSetToTransaksi(ResultSet rs) throws SQLException {
        Transaksi transaksi = new Transaksi();
        transaksi.setId_transaksi(rs.getString("id_transaksi"));
        transaksi.setId_user(rs.getInt("id_user"));
        transaksi.setId_alamat(rs.getString("id_alamat"));
        transaksi.setId_jasa_kirim(rs.getString("id_jasa_kirim"));
        transaksi.setId_metode_pembayaran(rs.getString("id_metode_pembayaran"));
        transaksi.setStatus_transaksi(rs.getString("status_transaksi"));
        transaksi.setTanggal_transaksi(rs.getTimestamp("tanggal_transaksi"));
        transaksi.setTotal_harga(rs.getInt("total_harga"));
        return transaksi;
    }

    private Produk mapProduk(ResultSet rs) throws SQLException {
        Produk produk = new Produk();

        produk.setId_produk(rs.getString("id_produk"));
        produk.setId_user_seller(rs.getInt("id_user_seller"));
        produk.setId_kategori(rs.getString("id_kategori"));
        produk.setNama_produk(rs.getString("nama_produk"));
        produk.setDeskripsi(rs.getString("deskripsi"));
        produk.setHarga(rs.getInt("harga"));
        produk.setStok(rs.getInt("stok"));
        produk.setStatus_produk(rs.getString("status_produk"));
        produk.setCreated_at(rs.getTimestamp("created_at"));
        produk.setFoto_produk(rs.getString("foto_produk"));
        produk.setKonten_deskripsi(rs.getString("konten_deskripsi"));
        produk.setCatatan_penjual(rs.getString("catatan_penjual"));

        return produk;
    }

    private void mapProdukRelations(ResultSet rs, Produk produk) throws SQLException {
        try {
            String namaKategori = rs.getString("nama_kategori");

            if (namaKategori != null) {
                KategoriProduk kategori = new KategoriProduk();
                kategori.setId_kategori(produk.getId_kategori());
                kategori.setNama_kategori(namaKategori);
                produk.setKategori(kategori);
            }
        } catch (SQLException ignored) {
        }

        try {
            int sellerId = rs.getInt("seller_id");

            if (!rs.wasNull()) {
                User seller = new User();
                seller.setId(sellerId);
                seller.setUsername(rs.getString("seller_username"));
                seller.setEmail(rs.getString("seller_email"));
                seller.setPassword(null);
                seller.setRole(rs.getString("seller_role"));

                String idToko = rs.getString("id_toko");

                if (idToko != null) {
                    Toko toko = new Toko();
                    toko.setId_toko(idToko);
                    toko.setId_user(sellerId);
                    toko.setNama_toko(rs.getString("nama_toko"));
                    toko.setEmail_bisnis(rs.getString("email_bisnis"));
                    toko.setAlamat_toko(rs.getString("alamat_toko"));
                    toko.setCreated_at(rs.getTimestamp("toko_created_at"));
                    seller.setToko(toko);
                }

                produk.setSeller(seller);
            }
        } catch (SQLException ignored) {
        }
    }

    private void loadTransaksiRelations(Connection conn, Transaksi transaksi) throws SQLException {
        transaksi.setDetail_transaksi(getDetailTransaksiByTransaksi(conn, transaksi.getId_transaksi()));
        transaksi.setAlamat(getAlamatById(conn, transaksi.getId_alamat()));
        transaksi.setJasa_kirim(getJasaKirimById(conn, transaksi.getId_jasa_kirim()));
        transaksi.setMetode_pembayaran(getMetodePembayaranById(conn, transaksi.getId_metode_pembayaran()));
        transaksi.setPembayaran(getPembayaranByTransaksi(conn, transaksi.getId_transaksi()));
        transaksi.setTracking_logs(getTrackingLogsByTransaksi(conn, transaksi.getId_transaksi()));
    }

    private List<Detail_Transaksi> getDetailTransaksiByTransaksi(Connection conn, String idTransaksi)
            throws SQLException {
        List<Detail_Transaksi> list = new ArrayList<>();

        String sql = "SELECT d.*, p.id_produk AS produk_id, p.id_user_seller, p.id_kategori, " +
                "p.nama_produk, p.deskripsi, p.harga, p.stok, p.status_produk, " +
                "p.created_at AS produk_created_at, p.foto_produk, p.konten_deskripsi, p.catatan_penjual, " +
                "p.foto_produk_list, " +
                "k.nama_kategori, " +
                "u.id AS seller_id, u.username AS seller_username, u.email AS seller_email, u.role AS seller_role, " +
                "t.id_toko, t.nama_toko, t.email_bisnis, t.alamat_toko, t.created_at AS toko_created_at " +
                "FROM \"Detail_Transaksi\" d " +
                "JOIN \"Produk\" p ON d.id_produk = p.id_produk " +
                "LEFT JOIN \"Kategori_Produk\" k ON p.id_kategori = k.id_kategori " +
                "LEFT JOIN \"User\" u ON p.id_user_seller = u.id " +
                "LEFT JOIN \"Toko\" t ON t.id_user = u.id " +
                "WHERE d.id_transaksi = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idTransaksi);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Detail_Transaksi detail = new Detail_Transaksi();
                    detail.setId_detail(rs.getString("id_detail"));
                    detail.setId_transaksi(rs.getString("id_transaksi"));
                    detail.setId_produk(rs.getString("id_produk"));
                    detail.setKuantitas(rs.getInt("kuantitas"));
                    detail.setHarga_satuan(rs.getInt("harga_satuan"));
                    detail.setSubtotal(rs.getInt("subtotal"));

                    Produk produk = new Produk();
                    produk.setId_produk(rs.getString("produk_id"));
                    produk.setId_user_seller(rs.getInt("id_user_seller"));
                    produk.setId_kategori(rs.getString("id_kategori"));
                    produk.setNama_produk(rs.getString("nama_produk"));
                    produk.setDeskripsi(rs.getString("deskripsi"));
                    produk.setHarga(rs.getInt("harga"));
                    produk.setStok(rs.getInt("stok"));
                    produk.setStatus_produk(rs.getString("status_produk"));
                    produk.setCreated_at(rs.getTimestamp("produk_created_at"));
                    produk.setFoto_produk(rs.getString("foto_produk"));
                    produk.setKonten_deskripsi(rs.getString("konten_deskripsi"));
                    produk.setCatatan_penjual(rs.getString("catatan_penjual"));

                    Array fotoArray = rs.getArray("foto_produk_list");
                    if (fotoArray != null) {
                        String[] fotoList = (String[]) fotoArray.getArray();
                        produk.setFoto_produk_list(java.util.Arrays.asList(fotoList));
                    }

                    mapProdukRelations(rs, produk);
                    detail.setProduk(produk);
                    list.add(detail);
                }
            }
        }

        return list;
    }

    private Alamat getAlamatById(Connection conn, String idAlamat) throws SQLException {
        String sql = "SELECT * FROM \"Alamat\" WHERE id_alamat = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idAlamat);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Alamat alamat = new Alamat();
                    alamat.setId_alamat(rs.getString("id_alamat"));
                    alamat.setId_user(rs.getInt("id_user"));
                    alamat.setNama_penerima(rs.getString("nama_penerima"));
                    alamat.setNomor_hp(rs.getString("nomor_hp"));
                    alamat.setAlamat_lengkap(rs.getString("alamat_lengkap"));
                    return alamat;
                }
            }
        }

        return null;
    }

    private Jasa_Kirim getJasaKirimById(Connection conn, String idJasa) throws SQLException {
        String sql = "SELECT * FROM \"Jasa_Kirim\" WHERE id_jasa = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idJasa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Jasa_Kirim jasa = new Jasa_Kirim();
                    jasa.setId_jasa(rs.getString("id_jasa"));
                    jasa.setNama_jasa(rs.getString("nama_jasa"));
                    jasa.setHarga_pengiriman(rs.getInt("harga_pengiriman"));
                    jasa.setEstimasi_waktu(rs.getString("estimasi_waktu"));
                    return jasa;
                }
            }
        }

        return null;
    }

    private Metode_Pembayaran getMetodePembayaranById(Connection conn, String idMetode) throws SQLException {
        String sql = "SELECT * FROM \"Metode_Pembayaran\" WHERE id_metode = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idMetode);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Metode_Pembayaran metode = new Metode_Pembayaran();
                    metode.setId_metode(rs.getString("id_metode"));
                    metode.setNama_metode(rs.getString("nama_metode"));
                    metode.setKode_metode(rs.getString("kode_metode"));
                    return metode;
                }
            }
        }

        return null;
    }

    private Pembayaran getPembayaranByTransaksi(Connection conn, String idTransaksi) throws SQLException {
        String sql = "SELECT * FROM \"Pembayaran\" WHERE id_transaksi = ? LIMIT 1";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idTransaksi);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pembayaran pembayaran = new Pembayaran();
                    pembayaran.setId_pembayaran(rs.getString("id_pembayaran"));
                    pembayaran.setId_transaksi(rs.getString("id_transaksi"));
                    pembayaran.setStatus_pembayaran(rs.getString("status_pembayaran"));
                    pembayaran.setTanggal_pembayaran(rs.getTimestamp("tanggal_pembayaran"));
                    return pembayaran;
                }
            }
        }

        return null;
    }

    private List<TrackingLog> getTrackingLogsByTransaksi(Connection conn, String idTransaksi) throws SQLException {
        List<TrackingLog> list = new ArrayList<>();

        String sql = "SELECT * FROM \"TrackingLog\" WHERE id_transaksi = ? ORDER BY waktu ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idTransaksi);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TrackingLog log = new TrackingLog();
                    log.setId_log(rs.getString("id_log"));
                    log.setId_transaksi(rs.getString("id_transaksi"));
                    log.setStatus(rs.getString("status"));
                    log.setWaktu(rs.getTimestamp("waktu"));
                    list.add(log);
                }
            }
        }

        return list;
    }

    public boolean createTransaksi(Transaksi transaksi) {
        String sql = "INSERT INTO \"Transaksi\" " +
                "(id_transaksi, id_user, id_alamat, id_jasa_kirim, id_metode_pembayaran, status_transaksi, tanggal_transaksi, total_harga) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            String id = transaksi.getId_transaksi();
            if (id == null || id.trim().isEmpty()) {
                id = UUID.randomUUID().toString();
                transaksi.setId_transaksi(id);
            }

            ps.setString(1, id);
            ps.setInt(2, transaksi.getId_user());
            ps.setString(3, transaksi.getId_alamat());
            ps.setString(4, transaksi.getId_jasa_kirim());
            ps.setString(5, transaksi.getId_metode_pembayaran());
            ps.setString(6, transaksi.getStatus_transaksi() == null ? "BELUM_BAYAR" : transaksi.getStatus_transaksi());
            ps.setInt(7, transaksi.getTotal_harga());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] createTransaksi gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean createTransaksiMultiProduk(Transaksi transaksi) {
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            List<Detail_Transaksi> details = transaksi.getDetail_transaksi();

            if (details == null || details.isEmpty()) {
                conn.rollback();
                return false;
            }

            String idTransaksi = transaksi.getId_transaksi();

            if (idTransaksi == null || idTransaksi.trim().isEmpty()) {
                idTransaksi = UUID.randomUUID().toString();
                transaksi.setId_transaksi(idTransaksi);
            }

            int totalProduk = 0;

            for (Detail_Transaksi detail : details) {
                String produkSql = "SELECT harga, stok FROM \"Produk\" WHERE id_produk = ?";

                try (PreparedStatement psProduk = conn.prepareStatement(produkSql)) {
                    psProduk.setString(1, detail.getId_produk());

                    try (ResultSet rs = psProduk.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }

                        int harga = rs.getInt("harga");
                        int stok = rs.getInt("stok");

                        if (detail.getKuantitas() <= 0 || stok < detail.getKuantitas()) {
                            conn.rollback();
                            return false;
                        }

                        totalProduk += harga * detail.getKuantitas();
                    }
                }
            }

            int ongkir = 0;
            String jasaSql = "SELECT harga_pengiriman FROM \"Jasa_Kirim\" WHERE id_jasa = ?";

            try (PreparedStatement psJasa = conn.prepareStatement(jasaSql)) {
                psJasa.setString(1, transaksi.getId_jasa_kirim());

                try (ResultSet rs = psJasa.executeQuery()) {
                    if (rs.next()) {
                        ongkir = rs.getInt("harga_pengiriman");
                    }
                }
            }

            int totalHarga = totalProduk + ongkir;
            transaksi.setTotal_harga(totalHarga);

            String statusTransaksi = transaksi.getStatus_transaksi();
            if (statusTransaksi == null || statusTransaksi.trim().isEmpty()) {
                statusTransaksi = "DIKEMAS";
                transaksi.setStatus_transaksi(statusTransaksi);
            }

            String insertTransaksiSql = "INSERT INTO \"Transaksi\" " +
                    "(id_transaksi, id_user, id_alamat, id_jasa_kirim, id_metode_pembayaran, status_transaksi, tanggal_transaksi, total_harga) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";

            try (PreparedStatement ps = conn.prepareStatement(insertTransaksiSql)) {
                ps.setString(1, idTransaksi);
                ps.setInt(2, transaksi.getId_user());
                ps.setString(3, transaksi.getId_alamat());
                ps.setString(4, transaksi.getId_jasa_kirim());
                ps.setString(5, transaksi.getId_metode_pembayaran());
                ps.setString(6, statusTransaksi);
                ps.setInt(7, totalHarga);
                ps.executeUpdate();
            }

            String insertDetailSql = "INSERT INTO \"Detail_Transaksi\" " +
                    "(id_detail, id_transaksi, id_produk, kuantitas, harga_satuan, subtotal) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            String updateStokSql = "UPDATE \"Produk\" SET stok = stok - ? WHERE id_produk = ?";
            String deleteCartSql = "DELETE FROM \"Keranjang\" WHERE id_user = ? AND id_produk = ?";

            for (Detail_Transaksi detail : details) {
                int harga = 0;

                String hargaSql = "SELECT harga FROM \"Produk\" WHERE id_produk = ?";

                try (PreparedStatement psHarga = conn.prepareStatement(hargaSql)) {
                    psHarga.setString(1, detail.getId_produk());

                    try (ResultSet rs = psHarga.executeQuery()) {
                        if (rs.next()) {
                            harga = rs.getInt("harga");
                        }
                    }
                }

                int subtotal = harga * detail.getKuantitas();

                try (PreparedStatement psDetail = conn.prepareStatement(insertDetailSql)) {
                    psDetail.setString(1, UUID.randomUUID().toString());
                    psDetail.setString(2, idTransaksi);
                    psDetail.setString(3, detail.getId_produk());
                    psDetail.setInt(4, detail.getKuantitas());
                    psDetail.setInt(5, harga);
                    psDetail.setInt(6, subtotal);
                    psDetail.executeUpdate();
                }

                try (PreparedStatement psStok = conn.prepareStatement(updateStokSql)) {
                    psStok.setInt(1, detail.getKuantitas());
                    psStok.setString(2, detail.getId_produk());
                    psStok.executeUpdate();
                }

                try (PreparedStatement psCart = conn.prepareStatement(deleteCartSql)) {
                    psCart.setInt(1, transaksi.getId_user());
                    psCart.setString(2, detail.getId_produk());
                    psCart.executeUpdate();
                }
            }

            String insertPembayaranSql = "INSERT INTO \"Pembayaran\" " +
                    "(id_pembayaran, id_transaksi, status_pembayaran, tanggal_pembayaran) " +
                    "VALUES (?, ?, ?, NOW())";

            try (PreparedStatement psBayar = conn.prepareStatement(insertPembayaranSql)) {
                psBayar.setString(1, UUID.randomUUID().toString());
                psBayar.setString(2, idTransaksi);
                psBayar.setString(3, "BAYAR_DI_TEMPAT");
                psBayar.executeUpdate();
            }

            String insertTrackingSql = "INSERT INTO \"TrackingLog\" " +
                    "(id_log, id_transaksi, status, waktu) " +
                    "VALUES (?, ?, ?, NOW())";

            try (PreparedStatement psTrack = conn.prepareStatement(insertTrackingSql)) {
                psTrack.setString(1, UUID.randomUUID().toString());
                psTrack.setString(2, idTransaksi);
                psTrack.setString(3, "Pesanan sedang dikemas");
                psTrack.executeUpdate();
            }

            loadTransaksiRelations(conn, transaksi);

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeError) {
                    closeError.printStackTrace();
                }
            }
        }
    }

    public Transaksi getTransaksiById(String idTransaksi) {
        String sql = "SELECT * FROM \"Transaksi\" WHERE id_transaksi = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idTransaksi);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Transaksi transaksi = mapResultSetToTransaksi(rs);
                    loadTransaksiRelations(conn, transaksi);
                    return transaksi;
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getTransaksiById gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Transaksi> getTransaksiByUser(int idUser) {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM \"Transaksi\" WHERE id_user = ? ORDER BY tanggal_transaksi DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaksi transaksi = mapResultSetToTransaksi(rs);
                    loadTransaksiRelations(conn, transaksi);
                    list.add(transaksi);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getTransaksiByUser gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Transaksi> getTransaksiBySeller(int idSeller) {
        List<Transaksi> list = new ArrayList<>();

        String sql = "SELECT t.* FROM \"Transaksi\" t " +
                "WHERE EXISTS (" +
                "SELECT 1 FROM \"Detail_Transaksi\" d " +
                "JOIN \"Produk\" p ON d.id_produk = p.id_produk " +
                "WHERE d.id_transaksi = t.id_transaksi " +
                "AND p.id_user_seller = ?" +
                ") " +
                "ORDER BY t.tanggal_transaksi DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSeller);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaksi transaksi = mapResultSetToTransaksi(rs);
                    loadTransaksiRelations(conn, transaksi);
                    list.add(transaksi);
                }
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] getTransaksiBySeller gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public boolean konfirmasiKirim(String idTransaksi, int idSeller) {
        String cekSql = "SELECT COUNT(*) FROM \"Detail_Transaksi\" d " +
                "JOIN \"Produk\" p ON d.id_produk = p.id_produk " +
                "WHERE d.id_transaksi = ? AND p.id_user_seller = ?";

        String insertTrackingSql = "INSERT INTO \"TrackingLog\" " +
                "(id_log, id_transaksi, status, waktu) " +
                "VALUES (?, ?, ?, NOW())";

        String updateTransaksiSql = "UPDATE \"Transaksi\" SET status_transaksi = ? WHERE id_transaksi = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psCek = conn.prepareStatement(cekSql)) {
                psCek.setString(1, idTransaksi);
                psCek.setInt(2, idSeller);

                try (ResultSet rs = psCek.executeQuery()) {
                    if (!rs.next() || rs.getInt(1) == 0) {
                        conn.rollback();
                        return false;
                    }
                }
            }

            try (PreparedStatement psTrack = conn.prepareStatement(insertTrackingSql)) {
                psTrack.setString(1, UUID.randomUUID().toString());
                psTrack.setString(2, idTransaksi);
                psTrack.setString(3, "DIKIRIM_SELLER_" + idSeller);
                psTrack.executeUpdate();
            }

            try (PreparedStatement psUpdate = conn.prepareStatement(updateTransaksiSql)) {
                psUpdate.setString(1, "DIKIRIM");
                psUpdate.setString(2, idTransaksi);
                psUpdate.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("[ERROR] konfirmasiKirim gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateStatusTransaksi(String idTransaksi, String statusTransaksi) {
        String sql = "UPDATE \"Transaksi\" SET status_transaksi = ? WHERE id_transaksi = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statusTransaksi);
            ps.setString(2, idTransaksi);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERROR] updateStatusTransaksi gagal: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}