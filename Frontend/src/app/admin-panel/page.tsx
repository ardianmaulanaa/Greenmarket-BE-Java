"use client";

import { useRouter } from "next/navigation";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useToast } from "@/hooks/useToast";
import Nav from "@/components/navbar";
import { API_BASE_URL } from "@/lib/api";
import { motion } from "framer-motion";

interface AdminUser {
  id: number | string;
  username?: string;
  name?: string;
  email?: string;
  role?: string;
  createdAt?: string | Date;
}

interface AdminProduct {
  id_produk: string;
  id_user_seller: number;
  nama_produk: string;
  harga: number;
  stok: number;
  status_produk: string;
  foto_produk?: string;
  foto_produk_list?: string[];
  deskripsi?: string;
  created_at?: string | Date;
}

interface AdminTransaction {
  id_transaksi: string;
  id_user: number;
  status_transaksi: string;
  tanggal_transaksi: string | Date;
  total_harga: number;
}

const containerVariants = {
  hidden: { opacity: 0 },
  show: {
    opacity: 1,
    transition: {
      staggerChildren: 0.06,
    },
  },
} as const;

const itemVariants = {
  hidden: { opacity: 0, y: 15 },
  show: {
    opacity: 1,
    y: 0,
    transition: {
      type: "spring",
      stiffness: 100,
      damping: 15,
    },
  },
} as const;

export default function AdminPanel() {
  const { showToast } = useToast();
  const router = useRouter();

  const [isPageLoading, setIsPageLoading] = useState(true);
  const [users, setUsers] = useState<AdminUser[]>([]);
  const [products, setProducts] = useState<AdminProduct[]>([]);
  const [transactions, setTransactions] = useState<AdminTransaction[]>([]);
  const [loading, setLoading] = useState(true);
  const [adminName, setAdminName] = useState("Admin");
  const [activeTab, setActiveTab] = useState<"dashboard" | "users" | "products">("dashboard");

  useEffect(() => {
    // Efek loading saat memuat halaman (800ms)
    const timer = setTimeout(() => {
      setIsPageLoading(false);
    }, 800);

    const role = localStorage.getItem("userRole");
    const savedUser = localStorage.getItem("user");

    if (savedUser) {
      try {
        const userData = JSON.parse(savedUser) as AdminUser;
        setAdminName(userData.username || userData.name || "Admin");
      } catch (e) {
        console.error("Gagal membaca user dari localStorage", e);
      }
    }

    // Pengecekan Keamanan Role Admin
    if (role !== "ADMIN") {
      showToast("Akses ditolak! Halaman ini khusus Administrator.", "error");
      if (role === "SELLER" || role === "BUYER") {
        router.push("/beranda-dashboard");
      } else {
        router.push("/login");
      }
      return;
    }

    const fetchDashboardData = async () => {
      try {
        const [usersRes, productsRes, transactionsRes] = await Promise.all([
          fetch(`${API_BASE_URL}/users`),
          fetch(`${API_BASE_URL}/products`),
          fetch(`${API_BASE_URL}/transactions`),
        ]);

        if (usersRes.ok) {
          const usersData = await usersRes.json();
          setUsers(usersData?.data || []);
        }
        if (productsRes.ok) {
          const productsData = await productsRes.json();
          setProducts(productsData || []);
        }
        if (transactionsRes.ok) {
          const transactionsData = await transactionsRes.json();
          setTransactions(transactionsData || []);
        }
      } catch (error) {
        console.error("Gagal mengambil data dashboard", error);
        showToast("Gagal menyinkronkan data dari database.", "error");
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();

    return () => clearTimeout(timer);
  }, [router, showToast]);

  const handleDeleteUser = async (id: number | string) => {
    if (!confirm("Apakah Anda yakin ingin menghapus pengguna ini?")) return;
    try {
      const res = await fetch(`${API_BASE_URL}/users/${id}`, {
        method: "DELETE",
      });
      if (res.ok) {
        showToast("Pengguna berhasil dihapus.", "success");
        setUsers((prev) => prev.filter((u) => Number(u.id) !== Number(id)));
      } else {
        showToast("Gagal menghapus pengguna.", "error");
      }
    } catch (err) {
      console.error(err);
      showToast("Terjadi kesalahan sistem.", "error");
    }
  };

  const handleUpdateRole = async (userId: number | string, newRole: string) => {
    try {
      const userToUpdate = users.find((u) => Number(u.id) === Number(userId));
      if (!userToUpdate) return;
      const res = await fetch(`${API_BASE_URL}/users/${userId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ ...userToUpdate, role: newRole }),
      });
      if (res.ok) {
        showToast("Role pengguna berhasil diperbarui.", "success");
        setUsers((prev) =>
          prev.map((u) => (Number(u.id) === Number(userId) ? { ...u, role: newRole } : u))
        );
      } else {
        showToast("Gagal memperbarui role pengguna.", "error");
      }
    } catch (err) {
      console.error(err);
      showToast("Terjadi kesalahan sistem.", "error");
    }
  };

  const handleDeleteProduct = async (id: string) => {
    if (!confirm("Apakah Anda yakin ingin menghapus produk ini?")) return;
    try {
      const res = await fetch(`${API_BASE_URL}/products/${id}`, {
        method: "DELETE",
      });
      if (res.ok) {
        showToast("Produk berhasil dihapus.", "success");
        setProducts((prev) => prev.filter((p) => p.id_produk !== id));
      } else {
        showToast("Gagal menghapus produk.", "error");
      }
    } catch (err) {
      console.error(err);
      showToast("Terjadi kesalahan sistem.", "error");
    }
  };

  const handleToggleProductStatus = async (product: any) => {
    const nextStatus = product.status_produk === "AKTIF" ? "NON_AKTIF" : "AKTIF";
    try {
      const res = await fetch(`${API_BASE_URL}/products`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ ...product, status_produk: nextStatus }),
      });
      if (res.ok) {
        showToast(`Status produk berhasil diubah menjadi ${nextStatus}.`, "success");
        setProducts((prev) =>
          prev.map((p) =>
            p.id_produk === product.id_produk ? { ...p, status_produk: nextStatus } : p
          )
        );
      } else {
        showToast("Gagal merubah status produk.", "error");
      }
    } catch (err) {
      console.error(err);
      showToast("Terjadi kesalahan sistem.", "error");
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    router.push("/login");
  };

  // Helper untuk formatting Rupiah
  const formatRupiah = (amount: number) => {
    return new Intl.NumberFormat("id-ID", {
      style: "currency",
      currency: "IDR",
      maximumFractionDigits: 0,
    }).format(amount);
  };

  // Helper untuk format Tanggal
  const formatDate = (dateStr?: string | Date) => {
    if (!dateStr) return "-";
    try {
      const d = new Date(dateStr);
      return d.toLocaleDateString("id-ID", {
        day: "numeric",
        month: "short",
        year: "numeric",
      });
    } catch {
      return "-";
    }
  };

  // ── CALCULATE STATS DYNAMICALLY ──
  const totalUsers = users.length;
  const totalSellers = users.filter((u) => u.role === "SELLER").length;
  const activeProducts = products.filter((p) => p.status_produk === "AKTIF").length;
  const totalTransactions = transactions.length;
  const totalRevenue = transactions.reduce((sum, tx) => sum + tx.total_harga, 0);

  // Sorting lists for recent feeds
  const sortedUsers = [...users]
    .sort((a, b) => {
      const timeA = a.createdAt ? new Date(a.createdAt).getTime() : 0;
      const timeB = b.createdAt ? new Date(b.createdAt).getTime() : 0;
      return timeB - timeA || Number(b.id) - Number(a.id);
    })
    .slice(0, 8);

  const sortedTransactions = [...transactions].slice(0, 8);
  const sortedProducts = [...products].slice(0, 5);

  // ── TAMPILAN LOADING SCREEN ──
  if (isPageLoading) {
    return (
      <div className="min-h-screen bg-[#0a110b] flex flex-col items-center justify-center font-sans">
        <div className="w-12 h-12 border-4 border-[#2fa84f]/20 border-t-[#2fa84f] rounded-full animate-spin mb-4"></div>
        <p className="text-[#2fa84f] font-bold text-[11px] tracking-[3px] uppercase animate-pulse">
          Otentikasi Sistem...
        </p>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-br from-[#070c08] via-[#0d140e] to-[#0a110b] font-sans text-white relative overflow-hidden">
      {/* ── BACKGROUND DECOR ── */}
      <div className="absolute top-[-10%] right-[-5%] w-[600px] h-[600px] bg-[#2fa84f] opacity-10 blur-[150px] rounded-full pointer-events-none z-0"></div>
      <div className="absolute bottom-[-15%] left-[-10%] w-[500px] h-[500px] bg-[#2fa84f] opacity-[0.08] blur-[120px] rounded-full pointer-events-none z-0"></div>

      {/* ── NAVBAR ── */}
      <Nav variant="admin" adminName={adminName} />

      {/* ── MAIN LAYOUT (Sidebar + Content) ── */}
      <div className="max-w-[1600px] mx-auto pt-28 pb-20 px-6 flex flex-col lg:flex-row gap-8 relative z-10 w-full flex-grow">
        {/* ── SIDEBAR ADMIN ── */}
        <aside className="w-full lg:w-[280px] shrink-0">
          <div className="sticky top-28 bg-[#1a1f1b]/80 backdrop-blur-xl rounded-[32px] p-6 border border-white/5 shadow-2xl">
            <span className="text-[10px] font-bold text-gray-500 uppercase tracking-[3px] mb-4 block text-center">
              Menu Kontrol
            </span>

            <nav className="flex flex-col gap-2">
              <button
                onClick={() => setActiveTab("dashboard")}
                className={`flex items-center gap-3 p-3.5 rounded-2xl transition text-left w-full border-0 ${
                  activeTab === "dashboard"
                    ? "bg-[#2fa84f] text-white font-bold shadow-[0_4px_15px_rgba(47,168,79,0.2)]"
                    : "bg-transparent text-gray-400 hover:bg-white/5 hover:text-white font-semibold"
                }`}
              >
                <svg
                  width="18"
                  height="18"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2.5"
                >
                  <rect x="3" y="3" width="7" height="7" />
                  <rect x="14" y="3" width="7" height="7" />
                  <rect x="14" y="14" width="7" height="7" />
                  <rect x="3" y="14" width="7" height="7" />
                </svg>
                <span className="text-sm">Dashboard</span>
              </button>
              <button
                onClick={() => setActiveTab("users")}
                className={`flex items-center gap-3 p-3.5 rounded-2xl transition text-left w-full border-0 ${
                  activeTab === "users"
                    ? "bg-[#2fa84f] text-white font-bold shadow-[0_4px_15px_rgba(47,168,79,0.2)]"
                    : "bg-transparent text-gray-400 hover:bg-white/5 hover:text-white font-semibold"
                }`}
              >
                <svg
                  width="18"
                  height="18"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2.5"
                >
                  <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" />
                  <circle cx="9" cy="7" r="4" />
                  <path d="M23 21v-2a4 4 0 0 0-3-3.87" />
                  <path d="M16 3.13a4 4 0 0 1 0 7.75" />
                </svg>
                <span className="text-sm">Kelola Pengguna</span>
              </button>
              <button
                onClick={() => setActiveTab("products")}
                className={`flex items-center gap-3 p-3.5 rounded-2xl transition text-left w-full border-0 ${
                  activeTab === "products"
                    ? "bg-[#2fa84f] text-white font-bold shadow-[0_4px_15px_rgba(47,168,79,0.2)]"
                    : "bg-transparent text-gray-400 hover:bg-white/5 hover:text-white font-semibold"
                }`}
              >
                <svg
                  width="18"
                  height="18"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2.5"
                >
                  <path d="M20.59 13.41l-7.17 7.17a2 2 0 0 1-2.83 0L2 12V2h10l8.59 8.59a2 2 0 0 1 0 2.82z" />
                  <line x1="7" y1="7" x2="7.01" y2="7" />
                </svg>
                <span className="text-sm">Moderasi Produk</span>
              </button>

              <div className="my-4 border-t border-white/5" />

              <button
                onClick={handleLogout}
                className="w-full flex items-center gap-3 p-3.5 rounded-2xl text-red-400 hover:bg-red-500/10 hover:border-red-500/20 border border-transparent bg-transparent transition font-bold text-left group"
              >
                <svg
                  width="18"
                  height="18"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2.5"
                  className="group-hover:translate-x-1 transition-transform"
                >
                  <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
                  <polyline points="16 17 21 12 16 7" />
                  <line x1="21" y1="12" x2="9" y2="12" />
                </svg>
                <span className="text-sm">Keluar Sistem</span>
              </button>
            </nav>
          </div>
        </aside>

        {/* ── MAIN CONTENT ── */}
        <main className="flex-grow flex flex-col min-w-0">
          <div className="mb-8">
            <span className="text-[11px] font-black text-[#2fa84f] uppercase tracking-[3px] mb-2 block">
              Executive Control Center
            </span>
            <h1 className="text-3xl lg:text-4xl font-[800] text-white tracking-tight leading-tight">
              {activeTab === "dashboard"
                ? "Admin Dashboard"
                : activeTab === "users"
                ? "Kelola Pengguna"
                : "Moderasi Produk"}
            </h1>
            <p className="text-gray-400 text-sm font-medium mt-2">
              {activeTab === "dashboard"
                ? "Pantau dan kelola seluruh ekosistem GreenMarket secara langsung."
                : activeTab === "users"
                ? "Manajemen akun, ubah role hak akses, dan lakukan pembersihan data pengguna."
                : "Moderasi barang dagangan, tayangkan/nonaktifkan produk marketplace secara real-time."}
            </p>
          </div>

          {activeTab === "dashboard" && (
            <>
              {/* ── STATS CARDS ── */}
              <motion.div
                variants={containerVariants}
                initial="hidden"
                animate="show"
                className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5 mb-10"
              >
                {[
                  {
                    label: "Total Pengguna",
                    value: loading ? "-" : totalUsers,
                    icon: "👥",
                    color: "from-blue-500 to-cyan-500",
                  },
                  {
                    label: "Total Penjual",
                    value: loading ? "-" : totalSellers,
                    icon: "🏪",
                    color: "from-purple-500 to-indigo-500",
                  },
                  {
                    label: "Produk Aktif",
                    value: loading ? "-" : activeProducts,
                    icon: "📦",
                    color: "from-emerald-500 to-teal-500",
                  },
                  {
                    label: "Total Transaksi",
                    value: loading ? "-" : totalTransactions,
                    icon: "💳",
                    color: "from-amber-500 to-orange-500",
                  },
                ].map((stat, idx) => (
                  <motion.div
                    key={idx}
                    variants={itemVariants}
                    className="bg-[#1a1f1b]/80 backdrop-blur-xl p-6 rounded-[28px] border border-white/5 shadow-2xl relative group overflow-hidden hover:-translate-y-1 transition-transform duration-300"
                  >
                    <div
                      className={`absolute -right-4 -top-4 w-20 h-20 bg-gradient-to-br ${stat.color} rounded-full opacity-10 blur-xl group-hover:opacity-20 transition-opacity`}
                    ></div>
                    <div className="absolute top-5 right-5 text-2xl opacity-50 group-hover:scale-110 group-hover:opacity-100 transition-all">
                      {stat.icon}
                    </div>

                    <h3 className="text-gray-400 font-bold text-[9px] uppercase tracking-[2px] mb-2">
                      {stat.label}
                    </h3>
                    <p className="text-[22px] font-[900] text-white tracking-tighter leading-none truncate max-w-[90%]">
                      {stat.value}
                    </p>
                    <div className="h-1 w-10 bg-white/10 rounded-full mt-5 overflow-hidden">
                      <div
                        className={`h-full bg-gradient-to-r ${stat.color} w-1/3 group-hover:w-full transition-all duration-700`}
                      ></div>
                    </div>
                  </motion.div>
                ))}
              </motion.div>

              {/* ── TWO-COLUMN LIVE FEED ── */}
              <div className="grid grid-cols-1 xl:grid-cols-2 gap-8 mb-10">
                {/* ── TRANSAKSI TERBARU ── */}
                <div className="bg-[#1a1f1b]/80 backdrop-blur-xl rounded-[32px] border border-white/5 shadow-2xl overflow-hidden flex flex-col h-[520px]">
                  <div className="p-7 border-b border-white/5 flex justify-between items-center bg-white/5">
                    <h3 className="text-base font-[800] text-white tracking-tight flex items-center gap-2.5">
                      <svg
                        width="18"
                        height="18"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="#2fa84f"
                        strokeWidth="2.5"
                      >
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z" />
                        <polyline points="14 2 14 8 20 8" />
                        <line x1="16" y1="13" x2="8" y2="13" />
                        <line x1="16" y1="17" x2="8" y2="17" />
                        <polyline points="10 9 9 9 8 9" />
                      </svg>
                      Transaksi Terbaru
                    </h3>
                    <span className="bg-[#2fa84f]/10 text-[#2fa84f] px-2.5 py-1 rounded-lg text-[9px] font-black uppercase tracking-widest border border-[#2fa84f]/20 flex items-center gap-1.5">
                      <div className="w-1.5 h-1.5 bg-[#2fa84f] rounded-full animate-pulse"></div>{" "}
                      Live
                    </span>
                  </div>
                  <div className="p-6 overflow-y-auto flex-grow no-scrollbar">
                    <div className="space-y-3">
                      {loading ? (
                        <div className="flex flex-col items-center justify-center h-40">
                          <div className="w-8 h-8 border-4 border-[#2fa84f]/20 border-t-[#2fa84f] rounded-full animate-spin mb-3"></div>
                          <span className="text-[9px] text-[#2fa84f] font-bold uppercase tracking-widest animate-pulse">
                            Sinkronisasi Data...
                          </span>
                        </div>
                      ) : sortedTransactions.length === 0 ? (
                        <div className="text-center py-20 text-gray-500 font-bold text-sm border border-dashed border-white/10 rounded-[24px]">
                          Belum ada transaksi di platform.
                        </div>
                      ) : (
                        sortedTransactions.map((tx) => {
                          const buyer = users.find((u) => Number(u.id) === Number(tx.id_user));
                          const buyerName = buyer?.username || buyer?.email || `Pembeli #${tx.id_user}`;

                          const getStatusBadge = (status: string) => {
                            const s = status.toUpperCase();
                            if (s.includes("SELESAI")) {
                              return "bg-emerald-500/10 text-emerald-400 border-emerald-500/20";
                            } else if (s.includes("KIRIM")) {
                              return "bg-cyan-500/10 text-cyan-400 border-cyan-500/20";
                            } else if (s.includes("KEMAS") || s.includes("BUAT")) {
                              return "bg-blue-500/10 text-blue-400 border-blue-500/20";
                            } else if (s.includes("TEMPAT")) {
                              return "bg-yellow-500/10 text-yellow-400 border-yellow-500/20";
                            } else {
                              return "bg-orange-500/10 text-orange-400 border-orange-500/20";
                            }
                          };

                          return (
                            <div
                              key={tx.id_transaksi}
                              className="flex items-center gap-4 p-4 bg-[#0a110b]/50 rounded-[20px] border border-white/5 hover:border-[#2fa84f]/40 hover:bg-white/5 transition-all group"
                            >
                              <div className="w-10 h-10 bg-gradient-to-tr from-white/5 to-transparent rounded-xl flex items-center justify-center text-white font-bold uppercase border border-white/5 shadow-inner shrink-0">
                                💸
                              </div>
                              <div className="flex-grow min-w-0">
                                <p className="font-bold text-white text-[13px] leading-tight mb-1 truncate">
                                  {buyerName}
                                </p>
                                <p className="text-[10px] text-gray-500 font-medium">
                                  ID: {tx.id_transaksi.slice(0, 8)}... • {formatDate(tx.tanggal_transaksi)}
                                </p>
                              </div>
                              <div className="text-right shrink-0 flex flex-col items-end gap-1.5">
                                <p className="text-[#2fa84f] font-black text-[13px] tracking-tight leading-none">
                                  {formatRupiah(tx.total_harga)}
                                </p>
                                <span
                                  className={`text-[8px] font-black px-2 py-1 rounded-md uppercase tracking-wider border ${getStatusBadge(
                                    tx.status_transaksi
                                  )}`}
                                >
                                  {tx.status_transaksi.replace(/_/g, " ")}
                                </span>
                              </div>
                            </div>
                          );
                        })
                      )}
                    </div>
                  </div>
                </div>

                {/* ── REGISTRASI PENGGUNA ── */}
                <div className="bg-[#1a1f1b]/80 backdrop-blur-xl rounded-[32px] border border-white/5 shadow-2xl overflow-hidden flex flex-col h-[520px]">
                  <div className="p-7 border-b border-white/5 flex justify-between items-center bg-white/5">
                    <h3 className="text-base font-[800] text-white tracking-tight flex items-center gap-2.5">
                      <svg
                        width="18"
                        height="18"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="#2fa84f"
                        strokeWidth="2.5"
                      >
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" />
                        <circle cx="9" cy="7" r="4" />
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87" />
                        <path d="M16 3.13a4 4 0 0 1 0 7.75" />
                      </svg>
                      Pengguna Baru
                    </h3>
                    <span className="text-gray-400 text-[10px] font-bold">
                      {totalUsers} Total
                    </span>
                  </div>
                  <div className="p-6 overflow-y-auto flex-grow no-scrollbar">
                    <div className="space-y-3">
                      {loading ? (
                        <div className="flex flex-col items-center justify-center h-40">
                          <div className="w-8 h-8 border-4 border-[#2fa84f]/20 border-t-[#2fa84f] rounded-full animate-spin mb-3"></div>
                          <span className="text-[9px] text-[#2fa84f] font-bold uppercase tracking-widest animate-pulse">
                            Sinkronisasi Data...
                          </span>
                        </div>
                      ) : sortedUsers.length === 0 ? (
                        <div className="text-center py-20 text-gray-500 font-bold text-sm border border-dashed border-white/10 rounded-[24px]">
                          Belum ada pengguna terdaftar.
                        </div>
                      ) : (
                        sortedUsers.map((u) => (
                          <div
                            key={u.id}
                            className="flex items-center gap-4 p-4 bg-[#0a110b]/50 rounded-[20px] border border-white/5 hover:border-[#2fa84f]/40 hover:bg-white/5 transition-all group"
                          >
                            <div className="w-10 h-10 bg-gradient-to-tr from-white/10 to-transparent rounded-full flex items-center justify-center text-white font-bold uppercase shadow-inner border border-white/5 shrink-0">
                              {u.username ? u.username.charAt(0) : "?"}
                            </div>
                            <div className="flex-grow min-w-0">
                              <p className="font-bold text-white text-[13px] leading-snug truncate">
                                {u.username}
                              </p>
                              <p className="text-[10px] text-gray-500 font-medium truncate">
                                {u.email} • Terdaftar: {formatDate(u.createdAt)}
                              </p>
                            </div>
                            <div className="shrink-0">
                              <span
                                className={`text-[8px] font-black px-2.5 py-1 rounded-md uppercase tracking-wider border ${
                                  u.role === "ADMIN"
                                    ? "bg-purple-500/10 text-purple-400 border-purple-500/20"
                                    : u.role === "SELLER"
                                    ? "bg-blue-500/10 text-blue-400 border-blue-500/20"
                                    : "bg-[#2fa84f]/10 text-[#2fa84f] border-[#2fa84f]/20"
                                }`}
                              >
                                {u.role}
                              </span>
                            </div>
                          </div>
                        ))
                      )}
                    </div>
                  </div>
                </div>
              </div>

              {/* ── PRODUK TERBARU TABLE ── */}
              <div className="bg-[#1a1f1b]/80 backdrop-blur-xl rounded-[32px] border border-white/5 shadow-2xl overflow-hidden flex flex-col w-full">
                <div className="p-7 border-b border-white/5 flex justify-between items-center bg-white/5">
                  <h3 className="text-base font-[800] text-white tracking-tight flex items-center gap-2.5">
                    <svg
                      width="18"
                      height="18"
                      viewBox="0 0 24 24"
                      fill="none"
                      stroke="#2fa84f"
                      strokeWidth="2.5"
                    >
                      <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z" />
                      <polyline points="3.27 6.96 12 12.01 20.73 6.96" />
                      <line x1="12" y1="22.08" x2="12" y2="12" />
                    </svg>
                    Produk Terbaru Marketplace
                  </h3>
                  <span className="text-gray-400 text-[10px] font-bold">
                    {products.length} Item
                  </span>
                </div>

                <div className="p-6 overflow-x-auto">
                  {loading ? (
                    <div className="flex flex-col items-center justify-center py-12">
                      <div className="w-8 h-8 border-4 border-[#2fa84f]/20 border-t-[#2fa84f] rounded-full animate-spin mb-3"></div>
                      <span className="text-[9px] text-[#2fa84f] font-bold uppercase tracking-widest animate-pulse">
                        Mencocokkan Inventaris...
                      </span>
                    </div>
                  ) : sortedProducts.length === 0 ? (
                    <div className="text-center py-12 text-gray-500 font-bold text-sm border border-dashed border-white/10 rounded-[24px]">
                      Belum ada produk terdaftar di marketplace.
                    </div>
                  ) : (
                    <table className="w-full text-left border-collapse min-w-[700px]">
                      <thead>
                        <tr className="border-b border-white/5 text-[9px] text-gray-500 font-black uppercase tracking-widest">
                          <th className="pb-4 pl-4">Produk</th>
                          <th className="pb-4">Penjual</th>
                          <th className="pb-4">Stok</th>
                          <th className="pb-4">Harga</th>
                          <th className="pb-4">Status</th>
                          <th className="pb-4 pr-4 text-right">Tanggal Rilis</th>
                        </tr>
                      </thead>
                      <tbody>
                        {sortedProducts.map((p) => {
                          const seller = users.find((u) => Number(u.id) === Number(p.id_user_seller));
                          const sellerName = seller?.username || `Seller #${p.id_user_seller}`;

                          const getProductImage = (prod: AdminProduct) => {
                            return (
                              prod.foto_produk ||
                              prod.foto_produk_list?.[0] ||
                              "https://placehold.co/100x100/1a1f1b/2fa84f?text=Product"
                            );
                          };

                          return (
                            <tr
                              key={p.id_produk}
                              className="border-b border-white/5 hover:bg-white/[0.02] transition-colors group text-[13px]"
                            >
                              <td className="py-4 pl-4 flex items-center gap-3">
                                <div className="w-10 h-10 bg-white/5 rounded-xl border border-white/10 overflow-hidden shrink-0">
                                  <img
                                    src={getProductImage(p)}
                                    alt={p.nama_produk}
                                    className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500"
                                  />
                                </div>
                                <span className="font-bold text-white group-hover:text-[#2fa84f] transition-colors">
                                  {p.nama_produk}
                                </span>
                              </td>
                              <td className="py-4 text-gray-300 font-medium">{sellerName}</td>
                              <td className="py-4">
                                <span
                                  className={`font-semibold ${
                                    p.stok < 5 ? "text-red-400" : "text-gray-400"
                                  }`}
                                >
                                  {p.stok} Unit
                                </span>
                              </td>
                              <td className="py-4 font-black text-[#2fa84f]">
                                {formatRupiah(p.harga)}
                              </td>
                              <td className="py-4">
                                <span className="text-[9px] font-black px-2 py-0.5 rounded bg-emerald-500/10 text-emerald-400 border border-emerald-500/20 uppercase">
                                  {p.status_produk}
                                </span>
                              </td>
                              <td className="py-4 pr-4 text-right text-gray-500 font-medium">
                                {formatDate(p.created_at)}
                              </td>
                            </tr>
                          );
                        })}
                      </tbody>
                    </table>
                  )}
                </div>
              </div>
            </>
          )}

          {activeTab === "users" && (
            <div className="bg-[#1a1f1b]/80 backdrop-blur-xl rounded-[32px] border border-white/5 shadow-2xl overflow-hidden flex flex-col w-full">
              <div className="p-7 border-b border-white/5 flex justify-between items-center bg-white/5">
                <h3 className="text-base font-[800] text-white tracking-tight flex items-center gap-2.5">
                  👥 Daftar Pengguna Sistem ({users.length})
                </h3>
              </div>
              <div className="p-6 overflow-x-auto">
                <table className="w-full text-left border-collapse min-w-[700px]">
                  <thead>
                    <tr className="border-b border-white/5 text-[9px] text-gray-500 font-black uppercase tracking-widest">
                      <th className="pb-4 pl-4">Pengguna</th>
                      <th className="pb-4">Email</th>
                      <th className="pb-4">Role Saat Ini</th>
                      <th className="pb-4">Ubah Role</th>
                      <th className="pb-4 pr-4 text-right">Aksi</th>
                    </tr>
                  </thead>
                  <tbody>
                    {users.map((u) => (
                      <tr
                        key={u.id}
                        className="border-b border-white/5 hover:bg-white/[0.02] transition-colors group text-[13px]"
                      >
                        <td className="py-4 pl-4 flex items-center gap-3">
                          <div className="w-9 h-9 bg-white/5 rounded-full flex items-center justify-center text-white font-bold uppercase border border-white/10 shrink-0">
                            {u.username ? u.username.charAt(0) : "?"}
                          </div>
                          <span className="font-bold text-white">
                            {u.username || "Tanpa Nama"}
                          </span>
                        </td>
                        <td className="py-4 text-gray-300 font-medium">{u.email}</td>
                        <td className="py-4">
                          <span
                            className={`text-[8px] font-black px-2.5 py-1 rounded-md uppercase tracking-wider border ${
                              u.role === "ADMIN"
                                ? "bg-purple-500/10 text-purple-400 border-purple-500/20"
                                : u.role === "SELLER"
                                ? "bg-blue-500/10 text-blue-400 border-blue-500/20"
                                : "bg-[#2fa84f]/10 text-[#2fa84f] border-[#2fa84f]/20"
                            }`}
                          >
                            {u.role}
                          </span>
                        </td>
                        <td className="py-4">
                          <select
                            value={u.role || "BUYER"}
                            onChange={(e) => handleUpdateRole(u.id, e.target.value)}
                            className="bg-[#0a110b] border border-white/10 text-white rounded-xl px-3 py-1.5 text-xs font-semibold focus:outline-none focus:border-[#2fa84f]"
                          >
                            <option value="BUYER">BUYER</option>
                            <option value="SELLER">SELLER</option>
                            <option value="ADMIN">ADMIN</option>
                          </select>
                        </td>
                        <td className="py-4 pr-4 text-right">
                          <button
                            onClick={() => handleDeleteUser(u.id)}
                            className="px-3 py-1.5 bg-red-500/10 text-red-400 hover:bg-red-500 hover:text-white rounded-xl text-xs font-bold transition border border-red-500/20"
                          >
                            Hapus
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}

          {activeTab === "products" && (
            <div className="bg-[#1a1f1b]/80 backdrop-blur-xl rounded-[32px] border border-white/5 shadow-2xl overflow-hidden flex flex-col w-full">
              <div className="p-7 border-b border-white/5 flex justify-between items-center bg-white/5">
                <h3 className="text-base font-[800] text-white tracking-tight flex items-center gap-2.5">
                  📦 Daftar Produk Marketplace ({products.length})
                </h3>
              </div>
              <div className="p-6 overflow-x-auto">
                <table className="w-full text-left border-collapse min-w-[800px]">
                  <thead>
                    <tr className="border-b border-white/5 text-[9px] text-gray-500 font-black uppercase tracking-widest">
                      <th className="pb-4 pl-4">Produk</th>
                      <th className="pb-4">Penjual</th>
                      <th className="pb-4">Stok</th>
                      <th className="pb-4">Harga</th>
                      <th className="pb-4">Status</th>
                      <th className="pb-4">Moderasi Status</th>
                      <th className="pb-4 pr-4 text-right">Aksi</th>
                    </tr>
                  </thead>
                  <tbody>
                    {products.map((p) => {
                      const seller = users.find((u) => Number(u.id) === Number(p.id_user_seller));
                      const sellerName = seller?.username || `Seller #${p.id_user_seller}`;

                      const getProductImage = (prod: AdminProduct) => {
                        return (
                          prod.foto_produk ||
                          prod.foto_produk_list?.[0] ||
                          "https://placehold.co/100x100/1a1f1b/2fa84f?text=Product"
                        );
                      };

                      return (
                        <tr
                          key={p.id_produk}
                          className="border-b border-white/5 hover:bg-white/[0.02] transition-colors group text-[13px]"
                        >
                          <td className="py-4 pl-4 flex items-center gap-3">
                            <div className="w-10 h-10 bg-white/5 rounded-xl border border-white/10 overflow-hidden shrink-0">
                              <img
                                src={getProductImage(p)}
                                alt={p.nama_produk}
                                className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500"
                              />
                            </div>
                            <span className="font-bold text-white group-hover:text-[#2fa84f] transition-colors">
                              {p.nama_produk}
                            </span>
                          </td>
                          <td className="py-4 text-gray-300 font-medium">{sellerName}</td>
                          <td className="py-4">
                            <span
                              className={`font-semibold ${
                                p.stok < 5 ? "text-red-400" : "text-gray-400"
                              }`}
                            >
                              {p.stok} Unit
                            </span>
                          </td>
                          <td className="py-4 font-black text-[#2fa84f]">
                            {formatRupiah(p.harga)}
                          </td>
                          <td className="py-4">
                            <span
                              className={`text-[9px] font-black px-2 py-0.5 rounded border uppercase ${
                                p.status_produk === "AKTIF"
                                  ? "bg-emerald-500/10 text-emerald-400 border-emerald-500/20"
                                  : "bg-red-500/10 text-red-400 border-red-500/20"
                              }`}
                            >
                              {p.status_produk}
                            </span>
                          </td>
                          <td className="py-4">
                            <button
                              onClick={() => handleToggleProductStatus(p)}
                              className={`px-3 py-1.5 rounded-xl text-xs font-bold transition border ${
                                p.status_produk === "AKTIF"
                                  ? "bg-amber-500/10 text-amber-400 border-amber-500/20 hover:bg-amber-500 hover:text-white"
                                  : "bg-emerald-500/10 text-emerald-400 border-emerald-500/20 hover:bg-emerald-500 hover:text-white"
                              }`}
                            >
                              {p.status_produk === "AKTIF" ? "Nonaktifkan" : "Aktifkan"}
                            </button>
                          </td>
                          <td className="py-4 pr-4 text-right">
                            <button
                              onClick={() => handleDeleteProduct(p.id_produk)}
                              className="px-3 py-1.5 bg-red-500/10 text-red-400 hover:bg-red-500 hover:text-white rounded-xl text-xs font-bold transition border border-red-500/20"
                            >
                              Hapus
                            </button>
                          </td>
                        </tr>
                      );
                    })}
                  </tbody>
                </table>
              </div>
            </div>
          )}
        </main>
      </div>      {/* ── FOOTER ── */}
      <footer className="bg-transparent py-8 text-center border-t border-white/5 mt-auto relative z-10">
        <p className="text-gray-600 text-[10px] font-black tracking-[4px] uppercase m-0">
          © 2026 GREENMARKET INC. ADMINISTRATOR HUB.
        </p>
      </footer>
    </div>
  );
}
