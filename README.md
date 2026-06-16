# GreenMarket - Platform Marketplace Produk Ramah Lingkungan

GreenMarket adalah aplikasi marketplace berbasis web yang menghubungkan penjual produk ramah lingkungan dan daur ulang dengan pembeli yang peduli terhadap kelestarian lingkungan. Dibangun sebagai Tugas Besar Pemrograman Berorientasi Objek (PBO).

---

## Tech Stack

| Layer | Teknologi |
|-------|-----------|
| Frontend | Next.js 15 (React 19), TypeScript, Tailwind CSS, Framer Motion |
| Backend | Java 17, Jakarta EE 9.1, Servlets |
| Database | PostgreSQL (Supabase Cloud) |
| Build Tool | Maven 3.8+ (Backend), npm (Frontend) |
| Web Server | Apache Tomcat 10 (Embedded via Cargo Maven Plugin) |
| Password Hashing | BCrypt (jBCrypt 0.4) |
| JSON Serialization | Gson 2.10.1 |

---

## Arsitektur Backend

Backend menggunakan pola **Controller → Service → DAO** (Layered Architecture):

```
com.greenmarket
├── controller/    # REST API Servlets (menangani HTTP request/response)
│   ├── AuthController.java         # Registrasi, login, cek session
│   ├── ProdukController.java       # CRUD produk, filter kategori
│   ├── TransaksiController.java    # Checkout dan transaksi
│   ├── KeranjangController.java    # Keranjang belanja
│   ├── PembayaranController.java   # Pembayaran
│   ├── UserController.java         # Manajemen user
│   ├── TokoController.java         # Manajemen toko
│   ├── AlamatController.java       # Alamat pengiriman
│   └── BaseApiController.java      # Base class (CORS, JSON helpers)
│
├── service/       # Business Logic Layer
│   ├── UserService.java
│   ├── ProdukService.java
│   ├── TransaksiService.java
│   ├── KeranjangService.java
│   ├── PembayaranService.java
│   └── TokoService.java
│
├── dao/           # Data Access Objects (query database)
│   ├── IUserDAO.java               # Interface
│   ├── IProdukDAO.java             # Interface
│   ├── ITransaksiDAO.java          # Interface
│   ├── UserDAO.java                # Implementasi
│   ├── ProdukDAO.java              # Implementasi
│   ├── TransaksiDAO.java           # Implementasi
│   ├── KeranjangDAO.java
│   ├── PembayaranDAO.java
│   └── ...
│
├── model/         # Entity Classes
│   ├── User.java          # Base class (Buyer, Seller, Admin)
│   ├── Buyer.java         # extends User
│   ├── Seller.java        # extends User
│   ├── Admin.java         # extends User
│   ├── Payment.java       # Interface
│   ├── Pembayaran.java    # implements Payment
│   ├── Produk.java        # Produk/toko
│   ├── Transaksi.java     # Transaksi
│   ├── Toko.java          # Toko penjual
│   ├── Keranjang.java     # Keranjang belanja
│   └── ...
│
└── util/
    └── DBConnection.java  # Koneksi database (Supabase)
```

### Penerapan Konsep OOP

| Konsep | Implementasi |
|--------|-------------|
| **Inheritance** | `Buyer`, `Seller`, `Admin` extends `User` |
| **Interface** | `Payment` diimplementasi oleh `Pembayaran`; `IUserDAO`, `IProdukDAO`, `ITransaksiDAO` |
| **Polymorphism** | `User.fromUser()` factory method — konversi User ke subclass berdasarkan role |
| **Encapsulation** | Seluruh model menggunakan private fields + getter/setter |
| **Abstraction** | DAO interfaces memisahkan kontrak dari implementasi |
| **Copy Constructor** | Subclass constructor menerima parent `User` object |

---

## Prerequisites

### 1. JDK 17 atau Lebih Baru

Download dari: [Adoptium](https://adoptium.net/) atau [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)

**Verifikasi:**
```bash
java --version
```
Output yang diharapkan: `openjdk 17.x.x` atau lebih baru

### 2. Apache Maven 3.8+

Download dari: [Maven Download](https://maven.apache.org/download.cgi)

**Instalasi Windows:**
1. Extract zip ke folder (misal `C:\apache-maven-3.9.9`)
2. Buka **System Properties → Environment Variables**
3. Di **System Variables**, klik `Path` → **Edit**
4. Tambahkan: `C:\apache-maven-3.9.9\bin`
5. Klik OK

**Verifikasi (buka CMD baru):**
```bash
mvn --version
```
Output yang diharapkan: `Apache Maven 3.9.x`

### 3. Node.js 18+ dan npm

Download dari: [Node.js](https://nodejs.org/) (pilih LTS)

**Verifikasi:**
```bash
node --version
npm --version
```

---

## Langkah-Langkah Konfigurasi dan Menjalankan Project

### Langkah 1: Clone Repository

```bash
git clone https://github.com/username-kamu/Greenmarket-BE-Java.git
cd Greenmarket-BE-Java
```

### Langkah 2: Konfigurasi Database

Project ini menggunakan **Supabase** sebagai database PostgreSQL cloud.

**File konfigurasi:** `backend-java/src/main/java/com/greenmarket/util/DBConnection.java`

```java
private static final String URL = "jdbc:postgresql://<host>:<port>/<database>";
private static final String USER = "postgres.xxx";
private static final String PASS = "password";
```

> **Catatan:** Database sudah dikonfigurasi dan aktif. Jika ingin menggunakan database lokal atau Supabase sendiri:
> 1. Buat database PostgreSQL baru
> 2. Import file `.sql` yang ada di repository
> 3. Ubah URL, USER, dan PASS di `DBConnection.java`

### Langkah 3: Build dan Jalankan Backend

Buka **terminal/CMD** di folder `backend-java`:

```bash
cd backend-java
mvn clean package cargo:run
```

**Penjelasan perintah:**
- `clean` — Hapus folder `target/` (build lama)
- `package` — Compile Java dan buat file `.war`
- `cargo:run` — Jalankan Tomcat 10 embedded dan deploy WAR

**Tunggu hingga muncul:**
```
[INFO] Tomcat 10.x Embedded started on port [8080]
```

**Verifikasi backend berjalan:**
Buka browser: `http://localhost:8080/backend-java/api/auth/check`

> **Troubleshooting Backend:**
>
> | Error | Solusi |
> |-------|--------|
> | `mvn is not recognized` | Maven belum masuk PATH — tambahkan folder `bin` Maven ke Environment Variables |
> | `invalid target release: 17` | JDK belum terinstall atau versi terlalu lama — install JDK 17+ |
> | `Failed to find .war file` | Jalankan `mvn clean package` dulu sebelum `cargo:run` |
> | `Address already in use: 8080` | Port 8080 sudah dipakai — tutup program lain yang memakai port tersebut |

### Langkah 4: Install Dependencies dan Jalankan Frontend

Buka **terminal/CMD baru** di root project:

```bash
cd Frontend
npm install
npm run dev
```

**Penjelasan perintah:**
- `npm install` — Install semua dependency dari `package.json`
- `npm run dev` — Jalankan Next.js development server

**Frontend akan berjalan di:** `http://localhost:3000`

> **Troubleshooting Frontend:**
>
> | Error | Solusi |
> |-------|--------|
> | `npm is not recognized` | Node.js belum terinstall — download dari nodejs.org |
> | `ECONNREFUSED localhost:8080` | Backend belum jalan — jalankan backend terlebih dahulu |
> | `Module not found` | Hapus `node_modules` dan `package-lock.json`, lalu `npm install` ulang |

### Langkah 5: Akses Aplikasi

1. Pastikan **backend** sudah jalan di port `8080`
2. Pastikan **frontend** sudah jalan di port `3000`
3. Buka browser: **http://localhost:3000**

### Urutan Menjalankan

```
Terminal 1 (Backend):
cd backend-java
mvn clean package cargo:run

Terminal 2 (Frontend):
cd Frontend
npm install    (hanya pertama kali)
npm run dev
```

---

## Struktur Folder

```
Greenmarket-BE-Java/
│
├── backend-java/                      # Backend Java (Jakarta EE + Maven)
│   ├── src/main/java/com/greenmarket/
│   │   ├── controller/                # 15 REST API Servlets
│   │   ├── service/                   # 7 Business Logic Services
│   │   ├── dao/                       # 14 DAOs (3 interface + 11 implementasi)
│   │   ├── model/                     # 16 Entity Classes
│   │   └── util/
│   │       └── DBConnection.java      # Koneksi Supabase
│   ├── src/main/webapp/
│   │   ├── WEB-INF/
│   │   │   └── web.xml                # Servlet configuration
│   │   └── index.html
│   └── pom.xml                        # Maven dependencies & plugins
│
├── Frontend/                          # Frontend Next.js
│   ├── src/
│   │   ├── app/                       # Page components (14 halaman)
│   │   │   ├── dashboard-buyer/       # Dashboard pembeli
│   │   │   ├── dashboard-seller/      # Dashboard penjual
│   │   │   ├── login/                 # Halaman login
│   │   │   ├── register/              # Halaman registrasi
│   │   │   ├── keranjang/             # Keranjang belanja
│   │   │   ├── pembayaran/            # Pembayaran
│   │   │   ├── pesanan/               # Tracking pesanan
│   │   │   ├── katalog-detail/        # Detail produk
│   │   │   └── ...
│   │   ├── components/                # Reusable UI components
│   │   ├── hooks/                     # Custom React hooks
│   │   └── lib/
│   │       └── api.ts                 # API base URL configuration
│   └── package.json                   # Dependencies
│
├── database/                          # File SQL database (upload manual)
├── docs/                              # Laporan & diagram (upload manual)
├── .gitignore
└── README.md
```

---

## API Endpoints

Base URL: `http://localhost:8080/backend-java/api`

### Authentication — `/api/auth/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| POST | `/auth/register` | Registrasi akun baru (Buyer/Seller) |
| POST | `/auth/login` | Login dan buat session |
| GET | `/auth/check` | Cek session yang aktif |
| DELETE | `/auth/logout` | Logout |

### Users — `/api/users/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/users` | Daftar semua user |
| GET | `/users/:id` | Detail user |
| PUT | `/users` | Update data user |
| DELETE | `/users/:id` | Hapus user |

### Products — `/api/products/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/products` | Daftar semua produk |
| GET | `/products?kategori=id1,id2` | Filter produk berdasarkan kategori |
| GET | `/products?search=keyword` | Cari produk |
| GET | `/products?seller=id` | Produk milik seller tertentu |
| GET | `/products/:id` | Detail produk |
| POST | `/products` | Tambah produk baru (Seller) |
| PUT | `/products` | Update produk (Seller) |
| DELETE | `/products/:id` | Hapus produk (Seller) |

### Categories — `/api/categories/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/categories` | Daftar semua kategori |
| GET | `/categories/:id` | Detail kategori |
| POST | `/categories` | Tambah kategori |
| PUT | `/categories` | Update kategori |
| DELETE | `/categories/:id` | Hapus kategori |

### Cart — `/api/carts/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/carts?userId=id` | Daftar keranjang user |
| POST | `/carts` | Tambah item ke keranjang |
| PUT | `/carts` | Update jumlah item |
| DELETE | `/carts/:id` | Hapus item dari keranjang |

### Transactions — `/api/transactions/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| POST | `/transactions/checkout` | Checkout keranjang |
| GET | `/transactions` | Daftar transaksi user |
| GET | `/transactions/:id` | Detail transaksi |
| PUT | `/transactions` | Update status transaksi |

### Transaction Details — `/api/transaction-details/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/transaction-details?transaksiId=id` | Detail item dalam transaksi |

### Payments — `/api/payments/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/payments` | Daftar pembayaran |
| GET | `/payments/:id` | Detail pembayaran |
| PUT | `/payments` | Update status pembayaran |

### Payment Methods — `/api/payment-methods/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/payment-methods` | Daftar metode pembayaran |
| POST | `/payment-methods` | Tambah metode pembayaran |

### Stores — `/api/stores/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/stores?userId=id` | Toko milik user |
| GET | `/stores/:id` | Detail toko |
| POST | `/stores` | Buat toko baru |
| PUT | `/stores` | Update data toko |

### Addresses — `/api/addresses/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/addresses?userId=id` | Daftar alamat user |
| POST | `/addresses` | Tambah alamat |
| PUT | `/addresses` | Update alamat |
| DELETE | `/addresses/:id` | Hapus alamat |

### Shipping Services — `/api/shipping-services/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/shipping-services` | Daftar jasa pengiriman |

### Tracking Logs — `/api/tracking-logs/*`
| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| GET | `/tracking-logs?transaksiId=id` | Tracking log transaksi |
| POST | `/tracking-logs` | Tambah tracking log |

---

## Fitur Aplikasi

### Untuk Buyer (Pembeli)
- Registrasi dan login sebagai pembeli
- Dashboard dengan carousel banner dan katalog produk
- Filter produk berdasarkan kategori
- Pencarian produk
- Detail produk dengan informasi penjual
- Keranjang belanja (tambah/hapus item)
- Checkout dan pembayaran
- Tracking pesanan

### Untuk Seller (Penjual)
- Registrasi dan login sebagai penjual
- Dashboard dengan statistik (total produk, stok, stok menipis)
- Katalog produk dengan badge "Produk Saya"
- Kelola inventaris produk
- Tracking pesanan masuk

### Untuk Admin
- Manajemen user
- Manajemen produk
- Manajemen kategori

---

## Kontributor

| Nama | NIM |
|------|-----|
| [Nama Anggota 1] | [103012400] | 
| [Muhammad Arief Faruq Wafdan] | [103012400246] | 
| [Handri Athallah Saputra] | [103012400328] | 
| [Muhammad Ghizar Al Hasan] | [103012400235] |
| [Keanu Fibie Dimasyqi] | [103012430045] |

---

## Lisensi

Project ini dibuat untuk memenuhi **Tugas Besar Pemrograman Berorientasi Objek (PBO)** Semester 4.
