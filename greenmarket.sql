--
-- PostgreSQL database dump
--

\restrict S0b6A4dM1pS6dlSaEHT6xwa3hdQsz0yj2cXupV8sxSo0VSfpRiCb0L4RzUElV7R

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- Name: Role; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public."Role" AS ENUM (
    'BUYER',
    'SELLER',
    'ADMIN',
    'GUEST'
);


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Alamat; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."Alamat" (
    id_alamat text NOT NULL,
    id_user integer NOT NULL,
    nama_penerima text NOT NULL,
    nomor_hp text NOT NULL,
    alamat_lengkap text NOT NULL
);


--
-- Name: Detail_Transaksi; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."Detail_Transaksi" (
    id_detail text NOT NULL,
    id_transaksi text NOT NULL,
    id_produk text NOT NULL,
    kuantitas integer NOT NULL,
    harga_satuan integer NOT NULL,
    subtotal integer NOT NULL
);


--
-- Name: Jasa_Kirim; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."Jasa_Kirim" (
    id_jasa text NOT NULL,
    nama_jasa text NOT NULL,
    harga_pengiriman integer NOT NULL,
    estimasi_waktu text NOT NULL
);


--
-- Name: Kategori_Produk; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."Kategori_Produk" (
    id_kategori text NOT NULL,
    nama_kategori text NOT NULL
);


--
-- Name: Keranjang; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."Keranjang" (
    id_keranjang text NOT NULL,
    id_user integer NOT NULL,
    id_produk text NOT NULL,
    created_at timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


--
-- Name: Metode_Pembayaran; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."Metode_Pembayaran" (
    id_metode text NOT NULL,
    nama_metode text NOT NULL,
    kode_metode text NOT NULL
);


--
-- Name: Pembayaran; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."Pembayaran" (
    id_pembayaran text NOT NULL,
    id_transaksi text NOT NULL,
    status_pembayaran text NOT NULL,
    tanggal_pembayaran timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


--
-- Name: Produk; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."Produk" (
    id_produk text NOT NULL,
    id_user_seller integer NOT NULL,
    id_kategori text NOT NULL,
    nama_produk text NOT NULL,
    deskripsi text NOT NULL,
    harga integer NOT NULL,
    stok integer NOT NULL,
    status_produk text NOT NULL,
    created_at timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    foto_produk text,
    konten_deskripsi text,
    catatan_penjual text,
    foto_produk_list text[] DEFAULT '{}'::text[]
);


--
-- Name: Toko; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."Toko" (
    id_toko text NOT NULL,
    id_user integer NOT NULL,
    nama_toko text NOT NULL,
    email_bisnis text,
    alamat_toko text,
    created_at timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


--
-- Name: TrackingLog; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."TrackingLog" (
    id_log text NOT NULL,
    id_transaksi text NOT NULL,
    status text NOT NULL,
    waktu timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


--
-- Name: Transaksi; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."Transaksi" (
    id_transaksi text NOT NULL,
    id_user integer NOT NULL,
    id_alamat text NOT NULL,
    id_jasa_kirim text NOT NULL,
    id_metode_pembayaran text NOT NULL,
    status_transaksi text NOT NULL,
    tanggal_transaksi timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    total_harga integer DEFAULT 0 NOT NULL
);


--
-- Name: User; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."User" (
    id integer NOT NULL,
    username text NOT NULL,
    email text NOT NULL,
    password text NOT NULL,
    "createdAt" timestamp(3) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    role public."Role" DEFAULT 'BUYER'::public."Role" NOT NULL
);


--
-- Name: User_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."User_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: User_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."User_id_seq" OWNED BY public."User".id;


--
-- Name: User id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."User" ALTER COLUMN id SET DEFAULT nextval('public."User_id_seq"'::regclass);


--
-- Data for Name: Alamat; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."Alamat" (id_alamat, id_user, nama_penerima, nomor_hp, alamat_lengkap) FROM stdin;
7f90895c-a8fe-4522-bd7a-97cf32812095	6	atta gledek	081222381331	Indonesian yummy street food, Jalan Lengkong Besar, Bandung, Indonesia
bcf49f13-871f-4f98-828b-fae268b39bfb	4	Billy	081122223333	Perumahan Batu Aji Permai, Batam, Indonesia
ce76cda3-ddd5-47c1-9687-cc3644ac09bd	12	Arisu	082198765432	Telkom University, Jl. Telekomunikasi No. 01, Bandung, Indonesia
7b1c9ab4-bc62-4d50-ba7c-fc0aa9d05ca1	13	testuser202	088822223333	Jalan Sasak Gantung, Bandung, Indonesia
ab1eed71-6300-4e81-bc61-1bbe6ff75fb9	18	Keanuzx	09999999999999999999	Telkom University, Jl. Telekomunikasi No. 01, Bandung, Indonesia
a323fa8f-1f72-4dd7-8642-503c80db9945	8	kakashi hatake	08473	yntkts
7055bd15-047d-4ad5-ba0c-0091fecc6917	16	Arief Wafdan	+628310838101	Gang Karyamas II, Bandung, Indonesia
ec856292-8040-4af1-b93c-54577c61d9c7	20	Keanuzx	+6282119996466	Telkom University, Jl. Telekomunikasi, Bandung, Indonesia
f9b99d1e-499b-4212-ae6c-3caddcf8d733	11	Test	+62888888888	Gang Malabar 1, Bandung, Indonesia
9b054d17-8fe5-49d6-8979-78a54e1d91af	25	Testi	+62888888889	Grand Malabar Hotel, Jalan Malabar, Bandung, Indonesia
3d682f04-36d0-4135-abab-fac0aa019c38	43	Oioi	+621111111111111	Telkom University Landmark Tower (TULT), Jalan Haji Udju, Dayeuhkolot, Indonesia
957967be-ddbe-4673-8376-886285c44474	1	qia	+6282233334444	Bukit Baruga, Makassar, Indonesia
69459d8c-2d27-4043-8f44-f0bc75a8ba40	10	Test	+62888888888	Gang Malabar 1, Bandung, Indonesia
3770dfbb-3c03-4c9d-9774-d18c4397fcdb	31	Keanu	+628211199646665	Telkom University Landmark Tower (TULT), Jalan Haji Udju, Dayeuhkolot, Indonesia
f085d0c0-8f4d-418d-9f86-7a6fb55987ce	15	Keanu	+622321312312313	Keanuiomano Stream, Waimea, United States
861491b8-f890-4ecd-8ce1-410c5aa81d14	40	Test	+62888888888	 Gang Malabar 1, Bandung, Indonesia
1720c511-2985-4748-bc17-c41bd295c42a	30	Test API Diperbarui	+6282111222333	Jl. Sudirman No. 99, Bandung 40123
ce604028-cd04-46d3-a25b-e797efa2e98b	35	rrrr	+622493859395839	333, Деснянська селищна громада, Україна
ceebe198-ca0d-4b3b-8426-1dc4ec450597	28	Test	+62888888888	Gang Malabar 1, Bandung, Indonesia
db8bd1f6-7fbe-482f-9a8c-27ba6f611485	45	wafdan	+62488323843	Telkom University, Jl. Telekomunikasi No. 01, Bandung, Indonesia
57071156-ed45-4c74-8b73-df73af477c79	10	Test	+62888888888	Gang Malabar 1, Bandung, Indonesia (Info detail alamat)\n
53e6e213-c45f-403e-b405-33f38672d22d	46	Test	+62888888888	Jalan Gudang Utara, Bandung, Indonesia
53766d1d-119b-4299-9183-1e550e21e293	28	Test	+62888888888	Telkom University, Jalan Raya Bojongsoang, Bojongsoang, Indonesia
67041740-7484-4127-8f32-fe88d462a7ac	48	Keanu	+622131231231231	Telkom University Landmark Tower (TULT), Jalan Haji Udju, Dayeuhkolot, Indonesia
8ac1b28c-ee25-413e-b4d0-2742ae63a1ae	50	bobobi	+621234556678	bandung
\.


--
-- Data for Name: Detail_Transaksi; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."Detail_Transaksi" (id_detail, id_transaksi, id_produk, kuantitas, harga_satuan, subtotal) FROM stdin;
4df04cb6-5273-4cec-bda4-a56d2d1cd0b1	4428e0b5-abd4-48b7-97b3-542c8df4c9b5	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
ed7d73ab-d4cf-42b1-80a6-b4800cff027e	4b132211-858f-4422-a829-cb897cc3d292	767eea1d-08cf-48e8-882f-150ab1326dd9	2	10000	20000
a3d632fa-b223-4459-9f10-b5aa8eaf7618	00456957-d2b7-48ab-91d2-cdb6c2aa73ea	767eea1d-08cf-48e8-882f-150ab1326dd9	2	10000	20000
d706a850-bcb0-4cbd-a09e-e8786d26eab6	c578e56e-4a29-40d3-b686-9e0b6d3f5e5e	767eea1d-08cf-48e8-882f-150ab1326dd9	2	10000	20000
880973c5-97b3-438f-9812-e8e571df85fe	4947f3f5-e9f5-4cbf-ac28-3936b2690405	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	4	1000	4000
0d2e7892-76f9-46bb-8b45-a7996508d4da	694baad0-a1c6-441c-a0a3-2afaa6796729	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
ce0bde03-683b-44d6-a741-9b41c5424f03	864e1f8a-bf6f-4b15-8b83-fe82b9a7ebac	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
633abdb7-2ae4-4110-8892-69154219ae6e	9e00fea2-f6bf-4ac5-a4b9-e7d6ea88ed38	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
58b4724e-328e-4485-a902-4ef2fff3d737	4d9d8c61-3c09-4a12-b645-941d55743c0c	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
a615fb3d-ef4b-430a-a42d-8c58594221c8	40215c2b-ae23-4e9e-9d51-ec4f0c94664b	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
19e44dbc-1001-429f-9360-6e69438f78f3	66aea0b9-abec-4397-921f-f99eda1d4ed0	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
d814e7a9-c0c0-4331-a21e-bb44e3502b71	b25f110c-907d-414b-8e03-2c14081b2fb7	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
2b591131-753d-45ea-b46f-8c0e6f005bf5	38e5c9ad-512c-4e31-b9dd-15929a359997	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
dd59ea90-430a-4e99-a81c-59d99e4cb1e0	bfeb09f1-8479-48d3-a2d1-f4de07908f3a	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
b54e9085-e0de-417b-9304-de47030008a9	05cf8f67-ea7f-4621-9a88-b5677a6bccfd	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
656490bd-31fe-4854-929f-f64a5803169d	05cf8f67-ea7f-4621-9a88-b5677a6bccfd	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
94e4185d-db3c-4697-9128-87760b9d59c1	dd6427a7-82dc-4cab-a0e0-c99b68b99371	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
3e5d524b-513c-41ad-87c7-e500eb79d9ab	f5d4ae1f-1bc8-4538-98ba-3b904246a28b	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
3e66b187-c0df-42e0-a466-b15f84a11f5d	c2004f1f-7025-438e-9282-fa2873934ff9	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	1	1000	1000
5c436c65-38b6-4b35-ad00-37bfbf592e60	70d3aac9-a7ad-4e4d-b64e-78e039a13aed	89c8e362-579d-4f5e-abb9-74008b0c5f06	1	1	1
e8d0c493-6b69-40d6-9aef-710f35848c1f	0975a9d8-1599-453e-8727-53946ff8b96e	89c8e362-579d-4f5e-abb9-74008b0c5f06	1	1	1
f3ec30d9-2888-4146-a8f3-7a8a216d5301	0975a9d8-1599-453e-8727-53946ff8b96e	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	7	1000	7000
a65499eb-365e-4560-aecf-60ddb39bdcfe	0d885946-94b6-4ee0-873e-39aee013669c	5a3f1d4c-b2fc-4f4e-9ac9-45fd85bd5696	1	20000	20000
b78dc96f-ec49-457b-a63a-f00d03b3ca3e	ede8d5ad-5b2f-42e6-b721-06efd7b5e57a	767eea1d-08cf-48e8-882f-150ab1326dd9	4	10000	40000
f6c9daf3-5427-4979-aac0-3e1056a5c7d4	08c88e4c-ebb8-4fe8-ac69-ec75ee03c771	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
0a0468aa-3a18-4c43-b930-753dcb5cb952	59187485-d31c-4bf9-a4ca-d83c607b91b7	e6c5448a-6c00-431b-a22e-e2637c5d33f2	1	200000	200000
41a9aecd-f9eb-4f82-836e-57a6cbc5a95a	5b8e104c-5363-4f09-95c6-835c2190302c	e6c5448a-6c00-431b-a22e-e2637c5d33f2	1	200000	200000
ff8a52a5-1c89-4f45-8292-ddb56b486c93	1dbcdc5e-10fe-43e9-a85d-9a3ea064771a	e6c5448a-6c00-431b-a22e-e2637c5d33f2	1	200000	200000
7178f344-da25-44cb-80c6-76d942ee990e	e3331a25-7c15-4fd2-b9af-7b02da3d965b	4ae9d78b-7e28-4855-a7e5-4ccdc44d7fe3	10	10000	100000
241156f8-5e78-4b7d-ad05-a30698202cad	257da69f-2189-4e6f-a3ac-d59bb2fdb47a	5a3f1d4c-b2fc-4f4e-9ac9-45fd85bd5696	1	20000	20000
b0b9851d-e305-4188-91b7-f8e1b57197e8	40136494-19b9-49db-aa31-ba312b1a5f67	5a3f1d4c-b2fc-4f4e-9ac9-45fd85bd5696	1	20000	20000
dc771ca9-9686-4153-b0de-c6b3f6f9e3ff	267aafe6-e636-4f7e-a7a0-1538529ee2ef	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
83e56c33-6fb3-4e63-97c7-6fc21771f649	fb1dcec7-6a16-4b79-ad49-bc5c19aad70e	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
099911e8-9def-4f03-80da-b69077be82d7	72bbd75e-a492-4025-a3d2-d3243ab36901	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
caa0872e-6fe4-4e1e-822e-78185c40778c	041e216f-5995-49a9-a481-ec7b3fdf0f79	5a3f1d4c-b2fc-4f4e-9ac9-45fd85bd5696	1	20000	20000
9a5b492d-eb61-4864-8066-f209f89822b4	fda4b075-f7ac-4cb2-af32-75eb2310c5a1	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
eeb1c709-012a-4b7b-b97f-43d2511421ac	2d7283ea-2ed0-4b32-b1fd-e86eb2c77607	5a3f1d4c-b2fc-4f4e-9ac9-45fd85bd5696	1	20000	20000
e5a3c690-d42f-45eb-92ce-414b83300acc	431c3215-fd5d-436c-8e9d-9ae3beb1acb7	4ae9d78b-7e28-4855-a7e5-4ccdc44d7fe3	1	10000	10000
a2800e4d-3eab-4c49-ab3c-b6959c09bbf8	04f26057-78bb-46ef-826c-f38620b27ea4	43ea2e3a-7909-4791-b9a6-ca49f3c5ba83	1	1	1
93c0edd3-a387-4e5e-95ad-e0ba3582650a	1572c31a-49e5-4611-bbd2-c281ffc11981	43ea2e3a-7909-4791-b9a6-ca49f3c5ba83	1	1	1
f2973bc3-fc72-49aa-9dfa-28c6bad2ace4	ae81d4a9-418c-4668-9f96-617c122dbcf1	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	15000	15000
7c7f5ab5-2306-4c4a-b715-a5ef9cdb9f18	2c9cb8c4-5b2e-49b6-8987-fd0c3c208fc0	43ea2e3a-7909-4791-b9a6-ca49f3c5ba83	1	1	1
0fa0808a-4e66-43e6-960d-7f9d5905975c	5ed2be0b-c4a4-42fe-baf6-888ee2e160b1	43ea2e3a-7909-4791-b9a6-ca49f3c5ba83	1	1	1
5bfa6f6a-6f18-45fa-9cee-76a02ff2fc42	5cff752e-70be-4460-b62f-be1ca2a62d13	54219152-31f8-42d4-ad08-69bd8d832fda	1	2000	2000
1271d5e8-da1d-4aa5-8d4a-ff42bbec9254	72caa5ce-5b7c-4e68-9f66-c83c60b974cc	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
63de0311-8a9a-4b5b-ac6b-657268d23402	3dafbe1a-ecb5-4156-9d68-dbd43d1f6e16	f8b002c7-ba96-47c0-a3cc-16a8afcd2b4f	2	500	1000
d5f5145c-cdff-4636-9f3a-e6ed89ad78b2	02c75cf9-37c4-4764-8280-5fbf6b89daf4	54219152-31f8-42d4-ad08-69bd8d832fda	1	2000	2000
4178b757-2d06-4a7d-89c3-b84e4056aeb3	405ece06-0b1c-44fd-8fbc-8af5f115061c	54219152-31f8-42d4-ad08-69bd8d832fda	1	2000	2000
8dbbebe5-025a-4ea9-ab0d-d5bbfd46a243	4c5cd29c-f95b-4efa-8590-83c376f03895	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
04679261-4441-41ce-b13c-c2da06575b95	63f514ec-a748-432e-aee2-a0216f8f2fd9	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
915d2fb2-d984-426a-b843-5919483f5902	63f514ec-a748-432e-aee2-a0216f8f2fd9	5a3f1d4c-b2fc-4f4e-9ac9-45fd85bd5696	2	20000	40000
da0418e5-44ec-438f-9432-4242f2a8ab3e	cbf9dd54-7453-42e4-bfb0-6e2c8143e747	54219152-31f8-42d4-ad08-69bd8d832fda	1	2000	2000
a8eb700f-546b-4479-ae85-bded37091747	853d06d6-a69c-4928-b4cf-ba5ede973fab	f8b002c7-ba96-47c0-a3cc-16a8afcd2b4f	2	500	1000
f4384b0d-f22d-4ed7-8456-6fe31366cd24	a5552ab7-625f-471d-88cd-d5fdd79e8dae	f8b002c7-ba96-47c0-a3cc-16a8afcd2b4f	2	500	1000
be1c17fd-1067-45ed-808d-5ebcde2caa91	7750855c-b4ca-4d8d-81f7-dc840ef8b56a	54219152-31f8-42d4-ad08-69bd8d832fda	1	2000	2000
a396f24d-5b89-4793-8930-469ee55698b3	6d1feb1f-ca06-4860-9188-8280ef9e1675	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
d4fc3c41-70ad-42f3-bece-f7ff15dc9425	02b0b57b-1277-42d4-818e-2f4fe7ff9569	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
505c98b1-871c-4067-81d1-85da15a6122b	806c0868-75f8-495a-8242-95a1489b419e	43ea2e3a-7909-4791-b9a6-ca49f3c5ba83	1	1	1
626c036f-c6a8-4379-b1c3-e73e1b656402	fc64ffa4-d795-4757-a625-353a847dca2d	f8b002c7-ba96-47c0-a3cc-16a8afcd2b4f	2	500	1000
8285e80c-9713-4f4f-9201-b46ec7e49678	cabbb8d3-770f-491c-98ed-28785e80ca82	f8b002c7-ba96-47c0-a3cc-16a8afcd2b4f	1	500	500
74fadc78-f491-404c-a6cb-7642044cf95b	4367ee14-198c-4196-89a0-b22034d5651f	c01c5877-8d44-4272-acb4-3ec460a4086c	1	1	1
c007581f-e6df-48d8-a679-2f771c1ea7de	8bbaedc1-252c-4707-bb15-7788a91a7d50	c01c5877-8d44-4272-acb4-3ec460a4086c	1	1	1
74022486-529a-4155-af41-9ac7f9b66d8e	7a719e27-72d8-4b5d-bdcc-01adb9e5505c	c01c5877-8d44-4272-acb4-3ec460a4086c	1	1	1
f406f756-e921-43c8-83ec-a4c1b659464c	309751ee-6352-4001-9746-d6873f0022a8	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
dad45b15-c382-41e2-9ce0-50c8f4b7e9ef	37dba90e-20c8-4f28-87fb-e2570c4aa0ad	4ae9d78b-7e28-4855-a7e5-4ccdc44d7fe3	1	10000	10000
abbd7000-cabd-4f6f-bd9d-5206ec70b93a	d904fefb-6838-46df-96f0-8933744484d5	5a3f1d4c-b2fc-4f4e-9ac9-45fd85bd5696	20	20000	400000
1ec002ee-320e-4132-874c-b47db3eeb8ba	1ab19ae2-805a-4ddd-b017-8995bf8d16e7	360f5e85-dc9e-4854-9e38-7764259216af	1	10000000	10000000
0517b374-6dae-4a71-9ce8-bae87e3038f0	0fe3426b-f88b-4231-962e-4e52e8758abd	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
5a3ad105-8aa5-4b65-9004-d19e652c66f6	3a1f8dea-5fb5-4cab-a5dd-284ccce22ce5	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
0e728353-0601-4f6d-b82e-7667d48eaf82	1d164501-3f05-4705-88fa-3f70bd09ef7a	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
d392e437-ad3d-4b18-be7d-b7d46846cbc0	0dc709ea-5331-43c1-b511-581067f3b6b4	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
5b179e0e-831d-404a-98c8-9cb7188f1d67	1eac65d5-f52c-4ea7-a2c4-0fc3d9178729	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
6b1dd09f-5502-41c8-a6ad-11fd2551ba75	0498270b-284d-445d-9534-ace1e4eaa578	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
7838fa13-259b-4f68-8f9a-2d0b1ab63d5a	70126e0e-e774-4787-b325-e218bfb6b770	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
a35a03c6-52eb-4788-b82e-dd124b6d0a5f	bf46805c-3b88-4105-90c9-7ffe1badbc87	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
6da0030d-90ed-491e-bc29-d1c928c82e24	c30c0c3b-288a-4e71-872a-7b17d38920e4	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
3e58522b-a785-42c0-ba26-e6f6233cc812	e3eff9a4-eb5d-46e2-8593-840729b1721d	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
59b39a38-0848-41ed-a82d-b0582663b0c1	1a9b189f-df1c-4e50-a1a4-856fe5b24dfe	906e9c03-5a78-4b0c-a9c0-a953869551b5	1	18000	18000
6863a51b-457c-4f3d-94f5-f82a676f33e1	f2eb9369-20dd-494f-982a-4b0ebcafc10f	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
2071ce0b-2c86-4357-b3e7-7fcc5a9be34e	4fc1f246-d1e9-4971-87ea-973409649aae	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
6beae41d-dbf5-4024-acb3-ad5e7d6543d1	6ca2219c-36ac-4d47-bb43-f3a17865f2f7	4f8f1dc6-b3ac-4cc7-9485-8a042fdead1d	2	1000	2000
09827bc0-a232-47b9-879e-93e2f9bda76c	1f131b56-9d1b-412b-ab98-23bc395a9b43	360f5e85-dc9e-4854-9e38-7764259216af	2	10000000	20000000
e86b6daf-c3f9-4c66-87ff-6629592a91ea	578f86c6-4889-43ca-ab17-8d305a7f0cde	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
6523640b-6f03-4d21-8c4e-80f18449726e	e6dd354c-914d-4775-86d7-6dcad9e33ca1	767eea1d-08cf-48e8-882f-150ab1326dd9	1	10000	10000
350104fc-0e03-4317-9d0b-9e58ac4f9305	613466b1-6a59-4873-8ddd-86237fac40df	360f5e85-dc9e-4854-9e38-7764259216af	1	10000000	10000000
3b82b1a0-f541-44eb-a17a-b06f026ae884	54de8af4-31d5-4648-a38b-7db386e3731e	360f5e85-dc9e-4854-9e38-7764259216af	1	10000000	10000000
1fd208a1-bc65-4ec5-b313-388b0e3d8ec1	77c43e8c-33f4-44ee-aa8a-cf89bb6ec47d	f8b002c7-ba96-47c0-a3cc-16a8afcd2b4f	2	500	1000
a29edae7-cea6-4271-a798-ce329fbb5965	e1ea49f6-1e28-407b-9626-6821d1adc217	2217d02f-ff6a-4459-8827-66d2ccff714e	1	15000	15000
3f915690-02eb-4920-acac-545c1c176c49	71f5a35a-783b-4646-949f-d6c38aaa740e	2217d02f-ff6a-4459-8827-66d2ccff714e	1	15000	15000
234d2a9c-996f-47d8-bc1d-b10d958b4a8c	ecf20e43-f1ec-4f73-a2ae-219eed359453	2217d02f-ff6a-4459-8827-66d2ccff714e	1	15000	15000
8284ea01-eb32-44e4-a902-a14ff6dfae2f	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	d2bbc193-ec7e-45bd-ae55-dcf53ce4042c	1	10000	10000
2119cefe-de2d-4f06-8852-6208bea96121	564d0220-c1ce-438e-9731-4ed4ce6ccc0e	393a2e70-ea13-4e44-b869-6eebd5b309fd	1	10000	10000
4ecda967-abea-49b6-a9f5-ec3509ade47f	489b575a-6157-4693-9960-01b1085eca41	393a2e70-ea13-4e44-b869-6eebd5b309fd	1	10000	10000
4fe3fbd1-21bf-47d0-bd78-909e4049d155	7686fff2-52b9-405d-9ef0-3458232e95eb	393a2e70-ea13-4e44-b869-6eebd5b309fd	1	10000	10000
6440f0d4-e60e-477a-8d6a-83e94c8d3fe3	b7f6a060-e85a-4dfc-8489-0cd86459e237	d2bbc193-ec7e-45bd-ae55-dcf53ce4042c	1	10000	10000
862e4fb5-947f-4c77-8477-c9be5dc1e212	a0cd08bb-d823-430b-bf2e-23d5c44e0762	d2bbc193-ec7e-45bd-ae55-dcf53ce4042c	1	10000	10000
554e2a83-4b93-4a06-8d52-f40d905aa0d4	2417503a-c011-44c5-8ab3-9a0ce567fcdc	0f81584b-1320-440b-a681-d016ef6c40d2	1	1000	1000
01cb46e8-1682-46bd-8b39-e9277106623c	f911b4b8-08e7-46e7-8f1a-869136c4288e	d2bbc193-ec7e-45bd-ae55-dcf53ce4042c	1	10000	10000
c9fae30e-406a-413c-94e7-703cf4b72081	2bc0fe7c-772b-4d59-aae9-c983ce079eb4	2217d02f-ff6a-4459-8827-66d2ccff714e	1	15000	15000
7ed0d881-e274-4257-b6ba-6825d853c8d2	eb82ad63-71e3-4b78-a844-50559af1b1e1	43ea2e3a-7909-4791-b9a6-ca49f3c5ba83	1	1	1
e79d0a5d-7d66-48f7-9378-81d136fb65a3	b18ffbc6-6926-4005-a6e5-9c73c533a131	393a2e70-ea13-4e44-b869-6eebd5b309fd	1	10000	10000
1d312af6-81d9-4556-8535-c27b7c60ae0c	141da71e-2fa7-4d85-90dc-fb55324deef3	393a2e70-ea13-4e44-b869-6eebd5b309fd	1	10000	10000
836b9747-7682-460e-8828-74ba9a16fb7d	43a5b414-7199-407f-9064-c29c11aacf12	2217d02f-ff6a-4459-8827-66d2ccff714e	1	15000	15000
795e24a2-e7ad-4ff1-95d6-dfc15bb23a66	63ffcdd2-adce-4230-9c61-630e410acfc5	2217d02f-ff6a-4459-8827-66d2ccff714e	1	15000	15000
c597e14e-c43d-462d-a3e0-5b6882e79d09	4080a0ea-e99b-49c4-9c63-ea12659dde9b	360f5e85-dc9e-4854-9e38-7764259216af	1	10000000	10000000
\.


--
-- Data for Name: Jasa_Kirim; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."Jasa_Kirim" (id_jasa, nama_jasa, harga_pengiriman, estimasi_waktu) FROM stdin;
a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	Green Reguler	6500	Estimasi tiba 9 - 12 Agustus
08cd80fa-9de3-47f2-9ebe-372ae183adcb	Green Express	12000	Estimasi tiba 7 - 9 Agustus
35c7744b-33be-4363-8650-7701733452fe	EcoSend Reguler	8500	Estimasi tiba 8 - 11 Agustus
552a879c-f42e-421d-b3f6-abc8d9fbe468	EcoSend Express	14000	Estimasi tiba 6 - 8 Agustus
c318bfba-0ac2-4397-b08c-6792b2447c2a	Leaf Courier Reguler	9000	Estimasi tiba 9 - 12 Agustus
41fe736e-d12d-47e1-9cc4-08bcbe7bbd93	Leaf Courier Express	15000	Estimasi tiba 6 - 8 Agustus
67520e75-a056-447e-85f7-a9f868e043ca	CarbonLite Standard	7500	Estimasi tiba 10 - 13 Agustus
2e682327-6c91-418d-8354-56067c612758	Green Cargo	1	Estimasi tiba 11 - 14 Agustus
\.


--
-- Data for Name: Kategori_Produk; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."Kategori_Produk" (id_kategori, nama_kategori) FROM stdin;
K001	Reduce
K002	Reuse
K003	Recycle
\.


--
-- Data for Name: Keranjang; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."Keranjang" (id_keranjang, id_user, id_produk, created_at) FROM stdin;
cc3a89e2-c7d7-4732-8aac-dbcc9f88deb7	44	c01c5877-8d44-4272-acb4-3ec460a4086c	2026-05-28 05:55:26.598
5a1e851a-e2e2-4b7c-bb7e-51ff1fd81f8b	12	0c8775a4-2ea1-4fef-b2b0-055cdc687e34	2026-05-14 13:53:23.153
d18e0d53-c034-4c93-9b93-f82aff15c92e	12	c01c5877-8d44-4272-acb4-3ec460a4086c	2026-05-30 04:40:01.314
e9b5992e-adad-47b9-9ef6-ab3579a48335	12	a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	2026-05-30 06:05:57.021
249d275d-8131-4d47-a5eb-d5d3ae0e63cf	50	2217d02f-ff6a-4459-8827-66d2ccff714e	2026-06-15 08:09:21.366
b6565378-7772-4432-ac37-31c70a6909f4	8	767eea1d-08cf-48e8-882f-150ab1326dd9	2026-05-17 22:24:53.038
b7ea58ca-d853-4108-beb8-befc7f2e1bac	31	f8b002c7-ba96-47c0-a3cc-16a8afcd2b4f	2026-05-25 05:26:19.643
6cd60e02-c27c-4473-9325-2f03f679a038	15	43ea2e3a-7909-4791-b9a6-ca49f3c5ba83	2026-05-26 03:09:57.484
cefccfff-9323-42a7-8ad6-4300295eebe8	25	5a3f1d4c-b2fc-4f4e-9ac9-45fd85bd5696	2026-05-27 08:54:35.347
\.


--
-- Data for Name: Metode_Pembayaran; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."Metode_Pembayaran" (id_metode, nama_metode, kode_metode) FROM stdin;
36d8c930-1d16-481d-a286-8d88df21e5c3	QRIS	QRIS
ceb331a0-1415-4dcc-a8ce-d71c2150947b	Cash	CASH
\.


--
-- Data for Name: Pembayaran; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."Pembayaran" (id_pembayaran, id_transaksi, status_pembayaran, tanggal_pembayaran) FROM stdin;
a81d34ab-3f2f-41f0-8967-7775ee980d2b	4428e0b5-abd4-48b7-97b3-542c8df4c9b5	MENUNGGU_PEMBAYARAN	2026-05-17 13:14:33.93
3e7d7751-9699-4f4a-9f16-47b0e9ba1cd0	4b132211-858f-4422-a829-cb897cc3d292	MENUNGGU_PEMBAYARAN	2026-05-17 14:44:08.92
c9b85ac2-ab51-4a37-bec8-a54737b0141b	00456957-d2b7-48ab-91d2-cdb6c2aa73ea	MENUNGGU_PEMBAYARAN	2026-05-17 14:44:10.199
66d0c4b3-88da-48a0-a316-7a6f37b36f6a	c578e56e-4a29-40d3-b686-9e0b6d3f5e5e	MENUNGGU_PEMBAYARAN	2026-05-17 14:44:27.779
53c1b077-9fb9-499c-980a-e46727397207	4947f3f5-e9f5-4cbf-ac28-3936b2690405	MENUNGGU_PEMBAYARAN	2026-05-17 14:45:32.44
d53bb3a1-13e2-464a-a874-7c52bfdf2935	694baad0-a1c6-441c-a0a3-2afaa6796729	MENUNGGU_PEMBAYARAN	2026-05-17 22:57:29.338
a4858796-cfaf-4baa-8fa4-39a82ac3dc24	864e1f8a-bf6f-4b15-8b83-fe82b9a7ebac	MENUNGGU_PEMBAYARAN	2026-05-17 22:57:30.895
e0acfb83-af14-448c-8939-189df08ed3b9	9e00fea2-f6bf-4ac5-a4b9-e7d6ea88ed38	MENUNGGU_PEMBAYARAN	2026-05-17 23:05:12.713
b8e35b00-f30a-4f3f-862c-54f76f69863d	4d9d8c61-3c09-4a12-b645-941d55743c0c	MENUNGGU_PEMBAYARAN	2026-05-17 23:05:14.589
54c8b68c-3486-487f-985a-7bffcf6eb654	40215c2b-ae23-4e9e-9d51-ec4f0c94664b	MENUNGGU_PEMBAYARAN	2026-05-17 23:11:18.808
bbd4882b-b435-49fd-8794-755f2a17c1ae	66aea0b9-abec-4397-921f-f99eda1d4ed0	MENUNGGU_PEMBAYARAN	2026-05-17 23:11:37.365
e7ad7c0d-5bc6-43f9-85c9-54af9ca0d0aa	b25f110c-907d-414b-8e03-2c14081b2fb7	MENUNGGU_PEMBAYARAN	2026-05-17 23:12:07.644
c47aef35-e102-4c1d-9897-53fb8119c186	38e5c9ad-512c-4e31-b9dd-15929a359997	MENUNGGU_PEMBAYARAN	2026-05-17 23:12:32.761
ea3ef631-8e92-4496-9ed8-c4b7050dfa83	bfeb09f1-8479-48d3-a2d1-f4de07908f3a	MENUNGGU_PEMBAYARAN	2026-05-17 23:12:59.121
9d22aa88-532c-4f1b-a221-8f84806b3fd8	05cf8f67-ea7f-4621-9a88-b5677a6bccfd	BAYAR_DI_TEMPAT	2026-05-18 04:13:45.071
2862da3f-1466-4160-8535-838e191c0974	dd6427a7-82dc-4cab-a0e0-c99b68b99371	BAYAR_DI_TEMPAT	2026-05-19 02:34:21.548
46d6cc20-f257-4d6c-b6b4-5e5474009989	f5d4ae1f-1bc8-4538-98ba-3b904246a28b	MENUNGGU_PEMBAYARAN	2026-05-19 02:51:04.938
d5be5cfb-0acd-4b4a-be18-c958d54f8799	c2004f1f-7025-438e-9282-fa2873934ff9	MENUNGGU_PEMBAYARAN	2026-05-19 02:52:12.889
192fdd09-dc20-488a-9cb0-a5566f855239	70d3aac9-a7ad-4e4d-b64e-78e039a13aed	MENUNGGU_PEMBAYARAN	2026-05-19 02:59:02.578
b318514b-9cbb-48af-9d6a-8ee0b1bfc949	0975a9d8-1599-453e-8727-53946ff8b96e	BAYAR_DI_TEMPAT	2026-05-20 00:55:12.187
9e7acaf0-e91f-48bf-a6ab-063b1fd34027	0d885946-94b6-4ee0-873e-39aee013669c	MENUNGGU_PEMBAYARAN	2026-05-20 00:56:42.92
63038efd-957e-4f68-ad81-78492bad0b8c	ede8d5ad-5b2f-42e6-b721-06efd7b5e57a	MENUNGGU_PEMBAYARAN	2026-05-20 01:14:29.254
db3230e9-f241-4a08-b6f7-2d42a57c4d77	08c88e4c-ebb8-4fe8-ac69-ec75ee03c771	BAYAR_DI_TEMPAT	2026-05-20 01:20:02.99
337ec5b1-9f96-45f1-887b-3660a2c97f1a	59187485-d31c-4bf9-a4ca-d83c607b91b7	MENUNGGU_PEMBAYARAN	2026-05-20 03:49:22.832
de854d56-88eb-42e3-bbc8-1b9107193341	5b8e104c-5363-4f09-95c6-835c2190302c	MENUNGGU_PEMBAYARAN	2026-05-20 03:50:55.006
5e55c202-8b9e-400d-b1ff-8163ef34fbc9	1dbcdc5e-10fe-43e9-a85d-9a3ea064771a	MENUNGGU_PEMBAYARAN	2026-05-20 03:51:06.545
34c100a1-0aeb-4fce-911a-7b12375f631a	e3331a25-7c15-4fd2-b9af-7b02da3d965b	MENUNGGU_PEMBAYARAN	2026-05-20 03:52:24.656
d9c9de31-ee3f-4793-9b57-b900c847cd28	257da69f-2189-4e6f-a3ac-d59bb2fdb47a	MENUNGGU_PEMBAYARAN	2026-05-21 15:23:37.428
6c8573bf-7a53-4c98-8481-e4f3e41ef283	40136494-19b9-49db-aa31-ba312b1a5f67	MENUNGGU_PEMBAYARAN	2026-05-22 07:46:15.498
968e58a9-efb3-4297-bee3-aeafc9baac1a	267aafe6-e636-4f7e-a7a0-1538529ee2ef	MENUNGGU_PEMBAYARAN	2026-05-24 06:20:53.666
40a3b1b6-f554-4222-a1e6-3ed633462ba3	fb1dcec7-6a16-4b79-ad49-bc5c19aad70e	MENUNGGU_PEMBAYARAN	2026-05-24 06:23:50.137
b252f6d6-bdf4-4ead-88cf-8ec37a1b7969	72bbd75e-a492-4025-a3d2-d3243ab36901	BAYAR_DI_TEMPAT	2026-05-24 06:48:21.063
e4744408-d7a3-462e-b83d-a5524476a5c9	041e216f-5995-49a9-a481-ec7b3fdf0f79	MENUNGGU_PEMBAYARAN	2026-05-24 07:45:57.416
f53c7aad-9691-4e38-98c6-b3e6c041af52	fda4b075-f7ac-4cb2-af32-75eb2310c5a1	MENUNGGU_PEMBAYARAN	2026-05-24 09:55:04.403
b3d23603-0882-44e5-9ef9-7563d505c574	2d7283ea-2ed0-4b32-b1fd-e86eb2c77607	MENUNGGU_PEMBAYARAN	2026-05-24 10:00:03.086
00742e97-90af-4f0b-ac38-4c5e0c297c46	431c3215-fd5d-436c-8e9d-9ae3beb1acb7	MENUNGGU_PEMBAYARAN	2026-05-26 05:42:52.078
06ca7eec-6820-4a1d-a8dd-be8bb3509a79	04f26057-78bb-46ef-826c-f38620b27ea4	MENUNGGU_PEMBAYARAN	2026-05-26 05:47:18.306
40d27a2b-ecf4-4af9-a914-6cb23cc32043	1572c31a-49e5-4611-bbd2-c281ffc11981	MENUNGGU_PEMBAYARAN	2026-05-26 05:50:16.702
021e28e3-cdd3-49cb-ba3e-aa247fb29266	ae81d4a9-418c-4668-9f96-617c122dbcf1	MENUNGGU_PEMBAYARAN	2026-05-26 05:51:23.558
c3b9019b-f797-4dcc-a37d-a0c6d891fe68	2c9cb8c4-5b2e-49b6-8987-fd0c3c208fc0	MENUNGGU_PEMBAYARAN	2026-05-26 05:57:03.86
aa9c2768-a4df-4269-9f75-d1f4fe46e8b6	5ed2be0b-c4a4-42fe-baf6-888ee2e160b1	MENUNGGU_PEMBAYARAN	2026-05-26 05:58:35.513
af6b4f19-b45e-422d-8e66-b7966bdb37d8	5cff752e-70be-4460-b62f-be1ca2a62d13	MENUNGGU_PEMBAYARAN	2026-05-26 13:22:33.303
ad4f0bab-73ae-4201-84f1-26f2859be6c0	72caa5ce-5b7c-4e68-9f66-c83c60b974cc	MENUNGGU_PEMBAYARAN	2026-05-26 13:24:27.614
c910816e-fea9-4ff4-ae09-4789ff456bf4	3dafbe1a-ecb5-4156-9d68-dbd43d1f6e16	MENUNGGU_PEMBAYARAN	2026-05-26 13:24:51.483
162434d3-59ac-4f91-8acf-f4ddc7707e95	02c75cf9-37c4-4764-8280-5fbf6b89daf4	MENUNGGU_PEMBAYARAN	2026-05-26 13:27:19.857
34b0bb76-f6a3-48f7-9e40-dcaf65ebd4d2	405ece06-0b1c-44fd-8fbc-8af5f115061c	MENUNGGU_PEMBAYARAN	2026-05-26 13:27:32.915
6e8f24fe-91ed-479e-9f72-5488f804c3fc	4c5cd29c-f95b-4efa-8590-83c376f03895	MENUNGGU_PEMBAYARAN	2026-05-26 13:32:25.843
ac66d0ab-5f29-4087-ad2f-965dcf424615	63f514ec-a748-432e-aee2-a0216f8f2fd9	MENUNGGU_PEMBAYARAN	2026-05-26 13:34:54.74
b0fd432c-bda4-474b-b24d-71d2c86d3c3f	cbf9dd54-7453-42e4-bfb0-6e2c8143e747	MENUNGGU_PEMBAYARAN	2026-05-26 13:49:38.262
e5505e95-d327-446c-ba9b-543c0f05b4bf	853d06d6-a69c-4928-b4cf-ba5ede973fab	BAYAR_DI_TEMPAT	2026-05-26 13:53:13.764
74d719f0-502c-417a-91ab-5c427b84c839	a5552ab7-625f-471d-88cd-d5fdd79e8dae	BAYAR_DI_TEMPAT	2026-05-26 13:54:50.589
796f45fa-370f-41b1-9da7-323847be5330	7750855c-b4ca-4d8d-81f7-dc840ef8b56a	MENUNGGU_PEMBAYARAN	2026-05-26 14:10:15.673
a3e96693-a4f5-4b69-b9ab-4b52c12d1b4b	6d1feb1f-ca06-4860-9188-8280ef9e1675	MENUNGGU_PEMBAYARAN	2026-05-26 14:47:29.338
32f324bd-54eb-43f0-a920-33a71c6c75a3	02b0b57b-1277-42d4-818e-2f4fe7ff9569	BAYAR_DI_TEMPAT	2026-05-26 14:47:38.943
1a7b0e90-879a-46b5-a37b-863a1b264a85	806c0868-75f8-495a-8242-95a1489b419e	MENUNGGU_PEMBAYARAN	2026-05-26 17:16:00.987
8d3c7202-55aa-48b3-8aa0-ff68abcbd42e	fc64ffa4-d795-4757-a625-353a847dca2d	BAYAR_DI_TEMPAT	2026-05-27 01:59:04.744
f5bb6d8c-fe89-4628-b0f0-22c5ef3f21e1	cabbb8d3-770f-491c-98ed-28785e80ca82	MENUNGGU_PEMBAYARAN	2026-05-27 02:00:47.682
5bdb3384-eccb-405a-bd95-7d7000d2eb51	4367ee14-198c-4196-89a0-b22034d5651f	MENUNGGU_PEMBAYARAN	2026-05-27 02:49:15.729
fe766a8d-3e41-4693-9544-5fc458ed7503	8bbaedc1-252c-4707-bb15-7788a91a7d50	MENUNGGU_PEMBAYARAN	2026-05-27 02:51:18.991
a9c315f9-4426-49fd-9be6-60bca9a29c58	7a719e27-72d8-4b5d-bdcc-01adb9e5505c	MENUNGGU_PEMBAYARAN	2026-05-27 02:51:52.314
db40c590-042f-4325-8992-888c501645fa	309751ee-6352-4001-9746-d6873f0022a8	MENUNGGU_PEMBAYARAN	2026-05-27 03:01:42.787
bd62b7b1-471a-454e-9d03-3a49862bae31	37dba90e-20c8-4f28-87fb-e2570c4aa0ad	MENUNGGU_PEMBAYARAN	2026-05-28 06:18:37.326
20fcd15e-14da-40c4-b7f1-18ab31940141	d904fefb-6838-46df-96f0-8933744484d5	BAYAR_DI_TEMPAT	2026-05-28 09:40:56.342
21715809-30fc-4fec-9c5c-bbc7f4f3a594	1ab19ae2-805a-4ddd-b017-8995bf8d16e7	MENUNGGU_PEMBAYARAN	2026-05-28 09:43:09.813
fa03e882-f60a-4589-bfb8-cb293a5b350d	0fe3426b-f88b-4231-962e-4e52e8758abd	BAYAR_DI_TEMPAT	2026-05-29 04:28:36.157
2bf25af6-219e-4a1c-bb94-8c935264c67f	3a1f8dea-5fb5-4cab-a5dd-284ccce22ce5	BAYAR_DI_TEMPAT	2026-05-29 04:28:52.52
d557e60e-1c4f-4545-887e-2401be271b15	1d164501-3f05-4705-88fa-3f70bd09ef7a	BAYAR_DI_TEMPAT	2026-05-29 04:30:14.839
a671a624-798e-4e0f-9e96-fd1ae375f9da	0dc709ea-5331-43c1-b511-581067f3b6b4	BAYAR_DI_TEMPAT	2026-05-29 04:30:26.367
2157458d-30ee-4c60-a245-8c05335d01f3	1eac65d5-f52c-4ea7-a2c4-0fc3d9178729	BAYAR_DI_TEMPAT	2026-05-29 04:30:32.346
69913413-19f4-4d1d-94eb-ec0610a9e7e2	0498270b-284d-445d-9534-ace1e4eaa578	BAYAR_DI_TEMPAT	2026-05-29 04:30:38.035
38c35e7e-dbbe-4795-9720-9bc801cebc93	70126e0e-e774-4787-b325-e218bfb6b770	MENUNGGU_PEMBAYARAN	2026-05-29 04:30:44.057
036f4371-09cd-4019-8f9e-9ae646b81a80	bf46805c-3b88-4105-90c9-7ffe1badbc87	BAYAR_DI_TEMPAT	2026-05-29 04:30:50.928
2344cc72-15e5-40c6-ad85-9606aa32caea	c30c0c3b-288a-4e71-872a-7b17d38920e4	BAYAR_DI_TEMPAT	2026-05-29 06:59:09.145
2f9a97d1-5057-481d-aa71-0f683bd2a6d5	e3eff9a4-eb5d-46e2-8593-840729b1721d	BAYAR_DI_TEMPAT	2026-05-29 08:29:33.675
4379d50b-9b0c-42ec-9cb5-b0e402beda00	1a9b189f-df1c-4e50-a1a4-856fe5b24dfe	BAYAR_DI_TEMPAT	2026-05-29 08:33:45.991
e930f3c9-72e4-442e-9da2-091cff5366d1	f2eb9369-20dd-494f-982a-4b0ebcafc10f	MENUNGGU_PEMBAYARAN	2026-05-29 13:28:11.978
eb6d0e22-8005-4c8a-8053-fed2771080ff	4fc1f246-d1e9-4971-87ea-973409649aae	MENUNGGU_PEMBAYARAN	2026-05-29 13:50:23.166
8a1bae86-3068-4158-ab1f-ecf5237ba078	6ca2219c-36ac-4d47-bb43-f3a17865f2f7	MENUNGGU_PEMBAYARAN	2026-05-30 11:46:29.905
acd2c159-1b4f-4edf-9dc7-4df236867be1	1f131b56-9d1b-412b-ab98-23bc395a9b43	MENUNGGU_PEMBAYARAN	2026-05-30 11:58:36.365
132bcb99-32c7-405c-8447-f4b8c8d31291	578f86c6-4889-43ca-ab17-8d305a7f0cde	MENUNGGU_PEMBAYARAN	2026-05-30 12:05:54.233
a1c755f8-c6b2-4851-a48f-42fe04d552b6	e6dd354c-914d-4775-86d7-6dcad9e33ca1	MENUNGGU_PEMBAYARAN	2026-05-30 12:05:58.04
146417c7-93fe-4551-a0cd-2167e8042a31	613466b1-6a59-4873-8ddd-86237fac40df	MENUNGGU_PEMBAYARAN	2026-05-31 03:45:59.387
d2b44d7d-2398-4f7a-87ce-a4cf79be4554	54de8af4-31d5-4648-a38b-7db386e3731e	BAYAR_DI_TEMPAT	2026-05-31 03:46:05.728
e9d38614-740d-41d2-8c78-3ffc1e88662f	77c43e8c-33f4-44ee-aa8a-cf89bb6ec47d	MENUNGGU_PEMBAYARAN	2026-05-31 12:48:18.721
0561571b-0eb0-459d-a30f-18131c0f4e1a	e1ea49f6-1e28-407b-9626-6821d1adc217	BAYAR_DI_TEMPAT	2026-06-01 13:13:23.557
a3e2cdd4-0a44-4611-a573-4197e698a76a	71f5a35a-783b-4646-949f-d6c38aaa740e	MENUNGGU_PEMBAYARAN	2026-06-01 13:13:42.894
06f123ef-b008-4667-be5e-1b2feb77eb3f	ecf20e43-f1ec-4f73-a2ae-219eed359453	MENUNGGU_PEMBAYARAN	2026-06-01 13:14:23.921
21ed77b6-753e-4810-a39e-c4fc931ea7b1	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	BAYAR_DI_TEMPAT	2026-06-01 13:21:27.75
4b16650b-bec5-498e-ab31-a29d4d9c7925	564d0220-c1ce-438e-9731-4ed4ce6ccc0e	MENUNGGU_PEMBAYARAN	2026-06-05 14:08:57.649
d0a44cd9-7fa1-465d-b0ce-dcfc4aa300ce	489b575a-6157-4693-9960-01b1085eca41	MENUNGGU_PEMBAYARAN	2026-06-05 14:09:04.485
a4f39057-789f-4b1d-8bf0-02e42a58a576	7686fff2-52b9-405d-9ef0-3458232e95eb	BAYAR_DI_TEMPAT	2026-06-05 14:09:10.386
e5a611e4-7504-48e7-a4f5-0a0b24bb17ec	b7f6a060-e85a-4dfc-8489-0cd86459e237	MENUNGGU_PEMBAYARAN	2026-06-15 08:10:06.632
168c7787-8207-4057-8fac-fa2d090703fc	a0cd08bb-d823-430b-bf2e-23d5c44e0762	MENUNGGU_PEMBAYARAN	2026-06-15 08:10:33.425
afaada59-e103-478d-a4dc-d4e17b6229ce	2417503a-c011-44c5-8ab3-9a0ce567fcdc	MENUNGGU_PEMBAYARAN	2026-06-16 02:53:30.233
021889f7-9bc7-422c-897f-055adc655edf	f911b4b8-08e7-46e7-8f1a-869136c4288e	BAYAR_DI_TEMPAT	2026-06-16 07:06:06.752
40c2e617-5ccb-42f9-8d22-a543a35fe5aa	2bc0fe7c-772b-4d59-aae9-c983ce079eb4	BAYAR_DI_TEMPAT	2026-06-16 07:57:02.215
066a3226-6c42-4988-9a39-2bcfe47df703	eb82ad63-71e3-4b78-a844-50559af1b1e1	BAYAR_DI_TEMPAT	2026-06-16 07:58:41.672
52eeb399-976e-471d-a1cf-46fd990d0fae	b18ffbc6-6926-4005-a6e5-9c73c533a131	BAYAR_DI_TEMPAT	2026-06-16 08:03:57.353
727262e3-6aac-405d-a642-a44d25a6b0d4	141da71e-2fa7-4d85-90dc-fb55324deef3	BAYAR_DI_TEMPAT	2026-06-16 08:04:48.561
58862f08-10b6-4761-8922-1e5b5fb92202	43a5b414-7199-407f-9064-c29c11aacf12	MENUNGGU_PEMBAYARAN	2026-06-16 08:18:47.674
89d7d673-e224-4e9d-a767-0d084cf3e679	63ffcdd2-adce-4230-9c61-630e410acfc5	BAYAR_DI_TEMPAT	2026-06-16 08:19:00.881
0b689859-d055-4246-9a25-146397f0d57f	4080a0ea-e99b-49c4-9c63-ea12659dde9b	MENUNGGU_PEMBAYARAN	2026-06-16 08:27:24.612
\.


--
-- Data for Name: Produk; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."Produk" (id_produk, id_user_seller, id_kategori, nama_produk, deskripsi, harga, stok, status_produk, created_at, foto_produk, konten_deskripsi, catatan_penjual, foto_produk_list) FROM stdin;
0f81584b-1320-440b-a681-d016ef6c40d2	30	K002	Tes Produk	Menampung tanaman	1000	10	AKTIF	2026-05-27 01:41:33.125	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779846092197-462987878.jpg	tes konten	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779846092197-462987878.jpg}
6cc17902-2437-4663-88e8-82cfd4301601	6	K001	kucing ceper	Produk ramah lingkungan.	0	0	AKTIF	2026-05-14 05:22:53.435	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736172437-480531708.jpg	eae	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736172437-480531708.jpg,https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736173221-841246914.jpeg}
88f2e4d9-0e61-42d7-a5cf-41a3b9ee1d7f	6	K001	baiwan kh	Produk ramah lingkungan.	0	0	AKTIF	2026-05-14 05:29:08.333	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736547678-897545253.jpg	eaea	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736547678-897545253.jpg}
8808583d-a8ff-4711-8958-608cb3021324	6	K001	kucing hitam dalam mobil	Produk ramah lingkungan.	0	0	AKTIF	2026-05-14 05:29:52.192	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736591832-420117181.jpeg	mobil	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736591832-420117181.jpeg}
0c8775a4-2ea1-4fef-b2b0-055cdc687e34	6	K001	kicau mania pontianak	Produk ramah lingkungan.	0	0	AKTIF	2026-05-14 05:34:42.829	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736882142-911818552.jpeg	eae 	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736882142-911818552.jpeg}
89c8e362-579d-4f5e-abb9-74008b0c5f06	6	K001	P	Produk ramah lingkungan.	1	0	AKTIF	2026-05-14 05:35:33.189	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736932780-60065574.jpeg	ahihihji	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778736932780-60065574.jpeg}
a03e7058-9861-4c28-b2cd-8d9a8bd5d0e4	14	K001	Green Cat	Produk ramah lingkungan.	1000	81	AKTIF	2026-05-15 00:03:52.055	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778803431392-585130939.jpg	Cat from mars	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778803431392-585130939.jpg}
767eea1d-08cf-48e8-882f-150ab1326dd9	11	K003	Pot botol plastik	Daur Ulang	10000	5	AKTIF	2026-05-15 08:09:40.715	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778832579931-775251920.jpg	Menampung tanaman	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1778832579931-775251920.jpg}
360f5e85-dc9e-4854-9e38-7764259216af	43	K001	PS Bekas	Produk ramah lingkungan.	10000000	10	AKTIF	2026-05-28 09:10:21.946	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779959420811-940614203.jpg	PS 5 bekas peninggalan di rumah ksong	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779959420811-940614203.jpg}
0f607c3c-45d6-4baf-8cb2-076862fa9524	30	K002	Tes Produk	Menampung tanaman	1000	10	AKTIF	2026-05-27 01:41:53.19	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779846112641-936078907.jpg	Belum ada detail produk.	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779846112641-936078907.jpg}
5a3f1d4c-b2fc-4f4e-9ac9-45fd85bd5696	1	K003	Boneka Badut Tutup Botol Daur Ulang	Produk ramah lingkungan.	20000	0	AKTIF	2026-05-19 14:32:14.11	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779201132214-488682922.png	boneka badut comel\r\n\r\nBoneka handmade unik berbahan tutup botol plastik daur ulang yang dibuat menjadi kerajinan kreatif dan ramah lingkungan. Cocok sebagai mainan edukasi, dekorasi meja, maupun souvenir eco-friendly. Membantu mengurangi limbah plastik sekaligus meningkatkan nilai guna barang bekas melalui proses upcycle kreatif.	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779201132214-488682922.png}
4ae9d78b-7e28-4855-a7e5-4ccdc44d7fe3	15	K002	Kaos  Alucard Keren Bekas	Pakaian Organik	10000	50	AKTIF	2026-05-18 01:27:29.879	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779067647750-211576378.jpg	Kaos Alucard bekas dan keren	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779067647750-211576378.jpg,https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779067648483-428272287.jpg,https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779067649023-604044408.jpg,https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779067649315-672910852.jpg}
393a2e70-ea13-4e44-b869-6eebd5b309fd	30	K001	Produk Test Error	Produk ramah lingkungan.	10000	7	AKTIF	2026-05-28 12:21:28.73	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779970887743-394008537.jpg	Belum ada detail produk.	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779970887743-394008537.jpg}
e6c5448a-6c00-431b-a22e-e2637c5d33f2	20	K001	AKM	Produk ramah lingkungan.	200000	12	AKTIF	2026-05-19 15:54:56.194	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779206095178-911038497.jpg	Senjata AKM (Avtomat Kalashnikova Modernizirovanniy) asli adalah senapan serbu militer otomatis kaliber 7.62x39mm buatan Uni Soviet. Berdasarkan hukum di Indonesia, kepemilikan, penjualan, maupun pembelian senjata api militer asli (baik baru maupun bekas) oleh warga sipil adalah tindakan ilegal dan melanggar hukum pidana	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779206095178-911038497.jpg,https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779206095563-858364200.jpg,https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779206095807-639331408.jpg}
f8b002c7-ba96-47c0-a3cc-16a8afcd2b4f	24	K003	Mobil	Pakaian Organik	500	94	AKTIF	2026-05-21 23:39:31.304	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779406769705-850075891.jpg	Mobil...	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779406769705-850075891.jpg,https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779406770932-201020956.jpeg}
c01c5877-8d44-4272-acb4-3ec460a4086c	35	K001	kokiko	Produk ramah lingkungan.	1	9000	AKTIF	2026-05-27 02:48:03.456	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779850121618-235376251.png	huh hb byk	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779850121618-235376251.png}
4f8f1dc6-b3ac-4cc7-9485-8a042fdead1d	14	K001	Tas	Daur Ulang	1000	10	AKTIF	2026-05-20 10:35:16.344	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779273315194-244967211.png	..	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779273315194-244967211.png}
906e9c03-5a78-4b0c-a9c0-a953869551b5	30	K001	Pot botol plastik	Menampung tanaman	18000	70	AKTIF	2026-05-25 03:41:00.334	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779680459628-519968950.jpg	Menampung tanaman	Jangan kepanasan	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779680459628-519968950.jpg}
d2bbc193-ec7e-45bd-ae55-dcf53ce4042c	10	K003	Pot botol plastik	Daur Ulang	10000	8	AKTIF	2026-06-01 13:10:30.39	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1780319429637-450694716.jpg	Menampung tanaman	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1780319429637-450694716.jpg}
43ea2e3a-7909-4791-b9a6-ca49f3c5ba83	15	K002	Katana Bekas	Produk ramah lingkungan.	1	0	AKTIF	2026-05-25 05:36:30.379	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779687389679-277044582.jpg	bekas memotong alpukat	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779687389679-277044582.jpg}
2217d02f-ff6a-4459-8827-66d2ccff714e	30	K001	Pot botol plastik	Menampung tanaman	15000	6	AKTIF	2026-05-31 10:04:19.804	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1780221858858-964172723.jpg	Menampung tanaman	Jangan kepanasan	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1780221858858-964172723.jpg}
54219152-31f8-42d4-ad08-69bd8d832fda	35	K001	test	Daur Ulang	2000	50	AKTIF	2026-05-26 09:11:48.561	https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779786707764-219749377.png	gdg	Belum ada catatan penjual.	{https://iobibwvdnejxhesekrne.supabase.co/storage/v1/object/public/produk/produk/1779786707764-219749377.png}
\.


--
-- Data for Name: Toko; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."Toko" (id_toko, id_user, nama_toko, email_bisnis, alamat_toko, created_at) FROM stdin;
70a1f4d6-fa97-4224-a749-21ff2fdc6a73	24	Green Taste	yorune@greenmarket.com	Jakarta	2026-05-21 23:37:08.133
eedd0e07-c954-4021-a50e-b0359b372e0d	10	bubadibako	bubadibako@greenmarket.com	Jalan Telekomunikasi No. 1, Terusan Buahbatu, Kecamatan Dayeuhkolot, Kabupaten Bandung, Jawa Barat	2026-05-22 14:28:47.656
4b4c02a3-2ef7-4fdc-ab14-16608e537092	22	asdf	\N	\N	2026-05-25 05:12:24.569
03c6de4f-88b6-41fe-a617-23d6cab1d2f6	31	Keanu9090	keanutelkom@greenmarket.com	Jakarta Barat	2026-05-25 05:16:08.554
3e28288c-3c28-4612-828c-52a047adfc53	1	Toko Hijau Sejahtera	toko@greenmarket.com	Jl. Lingkungan No. 1, Bandung	2026-05-22 13:50:26.431
efacac0c-d798-47dc-bbe9-3595c280da06	30	Toko Hijau Sejahtera	toko@greenmarket.com	Jl. Lingkungan No. 1, Bandung	2026-05-26 06:55:29.748
7e99ed89-6dc0-4ef1-83d7-bcfe82f2273b	35	Toko bagus	tokobagus@greenmarket.com	Aceh, Jawa Timur	2026-05-26 08:29:49.066
0e937e5f-ac1b-4270-9de8-1d66c5ecf964	15	Keanusdf	keanu@greenmarket.com	Bandung , Jawabarat	2026-05-27 07:21:06.959
4723dfe4-a79e-4dc4-814f-65bca7ed2085	37	tokotest	tokotest@greenmarket	Jalan Telekomunikasi No. 1, Terusan Buahbatu, Kecamatan Dayeuhkolot, Kabupaten Bandung, Jawa Barat	2026-05-27 07:25:03.085
78c15fef-f05a-44bc-a268-fad687efc105	25	tokotest	tokotest@greenmarket.com	Jalan Telekomunikasi No. 1, Terusan Buahbatu, Kecamatan Dayeuhkolot, Kabupaten Bandung, Jawa Barat	2026-05-27 10:19:42.968
764fe0d4-b66b-49c9-be73-e50ff1483ba3	42	tokotest	tokotest@greenmarket.com	Jalan Telekomunikasi No. 1, Terusan Buahbatu, Kecamatan Dayeuhkolot, Kabupaten Bandung, Jawa Barat	2026-05-27 10:24:32.844
882c0d83-9221-44b9-8e21-302d146b985d	43	TokoTermurah	tokoaku@greenmarket.com	Soreang, Jawa Barat	2026-05-28 07:53:04.288
a73603f5-969d-4dd8-a2ee-adf3cb12531b	45	toko	toko@greenmarket.com	Jl. Telekomunkasi No. 2	2026-05-30 12:01:38.19
1f1871d9-3f72-47fb-a737-c554e33c9d21	48	K User	k@greenmarket.com	Bandung, Jawa Barat	2026-06-09 09:17:48.742
efec75f7-b83e-4adb-9f84-4fd9a252f9b2	50	kicaumania	kicaumania@greenmarket.com	bandung baleendah	2026-06-15 08:08:44.443
93b70712-8cf5-4cdb-8e74-6a351a9f6d0f	51	BekasdanJual	sikeanzx@greenmarket.com	Bandung,Jawa Barat	2026-06-16 05:03:51.533
\.


--
-- Data for Name: TrackingLog; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."TrackingLog" (id_log, id_transaksi, status, waktu) FROM stdin;
8a2861da-0a94-4e5f-9ed4-0be50edae143	4428e0b5-abd4-48b7-97b3-542c8df4c9b5	Transaksi dibuat	2026-05-17 13:14:33.93
e010472e-2418-43c0-aac6-08affefdd433	4428e0b5-abd4-48b7-97b3-542c8df4c9b5	Menunggu pembayaran	2026-05-17 13:14:33.93
4f27a327-3320-46c1-b0c7-923611cc332c	4b132211-858f-4422-a829-cb897cc3d292	Transaksi dibuat	2026-05-17 14:44:08.92
3b42b2a9-0868-4489-8e2c-f3a4118afd02	4b132211-858f-4422-a829-cb897cc3d292	Menunggu pembayaran	2026-05-17 14:44:08.92
19320bf5-32af-4f80-98fc-c99b0312e39e	00456957-d2b7-48ab-91d2-cdb6c2aa73ea	Transaksi dibuat	2026-05-17 14:44:10.199
def917ca-f47f-43e7-a733-bd9de91a0be2	00456957-d2b7-48ab-91d2-cdb6c2aa73ea	Menunggu pembayaran	2026-05-17 14:44:10.199
c32579d8-569d-45df-b510-3002733b0952	c578e56e-4a29-40d3-b686-9e0b6d3f5e5e	Transaksi dibuat	2026-05-17 14:44:27.779
fc4afed2-20be-4a14-95c6-3790c49f1aae	c578e56e-4a29-40d3-b686-9e0b6d3f5e5e	Menunggu pembayaran	2026-05-17 14:44:27.779
d36f7953-2180-4829-8dd1-d01b8f55b55a	4947f3f5-e9f5-4cbf-ac28-3936b2690405	Transaksi dibuat	2026-05-17 14:45:32.44
48cbf237-2610-4ed5-81ea-efc68675281e	4947f3f5-e9f5-4cbf-ac28-3936b2690405	Menunggu pembayaran	2026-05-17 14:45:32.44
7ea89d79-e891-428c-a670-2b605f780fcd	694baad0-a1c6-441c-a0a3-2afaa6796729	Transaksi dibuat	2026-05-17 22:57:29.338
5a7997a0-e95c-4ef4-818a-4fca20b30ab6	694baad0-a1c6-441c-a0a3-2afaa6796729	Menunggu pembayaran	2026-05-17 22:57:29.338
27feee0a-a1a2-4711-9733-5565eb219168	864e1f8a-bf6f-4b15-8b83-fe82b9a7ebac	Transaksi dibuat	2026-05-17 22:57:30.895
adacec8e-cbdb-496c-b07a-6cf1b990d386	864e1f8a-bf6f-4b15-8b83-fe82b9a7ebac	Menunggu pembayaran	2026-05-17 22:57:30.895
4629bda7-8822-40a2-af35-021cd72be4c4	9e00fea2-f6bf-4ac5-a4b9-e7d6ea88ed38	Transaksi dibuat	2026-05-17 23:05:12.713
58b46adc-9f4b-4005-85f6-3c3e16090fba	9e00fea2-f6bf-4ac5-a4b9-e7d6ea88ed38	Menunggu pembayaran	2026-05-17 23:05:12.713
0d2fed03-7e76-46c7-9cb9-72fe0e05f8c6	4d9d8c61-3c09-4a12-b645-941d55743c0c	Transaksi dibuat	2026-05-17 23:05:14.589
fa07d87f-9f8e-4ecd-8774-e3e2726bdd91	4d9d8c61-3c09-4a12-b645-941d55743c0c	Menunggu pembayaran	2026-05-17 23:05:14.589
e9130af5-cb4a-4775-8f71-b3b23278e8f7	40215c2b-ae23-4e9e-9d51-ec4f0c94664b	Transaksi dibuat	2026-05-17 23:11:18.808
c98e8442-ae7e-4bb8-b3c7-e73d396afd86	40215c2b-ae23-4e9e-9d51-ec4f0c94664b	Menunggu pembayaran	2026-05-17 23:11:18.808
f70e212f-206b-4491-a74f-4869075b2943	66aea0b9-abec-4397-921f-f99eda1d4ed0	Transaksi dibuat	2026-05-17 23:11:37.365
d0c366d8-e286-41b2-9148-86d8974ef108	66aea0b9-abec-4397-921f-f99eda1d4ed0	Menunggu pembayaran	2026-05-17 23:11:37.365
2c712e7b-ed97-459e-a7cd-361e473e657a	b25f110c-907d-414b-8e03-2c14081b2fb7	Transaksi dibuat	2026-05-17 23:12:07.644
c2f12fce-3eb0-489e-8c6a-b8805fd14def	b25f110c-907d-414b-8e03-2c14081b2fb7	Menunggu pembayaran	2026-05-17 23:12:07.644
022bb991-f394-4d26-aa25-b7597c821112	38e5c9ad-512c-4e31-b9dd-15929a359997	Transaksi dibuat	2026-05-17 23:12:32.761
baa6176c-1bcc-4d53-ad53-13ad078ea0c2	38e5c9ad-512c-4e31-b9dd-15929a359997	Menunggu pembayaran	2026-05-17 23:12:32.761
e2365480-0e48-4502-adfb-06d8ae2a2c0b	bfeb09f1-8479-48d3-a2d1-f4de07908f3a	Transaksi dibuat	2026-05-17 23:12:59.121
14417b83-f1c9-4720-9cdf-1acb300b6acc	bfeb09f1-8479-48d3-a2d1-f4de07908f3a	Menunggu pembayaran	2026-05-17 23:12:59.121
c54ed12a-4143-4401-ac53-65183bcf0c3d	05cf8f67-ea7f-4621-9a88-b5677a6bccfd	Transaksi dibuat	2026-05-18 04:13:45.071
9b0f4e4a-649d-43d6-a3d5-f434c5a3bcf2	05cf8f67-ea7f-4621-9a88-b5677a6bccfd	Pesanan sedang dikemas	2026-05-18 04:13:45.071
87410bfa-a486-4e75-82ff-661b0c8172e0	05cf8f67-ea7f-4621-9a88-b5677a6bccfd	DIKIRIM_SELLER_14	2026-05-18 04:14:49.349
b02cd3eb-ad2d-4d6b-a36a-9bd5aa397869	05cf8f67-ea7f-4621-9a88-b5677a6bccfd	SELESAI_SELLER_14	2026-05-18 04:15:57.527
7decbe0b-f17e-4ed4-9223-babeb435e3db	dd6427a7-82dc-4cab-a0e0-c99b68b99371	Transaksi dibuat	2026-05-19 02:34:21.548
57cecbe3-415f-4e90-8eb3-13e94f169d08	dd6427a7-82dc-4cab-a0e0-c99b68b99371	Pesanan sedang dikemas	2026-05-19 02:34:21.548
25cfe2ed-9b8e-4458-a535-8a5ec89f12a0	dd6427a7-82dc-4cab-a0e0-c99b68b99371	DIKIRIM_SELLER_14	2026-05-19 02:35:06.493
002e415e-e3d9-44b1-89ba-c736e47f9ae6	dd6427a7-82dc-4cab-a0e0-c99b68b99371	SELESAI_SELLER_14	2026-05-19 02:36:24.518
b14a6bcd-375c-40f7-a51e-cce1661354e1	f5d4ae1f-1bc8-4538-98ba-3b904246a28b	Transaksi dibuat	2026-05-19 02:51:04.938
df084ff2-f384-44dc-87cb-d645272a20cc	f5d4ae1f-1bc8-4538-98ba-3b904246a28b	Menunggu pembayaran	2026-05-19 02:51:04.938
327106e7-41a9-4b08-b81b-9000b48234ce	c2004f1f-7025-438e-9282-fa2873934ff9	Transaksi dibuat	2026-05-19 02:52:12.889
644b20d7-63d1-4c3e-87e8-ebfc09fc690d	c2004f1f-7025-438e-9282-fa2873934ff9	Menunggu pembayaran	2026-05-19 02:52:12.889
42a21136-c252-4a01-8848-2ae0cd0f01dd	70d3aac9-a7ad-4e4d-b64e-78e039a13aed	Transaksi dibuat	2026-05-19 02:59:02.578
cb6ab2ce-8113-4781-a866-4523ba13702a	70d3aac9-a7ad-4e4d-b64e-78e039a13aed	Menunggu pembayaran	2026-05-19 02:59:02.578
7fdf2805-3067-43e3-8dda-4cbfca25e2a0	0975a9d8-1599-453e-8727-53946ff8b96e	Transaksi dibuat	2026-05-20 00:55:12.187
078aa45f-ed4c-4455-a7f3-fcd09bbe588b	0975a9d8-1599-453e-8727-53946ff8b96e	Pesanan sedang dikemas	2026-05-20 00:55:12.187
4e9fdf4c-71e3-4142-b6be-b9de34151a73	0d885946-94b6-4ee0-873e-39aee013669c	Transaksi dibuat	2026-05-20 00:56:42.92
b4ab2533-a35c-4bbe-ad78-625e3674588c	0d885946-94b6-4ee0-873e-39aee013669c	Menunggu pembayaran	2026-05-20 00:56:42.92
b7ecfc75-49cc-4eb9-aef1-a182b0de5115	ede8d5ad-5b2f-42e6-b721-06efd7b5e57a	Transaksi dibuat	2026-05-20 01:14:29.254
bba3a5b2-19c5-4f3b-aa81-b6fa847ab6cc	ede8d5ad-5b2f-42e6-b721-06efd7b5e57a	Menunggu pembayaran	2026-05-20 01:14:29.254
243806a3-6f72-4cd5-9be6-db7cfb5797a4	08c88e4c-ebb8-4fe8-ac69-ec75ee03c771	Transaksi dibuat	2026-05-20 01:20:02.99
c5faacf2-bd99-409f-a286-60c3b257fd52	08c88e4c-ebb8-4fe8-ac69-ec75ee03c771	Pesanan sedang dikemas	2026-05-20 01:20:02.99
dbd58770-9250-434b-a18c-610c40c22d5f	59187485-d31c-4bf9-a4ca-d83c607b91b7	Transaksi dibuat	2026-05-20 03:49:22.832
1204e721-0f0f-43a4-a698-1e084b0f418d	59187485-d31c-4bf9-a4ca-d83c607b91b7	Menunggu pembayaran	2026-05-20 03:49:22.832
d2c7466f-abea-4811-a7fc-5620e07f131e	5b8e104c-5363-4f09-95c6-835c2190302c	Transaksi dibuat	2026-05-20 03:50:55.006
d44e084b-d44a-483c-837d-07b4f9bd33c7	5b8e104c-5363-4f09-95c6-835c2190302c	Menunggu pembayaran	2026-05-20 03:50:55.006
d268afc3-70e9-45c0-bf77-5095c2d4c487	1dbcdc5e-10fe-43e9-a85d-9a3ea064771a	Transaksi dibuat	2026-05-20 03:51:06.545
ad665971-a7b0-42bc-80e5-bff47b085d9a	1dbcdc5e-10fe-43e9-a85d-9a3ea064771a	Menunggu pembayaran	2026-05-20 03:51:06.545
365ef504-5278-4c3d-a67e-55840fe33c96	e3331a25-7c15-4fd2-b9af-7b02da3d965b	Transaksi dibuat	2026-05-20 03:52:24.656
00434e7a-0a6c-425c-a676-9477918fee60	e3331a25-7c15-4fd2-b9af-7b02da3d965b	Menunggu pembayaran	2026-05-20 03:52:24.656
f966d5fb-57c1-44c8-9274-137a1e8d4cd2	257da69f-2189-4e6f-a3ac-d59bb2fdb47a	Transaksi dibuat	2026-05-21 15:23:37.428
d9b59c72-63ef-4b60-a1ee-9421d8d2f077	257da69f-2189-4e6f-a3ac-d59bb2fdb47a	Menunggu pembayaran	2026-05-21 15:23:37.428
3f444bf7-ab1a-4732-85c7-bc8d3ac2b47b	40136494-19b9-49db-aa31-ba312b1a5f67	Transaksi dibuat	2026-05-22 07:46:15.498
89b52e30-0889-4c06-9b65-85f6a4c7d70f	40136494-19b9-49db-aa31-ba312b1a5f67	Menunggu pembayaran	2026-05-22 07:46:15.498
cdda3a25-4be8-429c-8348-475f87fc6b13	267aafe6-e636-4f7e-a7a0-1538529ee2ef	Transaksi dibuat	2026-05-24 06:20:53.666
d75731ae-4c8e-4376-84cb-8cd155332fb5	267aafe6-e636-4f7e-a7a0-1538529ee2ef	Menunggu pembayaran	2026-05-24 06:20:53.666
2832aab1-6c57-4ca0-a966-64f92a533874	fb1dcec7-6a16-4b79-ad49-bc5c19aad70e	Transaksi dibuat	2026-05-24 06:23:50.137
844aa0b0-3a3c-4da1-b875-c15afb551ae4	fb1dcec7-6a16-4b79-ad49-bc5c19aad70e	Menunggu pembayaran	2026-05-24 06:23:50.137
a3e21a96-000b-4031-87df-75f087f2a56e	72bbd75e-a492-4025-a3d2-d3243ab36901	Transaksi dibuat	2026-05-24 06:48:21.063
73487487-b5ca-4e59-8a89-bc1222e1879c	72bbd75e-a492-4025-a3d2-d3243ab36901	Pesanan sedang dikemas	2026-05-24 06:48:21.063
2e05003e-1f8d-4feb-9f2d-5dbdb34777cf	041e216f-5995-49a9-a481-ec7b3fdf0f79	Transaksi dibuat	2026-05-24 07:45:57.416
b490d538-12ac-4a52-b866-bc7b8e0d24ec	041e216f-5995-49a9-a481-ec7b3fdf0f79	Menunggu pembayaran	2026-05-24 07:45:57.416
cad24636-e99d-46f9-b05c-4a1c11c2c7d3	fda4b075-f7ac-4cb2-af32-75eb2310c5a1	Transaksi dibuat	2026-05-24 09:55:04.403
1a00a145-d003-4121-aea9-6ef46e3cfef3	fda4b075-f7ac-4cb2-af32-75eb2310c5a1	Menunggu pembayaran	2026-05-24 09:55:04.403
9d10af8f-9753-42ce-a0e5-b281dc40b05e	2d7283ea-2ed0-4b32-b1fd-e86eb2c77607	Transaksi dibuat	2026-05-24 10:00:03.086
fcb09c22-abac-43e1-bc28-d116dc50267a	2d7283ea-2ed0-4b32-b1fd-e86eb2c77607	Menunggu pembayaran	2026-05-24 10:00:03.086
53f321af-ec86-4208-b13b-f5fe3f64b088	431c3215-fd5d-436c-8e9d-9ae3beb1acb7	Transaksi dibuat	2026-05-26 05:42:52.078
14e6245f-b7d5-4cf9-931e-3267cb8584fb	431c3215-fd5d-436c-8e9d-9ae3beb1acb7	Menunggu pembayaran	2026-05-26 05:42:52.078
3fcc533c-c284-4894-83f1-984f80a92fa9	04f26057-78bb-46ef-826c-f38620b27ea4	Transaksi dibuat	2026-05-26 05:47:18.306
92a4b55f-4c4c-44b8-aa59-1d270a67a5a9	04f26057-78bb-46ef-826c-f38620b27ea4	Menunggu pembayaran	2026-05-26 05:47:18.306
9224eefa-0e0f-44cc-a410-e296baba2958	1572c31a-49e5-4611-bbd2-c281ffc11981	Transaksi dibuat	2026-05-26 05:50:16.702
32a3924f-7399-4cb5-9e50-d5ed150320c5	1572c31a-49e5-4611-bbd2-c281ffc11981	Menunggu pembayaran	2026-05-26 05:50:16.702
a652bc70-c660-4dd4-9e5e-15e9829928eb	ae81d4a9-418c-4668-9f96-617c122dbcf1	Transaksi dibuat	2026-05-26 05:51:23.558
76d4f6a2-cc3c-421f-b68d-11c2c60cbd89	ae81d4a9-418c-4668-9f96-617c122dbcf1	Menunggu pembayaran	2026-05-26 05:51:23.558
6cd99be9-0aa0-47af-9389-470cbb14509f	2c9cb8c4-5b2e-49b6-8987-fd0c3c208fc0	Transaksi dibuat	2026-05-26 05:57:03.86
932a2e20-e988-4980-9cd8-2365226b3088	2c9cb8c4-5b2e-49b6-8987-fd0c3c208fc0	Menunggu pembayaran	2026-05-26 05:57:03.86
d65bb8d7-d13d-43db-a5d2-9b0fc7319dc4	5ed2be0b-c4a4-42fe-baf6-888ee2e160b1	Transaksi dibuat	2026-05-26 05:58:35.513
81514634-1596-42ff-b697-c1cd008f3e18	5ed2be0b-c4a4-42fe-baf6-888ee2e160b1	Menunggu pembayaran	2026-05-26 05:58:35.513
ecb4bde7-fc42-4d05-a6fc-7957c3d8f06d	5cff752e-70be-4460-b62f-be1ca2a62d13	Transaksi dibuat	2026-05-26 13:22:33.303
17620778-7d09-4548-871e-da079a4214f8	5cff752e-70be-4460-b62f-be1ca2a62d13	Menunggu pembayaran	2026-05-26 13:22:33.303
ae261fa4-aab9-4b93-9b60-0394b3d7ed41	72caa5ce-5b7c-4e68-9f66-c83c60b974cc	Transaksi dibuat	2026-05-26 13:24:27.614
05030d6c-cd67-4ea8-bc78-25f67a113ca0	72caa5ce-5b7c-4e68-9f66-c83c60b974cc	Menunggu pembayaran	2026-05-26 13:24:27.614
66083e79-2c4e-4570-a76f-a32659b24cf0	3dafbe1a-ecb5-4156-9d68-dbd43d1f6e16	Transaksi dibuat	2026-05-26 13:24:51.483
fdd5e5c7-aa16-4694-852d-01de1450491d	3dafbe1a-ecb5-4156-9d68-dbd43d1f6e16	Menunggu pembayaran	2026-05-26 13:24:51.483
4685d268-cd97-485c-bffc-90a55257f17d	02c75cf9-37c4-4764-8280-5fbf6b89daf4	Transaksi dibuat	2026-05-26 13:27:19.857
1c997eb8-55bb-4bba-a829-3f740c6da57f	02c75cf9-37c4-4764-8280-5fbf6b89daf4	Menunggu pembayaran	2026-05-26 13:27:19.857
1b8dbdd7-214e-4023-b70e-51aa19b4e4bc	405ece06-0b1c-44fd-8fbc-8af5f115061c	Transaksi dibuat	2026-05-26 13:27:32.915
1a027341-261d-4083-8596-a5698ff1f0a2	405ece06-0b1c-44fd-8fbc-8af5f115061c	Menunggu pembayaran	2026-05-26 13:27:32.915
ea8b4017-8598-4d28-a756-8fe0c4183562	4c5cd29c-f95b-4efa-8590-83c376f03895	Transaksi dibuat	2026-05-26 13:32:25.843
cd1f8235-1be0-442d-af7f-2454b589d886	4c5cd29c-f95b-4efa-8590-83c376f03895	Menunggu pembayaran	2026-05-26 13:32:25.843
504b119e-4726-47d3-b4a2-1c72cb68b9ec	63f514ec-a748-432e-aee2-a0216f8f2fd9	Transaksi dibuat	2026-05-26 13:34:54.74
aa84652c-4da2-40d8-9d5d-8dd5c057f5a0	63f514ec-a748-432e-aee2-a0216f8f2fd9	Menunggu pembayaran	2026-05-26 13:34:54.74
685e4dfa-3cb9-46d6-8cc2-6f69d8a597fd	cbf9dd54-7453-42e4-bfb0-6e2c8143e747	Transaksi dibuat	2026-05-26 13:49:38.262
42e19880-cfbb-438c-a1d9-4dd1d13aa7f3	cbf9dd54-7453-42e4-bfb0-6e2c8143e747	Menunggu pembayaran	2026-05-26 13:49:38.262
c96b3545-84f1-4ea8-8740-2f5b5f97faac	853d06d6-a69c-4928-b4cf-ba5ede973fab	Transaksi dibuat	2026-05-26 13:53:13.764
7138c7d7-65bf-4da4-8ed3-d79c117895ca	853d06d6-a69c-4928-b4cf-ba5ede973fab	Pesanan sedang dikemas	2026-05-26 13:53:13.764
3d2c21d6-617b-4f64-999f-986c1996421c	a5552ab7-625f-471d-88cd-d5fdd79e8dae	Transaksi dibuat	2026-05-26 13:54:50.589
99beabed-9311-48a1-869a-a0426e470cb5	a5552ab7-625f-471d-88cd-d5fdd79e8dae	Pesanan sedang dikemas	2026-05-26 13:54:50.589
08262e81-9ed5-47a5-8f02-9bea4846ce8f	7750855c-b4ca-4d8d-81f7-dc840ef8b56a	Transaksi dibuat	2026-05-26 14:10:15.673
5e16d610-f5de-45c6-99b9-24bb7e8bb0f3	7750855c-b4ca-4d8d-81f7-dc840ef8b56a	Menunggu pembayaran	2026-05-26 14:10:15.673
16801783-35d7-4a70-94ba-7114165232fe	6d1feb1f-ca06-4860-9188-8280ef9e1675	Transaksi dibuat	2026-05-26 14:47:29.338
90e12472-c948-45a0-9ce1-5fe134435ff2	6d1feb1f-ca06-4860-9188-8280ef9e1675	Menunggu pembayaran	2026-05-26 14:47:29.338
f5131648-d0ed-48fc-a7ed-b8ad092d29ef	02b0b57b-1277-42d4-818e-2f4fe7ff9569	Transaksi dibuat	2026-05-26 14:47:38.943
5daa978f-7259-4995-9410-2b96ec147017	02b0b57b-1277-42d4-818e-2f4fe7ff9569	Pesanan sedang dikemas	2026-05-26 14:47:38.943
9f48ed3c-01ec-4da7-ac32-d029ba14db3e	806c0868-75f8-495a-8242-95a1489b419e	Transaksi dibuat	2026-05-26 17:16:00.987
1eec6fe3-dfc8-424e-b5af-b76a09aa7145	806c0868-75f8-495a-8242-95a1489b419e	Menunggu pembayaran	2026-05-26 17:16:00.987
c2618a1f-f0ea-4687-8630-d4c168422b69	fc64ffa4-d795-4757-a625-353a847dca2d	Transaksi dibuat	2026-05-27 01:59:04.744
e203e2e2-26c1-4878-9cfd-15a615b1d1d1	fc64ffa4-d795-4757-a625-353a847dca2d	Pesanan sedang dikemas	2026-05-27 01:59:04.744
019297a9-3e1b-48b1-a863-f9fd80fa2747	cabbb8d3-770f-491c-98ed-28785e80ca82	Transaksi dibuat	2026-05-27 02:00:47.682
53ffd595-a030-42cd-94f9-ba01534cf1ba	cabbb8d3-770f-491c-98ed-28785e80ca82	Menunggu pembayaran	2026-05-27 02:00:47.682
0ca6a0c7-d142-41ec-82f0-aefbc5e622b3	4367ee14-198c-4196-89a0-b22034d5651f	Transaksi dibuat	2026-05-27 02:49:15.729
e7464e6f-13d6-4d71-8844-dfb7c0685bf8	4367ee14-198c-4196-89a0-b22034d5651f	Menunggu pembayaran	2026-05-27 02:49:15.729
1a310869-3d2f-4a53-b2d4-f9f6c6c6c2af	8bbaedc1-252c-4707-bb15-7788a91a7d50	Transaksi dibuat	2026-05-27 02:51:18.991
866a1bfc-45a4-40ae-a93b-b7a779e2f617	8bbaedc1-252c-4707-bb15-7788a91a7d50	Menunggu pembayaran	2026-05-27 02:51:18.991
8169f0ca-1be2-452e-9704-f3f4161c5be5	7a719e27-72d8-4b5d-bdcc-01adb9e5505c	Transaksi dibuat	2026-05-27 02:51:52.314
852dbd30-06f8-427d-ba02-144bc65351e7	7a719e27-72d8-4b5d-bdcc-01adb9e5505c	Menunggu pembayaran	2026-05-27 02:51:52.314
2bf429bf-9d3b-48af-a65c-4d8e491be176	309751ee-6352-4001-9746-d6873f0022a8	Transaksi dibuat	2026-05-27 03:01:42.787
bc926ace-8e69-40bb-9233-9fcdbb900368	309751ee-6352-4001-9746-d6873f0022a8	Menunggu pembayaran	2026-05-27 03:01:42.787
8ba1af41-cdfd-402a-b8e0-f0fc960aed06	37dba90e-20c8-4f28-87fb-e2570c4aa0ad	Transaksi dibuat	2026-05-28 06:18:37.326
f93c202a-17d5-4f50-a2a3-f19a3310d210	37dba90e-20c8-4f28-87fb-e2570c4aa0ad	Menunggu pembayaran	2026-05-28 06:18:37.326
6e75f241-d894-4ff6-b731-8dbf6649c0df	d904fefb-6838-46df-96f0-8933744484d5	Transaksi dibuat	2026-05-28 09:40:56.342
6f7dbc43-d7dd-4284-8685-5507907c6269	d904fefb-6838-46df-96f0-8933744484d5	Pesanan sedang dikemas	2026-05-28 09:40:56.342
4b376119-6a59-4bff-ac42-ebf06b800118	1ab19ae2-805a-4ddd-b017-8995bf8d16e7	Transaksi dibuat	2026-05-28 09:43:09.813
a110bf52-7aca-4401-9f40-3673ba03904b	1ab19ae2-805a-4ddd-b017-8995bf8d16e7	Menunggu pembayaran	2026-05-28 09:43:09.813
3d4820b4-0f56-43bc-aee5-496e42868014	0fe3426b-f88b-4231-962e-4e52e8758abd	Transaksi dibuat	2026-05-29 04:28:36.157
2936151d-89a2-4767-a3c5-1e0999d33cc4	0fe3426b-f88b-4231-962e-4e52e8758abd	Pesanan sedang dikemas	2026-05-29 04:28:36.157
1a6c33f5-853f-4cb7-860a-bfdfe9e41154	3a1f8dea-5fb5-4cab-a5dd-284ccce22ce5	Transaksi dibuat	2026-05-29 04:28:52.52
27c18dd6-1e87-4b6c-ae82-6dbaf3c63fba	3a1f8dea-5fb5-4cab-a5dd-284ccce22ce5	Pesanan sedang dikemas	2026-05-29 04:28:52.52
04b6903d-bfcd-4302-87db-dd6d65068a1d	1d164501-3f05-4705-88fa-3f70bd09ef7a	Transaksi dibuat	2026-05-29 04:30:14.839
9560f8fc-76fc-4f89-8dec-f21aca690f8e	1d164501-3f05-4705-88fa-3f70bd09ef7a	Pesanan sedang dikemas	2026-05-29 04:30:14.839
48c8aae1-40b1-45c7-9a31-7a27aa4a2f44	0dc709ea-5331-43c1-b511-581067f3b6b4	Transaksi dibuat	2026-05-29 04:30:26.367
123719d9-7128-4200-bcb5-738ffad022e4	0dc709ea-5331-43c1-b511-581067f3b6b4	Pesanan sedang dikemas	2026-05-29 04:30:26.367
1eeaebd0-b559-40b7-be93-599eb2dc094a	1eac65d5-f52c-4ea7-a2c4-0fc3d9178729	Transaksi dibuat	2026-05-29 04:30:32.346
e38dbf18-699a-4d80-b948-ded14a5e9b09	1eac65d5-f52c-4ea7-a2c4-0fc3d9178729	Pesanan sedang dikemas	2026-05-29 04:30:32.346
8ec8a546-88d4-462e-a016-aace7d4625ac	0498270b-284d-445d-9534-ace1e4eaa578	Transaksi dibuat	2026-05-29 04:30:38.035
9602aa6c-c355-45b7-8deb-3c7299f3554a	0498270b-284d-445d-9534-ace1e4eaa578	Pesanan sedang dikemas	2026-05-29 04:30:38.035
c1bd76c8-cca9-400c-8e0a-73502fb7dacc	70126e0e-e774-4787-b325-e218bfb6b770	Transaksi dibuat	2026-05-29 04:30:44.057
fc086510-e60f-4c48-ade1-f1a857ac8596	70126e0e-e774-4787-b325-e218bfb6b770	Menunggu pembayaran	2026-05-29 04:30:44.057
52925e77-a456-4c08-bb3a-c1cab1b926eb	bf46805c-3b88-4105-90c9-7ffe1badbc87	Transaksi dibuat	2026-05-29 04:30:50.928
bab428e3-fe3d-4a2f-8d32-5664946d4ccd	bf46805c-3b88-4105-90c9-7ffe1badbc87	Pesanan sedang dikemas	2026-05-29 04:30:50.928
b88bdd76-4280-436f-a5d9-d498711290bc	c30c0c3b-288a-4e71-872a-7b17d38920e4	Transaksi dibuat	2026-05-29 06:59:09.145
a718b352-27cf-440c-b9fb-4ec97bd0fb2b	c30c0c3b-288a-4e71-872a-7b17d38920e4	Pesanan sedang dikemas	2026-05-29 06:59:09.145
56266414-1809-4741-b74c-7e3c81baa32a	e3eff9a4-eb5d-46e2-8593-840729b1721d	Transaksi dibuat	2026-05-29 08:29:33.675
fa9ef880-73f4-49df-9e60-2b48e604531f	e3eff9a4-eb5d-46e2-8593-840729b1721d	Pesanan sedang dikemas	2026-05-29 08:29:33.675
272de707-ba18-40fb-9706-400a207d5466	1a9b189f-df1c-4e50-a1a4-856fe5b24dfe	Transaksi dibuat	2026-05-29 08:33:45.991
4b7fec7e-3015-40d6-ab5e-aca0ccd7e978	1a9b189f-df1c-4e50-a1a4-856fe5b24dfe	Pesanan sedang dikemas	2026-05-29 08:33:45.991
4f8c32f3-16e2-4972-bc37-8ae6d2414cb1	f2eb9369-20dd-494f-982a-4b0ebcafc10f	Transaksi dibuat	2026-05-29 13:28:11.978
f507ab1b-a6ee-4027-b6a1-4e1f2d8f9bf5	f2eb9369-20dd-494f-982a-4b0ebcafc10f	Menunggu pembayaran	2026-05-29 13:28:11.978
372f85fd-e6fd-41ec-b663-678e39f49c6c	4fc1f246-d1e9-4971-87ea-973409649aae	Transaksi dibuat	2026-05-29 13:50:23.166
af3b507a-9606-4c24-8fcd-0ab17382697c	4fc1f246-d1e9-4971-87ea-973409649aae	Menunggu pembayaran	2026-05-29 13:50:23.166
e42bd7d9-b84d-43b0-8558-7573a996ff19	6ca2219c-36ac-4d47-bb43-f3a17865f2f7	Transaksi dibuat	2026-05-30 11:46:29.905
3923a2f0-fb2f-4831-a150-6f3c591c9998	6ca2219c-36ac-4d47-bb43-f3a17865f2f7	Menunggu pembayaran	2026-05-30 11:46:29.905
0d64c5a0-f52c-48f7-bee8-e6dd664a2f39	1f131b56-9d1b-412b-ab98-23bc395a9b43	Transaksi dibuat	2026-05-30 11:58:36.365
40bc881b-750a-42f0-aee2-48d7f0c4d6d7	1f131b56-9d1b-412b-ab98-23bc395a9b43	Menunggu pembayaran	2026-05-30 11:58:36.365
4c27b10d-bb16-42f5-a053-ffdcbc963a67	578f86c6-4889-43ca-ab17-8d305a7f0cde	Transaksi dibuat	2026-05-30 12:05:54.233
0248c9c8-a193-4580-a2a2-d271d6934f27	578f86c6-4889-43ca-ab17-8d305a7f0cde	Menunggu pembayaran	2026-05-30 12:05:54.233
82cef07f-60af-4d03-aad9-abfe0d310e93	e6dd354c-914d-4775-86d7-6dcad9e33ca1	Transaksi dibuat	2026-05-30 12:05:58.04
a0cb1caa-52c7-4b48-a4bb-1b4e4e3aa79a	e6dd354c-914d-4775-86d7-6dcad9e33ca1	Menunggu pembayaran	2026-05-30 12:05:58.04
3a8ecb1c-ece2-4677-a31f-05b7f819a8fb	613466b1-6a59-4873-8ddd-86237fac40df	Transaksi dibuat	2026-05-31 03:45:59.387
a68c5c23-adff-4a18-8010-3bc5b46a709d	613466b1-6a59-4873-8ddd-86237fac40df	Menunggu pembayaran	2026-05-31 03:45:59.387
37d7e76f-ff1d-4ee4-b9f9-d50d8925f135	54de8af4-31d5-4648-a38b-7db386e3731e	Transaksi dibuat	2026-05-31 03:46:05.728
ce0fd4c2-36f7-4437-ad7e-031a99e4b006	54de8af4-31d5-4648-a38b-7db386e3731e	Pesanan sedang dikemas	2026-05-31 03:46:05.728
baeac9f5-37e0-4a38-9294-a08c3e1ad195	77c43e8c-33f4-44ee-aa8a-cf89bb6ec47d	Transaksi dibuat	2026-05-31 12:48:18.721
e90e00bc-c69d-479e-8291-dcb10794f945	77c43e8c-33f4-44ee-aa8a-cf89bb6ec47d	Menunggu pembayaran	2026-05-31 12:48:18.721
c7219020-380e-4553-8960-9193bd1ef672	e1ea49f6-1e28-407b-9626-6821d1adc217	Transaksi dibuat	2026-06-01 13:13:23.557
ea3c85b6-d0b4-4b7d-88be-8f3dce6d684f	e1ea49f6-1e28-407b-9626-6821d1adc217	Pesanan sedang dikemas	2026-06-01 13:13:23.557
be642806-781e-4b1c-aca9-aac194e8606c	71f5a35a-783b-4646-949f-d6c38aaa740e	Transaksi dibuat	2026-06-01 13:13:42.894
09c1b53f-b617-40a9-9b34-8e1c42ba6731	71f5a35a-783b-4646-949f-d6c38aaa740e	Menunggu pembayaran	2026-06-01 13:13:42.894
00e55e05-f28c-4804-bcee-0ee3d471ab87	ecf20e43-f1ec-4f73-a2ae-219eed359453	Transaksi dibuat	2026-06-01 13:14:23.921
35eb793f-f6ae-47ad-9fc7-07de8fd0dfd7	ecf20e43-f1ec-4f73-a2ae-219eed359453	Menunggu pembayaran	2026-06-01 13:14:23.921
27354b19-f25d-4b1a-8fac-4dddef04270a	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	Transaksi dibuat	2026-06-01 13:21:27.75
cd84025b-b930-408f-acdb-7af7d7ea5ed1	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	Pesanan sedang dikemas	2026-06-01 13:21:27.75
b289323e-1e14-4bda-ae07-2d337e8e2c6a	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	DIKIRIM_SELLER_10	2026-06-01 13:22:17.328
289eca8b-b1fb-4033-b86e-1d2f0fc3bc1d	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	DIKIRIM_SELLER_10	2026-06-01 13:22:17.489
ef6ab9f9-f934-4ab0-890c-cbfbb6f5084e	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	DIKIRIM_SELLER_10	2026-06-01 13:22:17.564
a44c45c4-3df4-4f2b-a425-36520e46c346	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	DIKIRIM_SELLER_10	2026-06-01 13:22:17.562
0d8398e6-067c-4638-b037-8652a20093e4	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	DIKIRIM_SELLER_10	2026-06-01 13:22:17.565
e0332def-f95e-48f1-a733-e028e85db2c6	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	DIKIRIM_SELLER_10	2026-06-01 13:22:17.566
1936f9f3-acea-432a-9844-378e3cf9ef89	b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	SELESAI_SELLER_10	2026-06-01 13:24:47.848
9953f36e-664d-48f8-aa32-4a743bfc2691	564d0220-c1ce-438e-9731-4ed4ce6ccc0e	Transaksi dibuat	2026-06-05 14:08:57.649
b80d6a5c-0bf3-46a0-9757-c42e5d523304	564d0220-c1ce-438e-9731-4ed4ce6ccc0e	Menunggu pembayaran	2026-06-05 14:08:57.649
01594c5c-c071-4ae7-b275-05e9a3043c6b	489b575a-6157-4693-9960-01b1085eca41	Transaksi dibuat	2026-06-05 14:09:04.485
8dfe4f45-8fec-4991-b7e0-0fa3996a04a7	489b575a-6157-4693-9960-01b1085eca41	Menunggu pembayaran	2026-06-05 14:09:04.485
8fbee696-184a-456b-abdd-0fd8d53e2107	7686fff2-52b9-405d-9ef0-3458232e95eb	Transaksi dibuat	2026-06-05 14:09:10.386
d812d970-1fbb-442d-8f3b-9765354e13b9	7686fff2-52b9-405d-9ef0-3458232e95eb	Pesanan sedang dikemas	2026-06-05 14:09:10.386
e4c51f33-b9a4-4587-9988-153276e53b0f	b7f6a060-e85a-4dfc-8489-0cd86459e237	Transaksi dibuat	2026-06-15 08:10:06.632
e9dac2f9-74b0-4fdd-8a91-b860336f297a	b7f6a060-e85a-4dfc-8489-0cd86459e237	Menunggu pembayaran	2026-06-15 08:10:06.632
5b1eaca6-cfb4-4ffc-b8a2-cd3a47e75d01	a0cd08bb-d823-430b-bf2e-23d5c44e0762	Transaksi dibuat	2026-06-15 08:10:33.425
137debac-f1c3-46e0-bcdf-90485df35d72	a0cd08bb-d823-430b-bf2e-23d5c44e0762	Menunggu pembayaran	2026-06-15 08:10:33.425
3230b0d2-2565-4a1a-97bf-293ab6c2e546	2417503a-c011-44c5-8ab3-9a0ce567fcdc	Transaksi dibuat	2026-06-16 02:53:30.233
1ccf6983-a22c-489a-9e1f-e0319b80672f	2417503a-c011-44c5-8ab3-9a0ce567fcdc	Menunggu pembayaran	2026-06-16 02:53:30.233
def44f35-f452-4919-abb1-b7921735877e	f911b4b8-08e7-46e7-8f1a-869136c4288e	Pesanan sedang dikemas	2026-06-16 07:06:06.752
8ba33efc-fe51-481d-ab50-c63e5182e95b	2bc0fe7c-772b-4d59-aae9-c983ce079eb4	Pesanan sedang dikemas	2026-06-16 07:57:02.215
a02c325e-7a1d-4f06-b66d-0aa01f21a0df	eb82ad63-71e3-4b78-a844-50559af1b1e1	Pesanan sedang dikemas	2026-06-16 07:58:41.672
1e1fba24-354b-43df-88d2-4c99e22bd363	b18ffbc6-6926-4005-a6e5-9c73c533a131	Pesanan sedang dikemas	2026-06-16 08:03:57.353
d3593194-e261-4880-aba6-7d8ee7e795b6	141da71e-2fa7-4d85-90dc-fb55324deef3	Pesanan sedang dikemas	2026-06-16 08:04:48.561
92052113-2b17-4d17-872f-24039d18ad9a	43a5b414-7199-407f-9064-c29c11aacf12	Menunggu pembayaran QRIS	2026-06-16 08:18:47.674
c18f6d79-2cc4-4a0a-953e-c6e68ef8d242	63ffcdd2-adce-4230-9c61-630e410acfc5	Pesanan sedang dikemas	2026-06-16 08:19:00.881
d58711de-23aa-468a-af71-ab2ccc31d811	4080a0ea-e99b-49c4-9c63-ea12659dde9b	Menunggu pembayaran QRIS	2026-06-16 08:27:24.612
\.


--
-- Data for Name: Transaksi; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."Transaksi" (id_transaksi, id_user, id_alamat, id_jasa_kirim, id_metode_pembayaran, status_transaksi, tanggal_transaksi, total_harga) FROM stdin;
4428e0b5-abd4-48b7-97b3-542c8df4c9b5	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	c318bfba-0ac2-4397-b08c-6792b2447c2a	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 13:14:33.93	19000
4b132211-858f-4422-a829-cb897cc3d292	18	ab1eed71-6300-4e81-bc61-1bbe6ff75fb9	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 14:44:08.92	26500
00456957-d2b7-48ab-91d2-cdb6c2aa73ea	18	ab1eed71-6300-4e81-bc61-1bbe6ff75fb9	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 14:44:10.199	26500
c578e56e-4a29-40d3-b686-9e0b6d3f5e5e	18	ab1eed71-6300-4e81-bc61-1bbe6ff75fb9	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 14:44:27.779	26500
4947f3f5-e9f5-4cbf-ac28-3936b2690405	18	ab1eed71-6300-4e81-bc61-1bbe6ff75fb9	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 14:45:32.44	10500
694baad0-a1c6-441c-a0a3-2afaa6796729	8	a323fa8f-1f72-4dd7-8642-503c80db9945	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 22:57:29.338	7500
864e1f8a-bf6f-4b15-8b83-fe82b9a7ebac	8	a323fa8f-1f72-4dd7-8642-503c80db9945	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 22:57:30.895	7500
9e00fea2-f6bf-4ac5-a4b9-e7d6ea88ed38	8	a323fa8f-1f72-4dd7-8642-503c80db9945	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 23:05:12.713	7500
4d9d8c61-3c09-4a12-b645-941d55743c0c	8	a323fa8f-1f72-4dd7-8642-503c80db9945	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 23:05:14.589	7500
40215c2b-ae23-4e9e-9d51-ec4f0c94664b	8	a323fa8f-1f72-4dd7-8642-503c80db9945	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 23:11:18.808	7500
66aea0b9-abec-4397-921f-f99eda1d4ed0	8	a323fa8f-1f72-4dd7-8642-503c80db9945	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 23:11:37.365	7500
b25f110c-907d-414b-8e03-2c14081b2fb7	8	a323fa8f-1f72-4dd7-8642-503c80db9945	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 23:12:07.644	7500
38e5c9ad-512c-4e31-b9dd-15929a359997	8	a323fa8f-1f72-4dd7-8642-503c80db9945	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 23:12:32.761	7500
bfeb09f1-8479-48d3-a2d1-f4de07908f3a	8	a323fa8f-1f72-4dd7-8642-503c80db9945	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-17 23:12:59.121	7500
257da69f-2189-4e6f-a3ac-d59bb2fdb47a	11	f9b99d1e-499b-4212-ae6c-3caddcf8d733	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-21 15:23:37.428	26500
40136494-19b9-49db-aa31-ba312b1a5f67	8	a323fa8f-1f72-4dd7-8642-503c80db9945	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-22 07:46:15.498	26500
267aafe6-e636-4f7e-a7a0-1538529ee2ef	10	69459d8c-2d27-4043-8f44-f0bc75a8ba40	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-24 06:20:53.666	16500
fb1dcec7-6a16-4b79-ad49-bc5c19aad70e	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-24 06:23:50.137	16500
05cf8f67-ea7f-4621-9a88-b5677a6bccfd	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-18 04:13:45.071	17500
dd6427a7-82dc-4cab-a0e0-c99b68b99371	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	SELESAI	2026-05-19 02:34:21.548	7500
f5d4ae1f-1bc8-4538-98ba-3b904246a28b	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-19 02:51:04.938	7500
c2004f1f-7025-438e-9282-fa2873934ff9	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-19 02:52:12.889	7500
70d3aac9-a7ad-4e4d-b64e-78e039a13aed	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	2e682327-6c91-418d-8354-56067c612758	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-19 02:59:02.578	2
0975a9d8-1599-453e-8727-53946ff8b96e	16	7055bd15-047d-4ad5-ba0c-0091fecc6917	67520e75-a056-447e-85f7-a9f868e043ca	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-20 00:55:12.187	14501
0d885946-94b6-4ee0-873e-39aee013669c	16	7055bd15-047d-4ad5-ba0c-0091fecc6917	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-20 00:56:42.92	26500
ede8d5ad-5b2f-42e6-b721-06efd7b5e57a	13	7b1c9ab4-bc62-4d50-ba7c-fc0aa9d05ca1	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-20 01:14:29.254	46500
08c88e4c-ebb8-4fe8-ac69-ec75ee03c771	13	7b1c9ab4-bc62-4d50-ba7c-fc0aa9d05ca1	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-20 01:20:02.99	16500
59187485-d31c-4bf9-a4ca-d83c607b91b7	20	ec856292-8040-4af1-b93c-54577c61d9c7	552a879c-f42e-421d-b3f6-abc8d9fbe468	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-20 03:49:22.832	214000
5b8e104c-5363-4f09-95c6-835c2190302c	20	ec856292-8040-4af1-b93c-54577c61d9c7	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-20 03:50:55.006	206500
1dbcdc5e-10fe-43e9-a85d-9a3ea064771a	20	ec856292-8040-4af1-b93c-54577c61d9c7	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-20 03:51:06.545	206500
e3331a25-7c15-4fd2-b9af-7b02da3d965b	20	ec856292-8040-4af1-b93c-54577c61d9c7	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-20 03:52:24.656	106500
72bbd75e-a492-4025-a3d2-d3243ab36901	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-24 06:48:21.063	16500
041e216f-5995-49a9-a481-ec7b3fdf0f79	11	f9b99d1e-499b-4212-ae6c-3caddcf8d733	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-24 07:45:57.416	26500
fda4b075-f7ac-4cb2-af32-75eb2310c5a1	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-24 09:55:04.403	16500
2d7283ea-2ed0-4b32-b1fd-e86eb2c77607	11	f9b99d1e-499b-4212-ae6c-3caddcf8d733	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-24 10:00:03.086	26500
431c3215-fd5d-436c-8e9d-9ae3beb1acb7	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 05:42:52.078	16500
04f26057-78bb-46ef-826c-f38620b27ea4	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 05:47:18.306	6501
1572c31a-49e5-4611-bbd2-c281ffc11981	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 05:50:16.702	6501
ae81d4a9-418c-4668-9f96-617c122dbcf1	15	f085d0c0-8f4d-418d-9f86-7a6fb55987ce	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 05:51:23.558	21500
2c9cb8c4-5b2e-49b6-8987-fd0c3c208fc0	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 05:57:03.86	6501
5ed2be0b-c4a4-42fe-baf6-888ee2e160b1	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 05:58:35.513	6501
5cff752e-70be-4460-b62f-be1ca2a62d13	4	bcf49f13-871f-4f98-828b-fae268b39bfb	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 13:22:33.303	8500
72caa5ce-5b7c-4e68-9f66-c83c60b974cc	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 13:24:27.614	24500
3dafbe1a-ecb5-4156-9d68-dbd43d1f6e16	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 13:24:51.483	7500
02c75cf9-37c4-4764-8280-5fbf6b89daf4	4	bcf49f13-871f-4f98-828b-fae268b39bfb	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 13:27:19.857	8500
405ece06-0b1c-44fd-8fbc-8af5f115061c	4	bcf49f13-871f-4f98-828b-fae268b39bfb	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 13:27:32.915	8500
4c5cd29c-f95b-4efa-8590-83c376f03895	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 13:32:25.843	24500
63f514ec-a748-432e-aee2-a0216f8f2fd9	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 13:34:54.74	64500
cbf9dd54-7453-42e4-bfb0-6e2c8143e747	4	bcf49f13-871f-4f98-828b-fae268b39bfb	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 13:49:38.262	8500
853d06d6-a69c-4928-b4cf-ba5ede973fab	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-26 13:53:13.764	7500
a5552ab7-625f-471d-88cd-d5fdd79e8dae	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-26 13:54:50.589	7500
7750855c-b4ca-4d8d-81f7-dc840ef8b56a	4	bcf49f13-871f-4f98-828b-fae268b39bfb	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 14:10:15.673	8500
6d1feb1f-ca06-4860-9188-8280ef9e1675	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 14:47:29.338	16500
02b0b57b-1277-42d4-818e-2f4fe7ff9569	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-26 14:47:38.943	16500
806c0868-75f8-495a-8242-95a1489b419e	35	ce604028-cd04-46d3-a25b-e797efa2e98b	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-26 17:16:00.987	6501
fc64ffa4-d795-4757-a625-353a847dca2d	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-27 01:59:04.744	7500
cabbb8d3-770f-491c-98ed-28785e80ca82	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-27 02:00:47.682	7000
4367ee14-198c-4196-89a0-b22034d5651f	15	f085d0c0-8f4d-418d-9f86-7a6fb55987ce	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-27 02:49:15.729	6501
8bbaedc1-252c-4707-bb15-7788a91a7d50	15	f085d0c0-8f4d-418d-9f86-7a6fb55987ce	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-27 02:51:18.991	6501
7a719e27-72d8-4b5d-bdcc-01adb9e5505c	15	f085d0c0-8f4d-418d-9f86-7a6fb55987ce	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-27 02:51:52.314	6501
309751ee-6352-4001-9746-d6873f0022a8	28	ceebe198-ca0d-4b3b-8426-1dc4ec450597	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-27 03:01:42.787	24500
37dba90e-20c8-4f28-87fb-e2570c4aa0ad	43	3d682f04-36d0-4135-abab-fac0aa019c38	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-28 06:18:37.326	16500
d904fefb-6838-46df-96f0-8933744484d5	11	f9b99d1e-499b-4212-ae6c-3caddcf8d733	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-28 09:40:56.342	406500
1ab19ae2-805a-4ddd-b017-8995bf8d16e7	11	f9b99d1e-499b-4212-ae6c-3caddcf8d733	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-28 09:43:09.813	10006500
0fe3426b-f88b-4231-962e-4e52e8758abd	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-29 04:28:36.157	24500
3a1f8dea-5fb5-4cab-a5dd-284ccce22ce5	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-29 04:28:52.52	24500
1d164501-3f05-4705-88fa-3f70bd09ef7a	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-29 04:30:14.839	24500
0dc709ea-5331-43c1-b511-581067f3b6b4	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-29 04:30:26.367	24500
1eac65d5-f52c-4ea7-a2c4-0fc3d9178729	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-29 04:30:32.346	24500
0498270b-284d-445d-9534-ace1e4eaa578	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-29 04:30:38.035	24500
70126e0e-e774-4787-b325-e218bfb6b770	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-29 04:30:44.057	24500
bf46805c-3b88-4105-90c9-7ffe1badbc87	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-29 04:30:50.928	24500
c30c0c3b-288a-4e71-872a-7b17d38920e4	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-29 06:59:09.145	24500
e3eff9a4-eb5d-46e2-8593-840729b1721d	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-29 08:29:33.675	24500
1a9b189f-df1c-4e50-a1a4-856fe5b24dfe	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-29 08:33:45.991	24500
f2eb9369-20dd-494f-982a-4b0ebcafc10f	25	9b054d17-8fe5-49d6-8979-78a54e1d91af	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-29 13:28:11.978	16500
4fc1f246-d1e9-4971-87ea-973409649aae	40	861491b8-f890-4ecd-8ce1-410c5aa81d14	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-29 13:50:23.166	16500
6ca2219c-36ac-4d47-bb43-f3a17865f2f7	40	861491b8-f890-4ecd-8ce1-410c5aa81d14	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-30 11:46:29.905	8500
1f131b56-9d1b-412b-ab98-23bc395a9b43	45	db8bd1f6-7fbe-482f-9a8c-27ba6f611485	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-30 11:58:36.365	20006500
578f86c6-4889-43ca-ab17-8d305a7f0cde	40	861491b8-f890-4ecd-8ce1-410c5aa81d14	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-30 12:05:54.233	16500
e6dd354c-914d-4775-86d7-6dcad9e33ca1	40	861491b8-f890-4ecd-8ce1-410c5aa81d14	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-30 12:05:58.04	16500
613466b1-6a59-4873-8ddd-86237fac40df	11	f9b99d1e-499b-4212-ae6c-3caddcf8d733	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-31 03:45:59.387	10006500
54de8af4-31d5-4648-a38b-7db386e3731e	11	f9b99d1e-499b-4212-ae6c-3caddcf8d733	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-05-31 03:46:05.728	10006500
77c43e8c-33f4-44ee-aa8a-cf89bb6ec47d	30	1720c511-2985-4748-bc17-c41bd295c42a	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-05-31 12:48:18.721	7500
e1ea49f6-1e28-407b-9626-6821d1adc217	10	57071156-ed45-4c74-8b73-df73af477c79	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-06-01 13:13:23.557	21500
71f5a35a-783b-4646-949f-d6c38aaa740e	10	69459d8c-2d27-4043-8f44-f0bc75a8ba40	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-06-01 13:13:42.894	21500
ecf20e43-f1ec-4f73-a2ae-219eed359453	10	69459d8c-2d27-4043-8f44-f0bc75a8ba40	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-06-01 13:14:23.921	21500
b1b9d4d4-ec87-41ea-b7b1-7035cd71602f	28	ceebe198-ca0d-4b3b-8426-1dc4ec450597	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	SELESAI	2026-06-01 13:21:27.75	16500
564d0220-c1ce-438e-9731-4ed4ce6ccc0e	10	69459d8c-2d27-4043-8f44-f0bc75a8ba40	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-06-05 14:08:57.649	16500
489b575a-6157-4693-9960-01b1085eca41	10	69459d8c-2d27-4043-8f44-f0bc75a8ba40	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-06-05 14:09:04.485	16500
7686fff2-52b9-405d-9ef0-3458232e95eb	10	69459d8c-2d27-4043-8f44-f0bc75a8ba40	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-06-05 14:09:10.386	16500
b7f6a060-e85a-4dfc-8489-0cd86459e237	50	8ac1b28c-ee25-413e-b4d0-2742ae63a1ae	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-06-15 08:10:06.632	16500
a0cd08bb-d823-430b-bf2e-23d5c44e0762	50	8ac1b28c-ee25-413e-b4d0-2742ae63a1ae	2e682327-6c91-418d-8354-56067c612758	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-06-15 08:10:33.425	10001
2417503a-c011-44c5-8ab3-9a0ce567fcdc	10	57071156-ed45-4c74-8b73-df73af477c79	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	BELUM_BAYAR	2026-06-16 02:53:30.233	7500
f911b4b8-08e7-46e7-8f1a-869136c4288e	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	DIKEMAS	2026-06-16 07:06:06.752	16500
2bc0fe7c-772b-4d59-aae9-c983ce079eb4	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	DIKEMAS	2026-06-16 07:57:02.215	21500
eb82ad63-71e3-4b78-a844-50559af1b1e1	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	DIKEMAS	2026-06-16 07:58:41.672	6501
b18ffbc6-6926-4005-a6e5-9c73c533a131	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	DIKEMAS	2026-06-16 08:03:57.353	16500
141da71e-2fa7-4d85-90dc-fb55324deef3	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	DIKEMAS	2026-06-16 08:04:48.561	16500
43a5b414-7199-407f-9064-c29c11aacf12	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	MENUNGGU_PEMBAYARAN	2026-06-16 08:18:47.674	21500
63ffcdd2-adce-4230-9c61-630e410acfc5	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	ceb331a0-1415-4dcc-a8ce-d71c2150947b	DIKEMAS	2026-06-16 08:19:00.881	21500
4080a0ea-e99b-49c4-9c63-ea12659dde9b	12	ce76cda3-ddd5-47c1-9687-cc3644ac09bd	a1fb1c3d-1b4a-4618-b74a-5fb1fc68e001	36d8c930-1d16-481d-a286-8d88df21e5c3	MENUNGGU_PEMBAYARAN	2026-06-16 08:27:24.612	10006500
\.


--
-- Data for Name: User; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."User" (id, username, email, password, "createdAt", role) FROM stdin;
2	KeanzxBatagor	keanu1@gmail.com	$2b$10$uH7LHGBnNW45P2tT6VlKSeUCciyMKfNUz.rjv1qFLgx8nR6WSl4/2	2026-05-14 01:03:07.246	SELLER
3	ardian	ardian@gmail.com	$2b$10$BQUDOCODwZ8rekY7qtFFteujJzOTO2NmVNqFYEI/PzCqa7LvRaeVi	2026-05-14 02:07:40.578	BUYER
5	KeanzxBatagor1	keanu2@gmail.com	$2b$10$Il3exBqM8y4UcvLysrRgUOuWPAHHNx56ZfEUr1vW43/RrlJINwwNO	2026-05-14 02:26:20.906	BUYER
7	masrusdi	rusdi@gmail.com	$2b$10$dT3vvaRFWWWCvc/4OgeN6.JXyJS3BGes.N9r1oC3Klmn6dnJpjIG2	2026-05-14 02:32:20.285	SELLER
6	ardian	ardian@greenmarket.com	$2b$10$bKArUP9iMJiwj6Cvnuui/eFuDOO.Pixz9afa81Qoj5UWfXlX1lKUe	2026-05-14 02:26:38.025	SELLER
8	ardianbeli	ardianbeli@greenmarket.com	$2b$10$ezh8uePW/ifoJOZ4XPjcEOznqgju/2RF3dMHy.AH9bGlwOZx4ObMC	2026-05-14 05:26:58.932	BUYER
9	testuser	test123@gmail.com	$2b$10$muxQ//eChXCzDdxSx3uVdeukzXUq0GGDRN9zW/H0XzfOodepkVYS2	2026-05-14 05:46:38.786	BUYER
15	KeanzxSuperz	keanu@greenmarket.com	$2b$10$v.PohZa4TwZ2fR/Dw2HFA./6Bit9kICSvMZk3h6Zemhqp4Ry/HQIm	2026-05-17 07:14:38.526	ADMIN
10	testuser1012	testuser@greenmarket.com	$2b$10$fZtvkNhnwn1f5eed71kMR.SYfzfJVYhkjUUOZNykV9tq40qGPJpeW	2026-05-14 07:54:28.415	SELLER
1	userbaru	userbaru@greenmarket.com	$2b$10$GZxbFoFpkMhkhW0sYcc2o.lxx2DlI7GR1vGGsDaiu/oFdYAYhrALq	2026-05-14 00:29:16.828	SELLER
30	testuser2	testuser2@greenmarket.com	$2b$10$1kfqvBPUSJ6uUKFRhAwWeOil7xNEdtGInIYqzMw8KqhxaPTQZFciC	2026-05-24 13:43:27.476	SELLER
14	testing	testing@greenmarket.com	$2b$10$HcIeBdFEfBaSHtMWRaSGYO9ucWyViWKMIm4gSQJLbEMBCA0iGzjAa	2026-05-15 00:01:49.897	SELLER
13	testuser202	testuser202@greenmarket.com	$2b$10$TYTqDJQn0qzY3D5cns0jzemz2Js/wIri/k0UJkci3GnrL.RDxy596	2026-05-14 13:46:37.419	SELLER
37	selleracc	selleracc@greenmarket.com	$2b$10$XiFwoPE/xttQytGfqVgzRuZIVOPNZSXSr0MW5OehJd7KMxfBDGSbG	2026-05-27 07:20:43.8	SELLER
17	keanzx	keanu1@greenmarket.com	$2b$10$O.E.4vJuG7c/HfNrYTM0teuQsWu2js5FDUn89aQvchFdCm8/n5Z3a	2026-05-17 13:45:40.861	BUYER
18	KeanuGaming	KeanuGaming@greenmarket.com	$2b$10$.eLqk/D9yGjVrSS2DPOJyuG33a2zhqJ18/N9OEZ7gofgQOImVJT4K	2026-05-17 14:40:45.436	BUYER
33	admintest	admintest@greenmarket.com	$2b$10$jnydfB6dntuNgUFUApkLi.Bwvi/naJf23erYjd96A.5TZUdUmJXTi	2026-05-26 03:28:42.454	ADMIN
19	ardian2	ardian2@greenmarket.com	$2b$10$kuS5rbYX/GJr3NxagSW2T.J3tfHKSf1RYnzJL0eoMW9F5nSYlYG2m	2026-05-17 23:38:08.924	SELLER
21	Guest User	guest@greenmarket.local	guest-no-login	2026-05-18 00:29:10.071	GUEST
12	arisu	arisu@greenmarket.com	$2b$10$B7/hK5j7/CNDeRzFVFQht.jPqp3m2jD.7Z7lxQUBddjCFjE.RBoH6	2026-05-14 12:30:53.487	BUYER
23	KeanzxLemari	keanu321@greenmarket.com	$2b$10$k.UTXSKvnectaamZdabY3eAawkpTiRPm8Uhf.iOpfTVzTfdmOJQAC	2026-05-19 15:17:38.189	SELLER
20	KeanuPertama	keanu2@greenmarket.com	$2b$10$h/UE2fnXomJL90uncacHUep5SvvsS.Xe0xMmVxsSzn/0VFG/JDbEC	2026-05-18 00:04:04.712	SELLER
24	Yorune	yoru@greenmarket.com	$2b$10$w45a3Y9TVsa2E7hD3q3c3.et2XGlWWs9rfVo7torVFfwBTsIqMzD6	2026-05-21 23:36:00.795	SELLER
26	testuser100	testuser100@greenmarket.com	$2b$10$uvomslfl5szIBEOGBG7h9.zNqnZlPe9eAzyAL0SPbUB6wrDstvX1G	2026-05-22 07:45:13.256	BUYER
27	dummy	dummy1@greenmarket.com	$2b$10$Y4ez5danrULfamZvGxhWhOCCnP1GmmVgmcm6aulXDMDcsyqkVcd1m	2026-05-22 07:46:32.342	BUYER
34	admin	admin@greenmarket.com	$2b$10$zWiUzWpxu20nWKoCOnTDPeKGLE6hT7QFKWVFNRGjQzvmwuEhWy8ou	2026-05-26 07:24:26.853	ADMIN
43	BillyTheDuck	keanu123@greenmarket.com	$2b$10$Ehm8BRALUDQ.OdaeIxZhRuaHALN0hSt4yd8mFNjg3dtM.LwZKdedm	2026-05-27 14:50:28.26	SELLER
39	lastreg	lastreg@greenmarket.com	$2b$10$xv8DMuN9paWMUELfSyPxuuf/D/5HP/DPEmDQoLgdTy0XKfDha/79O	2026-05-27 09:13:58.829	BUYER
29	testuser1	testuser1@greenmarket.com	$2b$10$v.SecmjO2Sof1Ap0AShiYuAr7jL0ZXTHJ6oQdk9eydHn9xli2ccXa	2026-05-24 13:23:44.018	BUYER
35	emily	emily@greenmarket.com	$2b$10$grg6o0lPtXCsLvK7Ot9CWuSN4zPPz0ruln.ipMfKpUNnSI2Pv2Vhu	2026-05-26 08:26:44.674	SELLER
41	sellerlast	sellerlast@greenmarket.com	$2b$10$vop6q7AnBpyHnDyb3e2wY.fVaN0noBnNA.2cTZxvVOZ6ZFxaaiWgq	2026-05-27 10:16:43.613	BUYER
25	Test	testimoni@greenmarket.com	$2b$10$VN62SurysQBRdTyt5r.Ex.tbJyK2E5pQN4mhwG.XIWRrxrp7r8StG	2026-05-22 02:42:26.062	SELLER
4	billy	billy@gmail.com	$2b$10$BtiDV2N9oscFYkSspY5b9.Y/S3gHSm3w.k2K3ZTCVsYs.k7Qf3ZxW	2026-05-14 02:16:32.594	BUYER
22	Keanzx2213	keanu3@greenmarket.com	$2b$10$tLkCNwDJaluXG2HvXQkFs.43Q.HRUPJq3OYu1LfmnyHSl8sW5NrvG	2026-05-19 14:29:54.873	SELLER
31	keanu3213	keanu12@greenmarket.com	$2b$10$GWkNexZbiwFKPTFi.pG1LO/clPFmLX.uXGXyG81EORSwU3NeQ2I1a	2026-05-25 05:14:37.899	SELLER
32	Seiji	seiji@greenmarket.com	$2b$10$4vkuddRJfdyyDjhzJg2CKezt1muemPXnv1Mom.Fm1qnRTf4/K0Ko6	2026-05-26 03:08:50.756	ADMIN
36	test_java	java@greenmarket.com	$2a$10$uyPfE7CGmGlbnaNz1AkaIOe3Y.47SXcF8B41Rc8qvnyAMW..aPVta	2026-05-27 02:17:13.858	BUYER
11	testuser101	testuser101@greenmarket.com	$2b$10$t7UBIatohVuYMaZjVFFidOx6FOJCP.NH8ZWgkbFShY/FH5iLyn2Vi	2026-05-14 09:24:33.486	SELLER
42	seller	seller@greenmarket.com	$2b$10$JZ5fhC.0IupsSHhTi6I20OKujc2XlRVq.IOwbtQSrMWiJJOJLh.PS	2026-05-27 10:22:18.805	SELLER
40	Test	lastnih@greenmarket.com	$2b$10$gfIgRNRNSg.ci8WzC1ez7eNvrSVj3t7pR8zsULvfDJwC9RlCq3Qna	2026-05-27 09:15:01.697	BUYER
44	arima	arima@greenmarket.com	$2a$10$VzYzCc/Z7SaClqQ6wGovUONUmuCf5Zn4KRzv6GJ.5djMACaHNYAYa	2026-05-28 05:54:24.928	BUYER
16	testuser303	testuser303@greenmarket.com	$2b$10$UubJcQFMmqcGHnAcpfZu1.9PsB4mZqQlV8.yK5gqLEIsUUDyoJTRy	2026-05-17 07:37:43.198	SELLER
45	Arief	wafdan@greenmarket.com	$2b$10$c.idmqF3TFxBPzXMgiC7KuMbjZINN/QLfsrPIROGJ0694bkRB9kwa	2026-05-30 11:54:44.523	SELLER
28	TestDummy	TestDummy@greenmarket.com	$2b$10$YPjyCDYvMW3y6fah3pGNLehxusCbjvAicWZhMmt.1X6LkIFtLn.g.	2026-05-22 07:48:04.569	BUYER
38	lasttest	lasttest@greenmarket.com	$2b$10$xQao1uWNPN5fScrFL5zl..1lp1L6ZG8sWTyo.4a445Lt/ax3wpLoi	2026-05-27 09:12:53.899	BUYER
48	Keanzx111	keanu1234@greenmarket.com	$2b$10$zR9Iw7Uc7EDBybAawxnLFuBMxFyqwWHBQWnVoaAZ8FiGlCXAsR7ny	2026-06-09 07:56:09.624	SELLER
46	test123	test123@greenmarket.com	$2b$10$0cu6VkMbYbaSseYMRp.j8OW08dFQq77Xmjfuc32sPnxzyDQ5sDJcK	2026-06-01 13:34:55.194	BUYER
47	test_user	test_user@greenmarket.com	$2b$10$3qzbDUDcQW8frv3MPFHRveND0Y1DOkIG08kPID4m08oCmb.MUURHq	2026-06-09 07:52:30.234	BUYER
49	barubaru	barubaru@greenmarket.com	$2b$10$V/Byzjd54Wns3kG1uxbjJOJe7.nRU70sA91CRdzvf7uboihlsSqUG	2026-06-10 02:58:25.053	BUYER
50	p1ardian	p1@greenmarket.com	$2b$10$7zJtxkM7RjCNzPBJTTyVo.IjezJnZHIQJHbuKlxSQ8oYwROs5khSe	2026-06-15 08:07:18.594	SELLER
51	sikeanzx	sikeanzx@greenmarket.com	$2a$10$ivVPzU6wywPtqTl68erJD.PiOo8eStPbodmM.hUz5RN60VGuRh63i	2026-06-16 05:02:29.633	SELLER
\.


--
-- Name: User_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."User_id_seq"', 51, true);


--
-- Name: Alamat Alamat_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Alamat"
    ADD CONSTRAINT "Alamat_pkey" PRIMARY KEY (id_alamat);


--
-- Name: Detail_Transaksi Detail_Transaksi_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Detail_Transaksi"
    ADD CONSTRAINT "Detail_Transaksi_pkey" PRIMARY KEY (id_detail);


--
-- Name: Jasa_Kirim Jasa_Kirim_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Jasa_Kirim"
    ADD CONSTRAINT "Jasa_Kirim_pkey" PRIMARY KEY (id_jasa);


--
-- Name: Kategori_Produk Kategori_Produk_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Kategori_Produk"
    ADD CONSTRAINT "Kategori_Produk_pkey" PRIMARY KEY (id_kategori);


--
-- Name: Keranjang Keranjang_id_user_id_produk_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Keranjang"
    ADD CONSTRAINT "Keranjang_id_user_id_produk_key" UNIQUE (id_user, id_produk);


--
-- Name: Keranjang Keranjang_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Keranjang"
    ADD CONSTRAINT "Keranjang_pkey" PRIMARY KEY (id_keranjang);


--
-- Name: Metode_Pembayaran Metode_Pembayaran_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Metode_Pembayaran"
    ADD CONSTRAINT "Metode_Pembayaran_pkey" PRIMARY KEY (id_metode);


--
-- Name: Pembayaran Pembayaran_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Pembayaran"
    ADD CONSTRAINT "Pembayaran_pkey" PRIMARY KEY (id_pembayaran);


--
-- Name: Produk Produk_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Produk"
    ADD CONSTRAINT "Produk_pkey" PRIMARY KEY (id_produk);


--
-- Name: Toko Toko_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Toko"
    ADD CONSTRAINT "Toko_pkey" PRIMARY KEY (id_toko);


--
-- Name: TrackingLog TrackingLog_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."TrackingLog"
    ADD CONSTRAINT "TrackingLog_pkey" PRIMARY KEY (id_log);


--
-- Name: Transaksi Transaksi_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Transaksi"
    ADD CONSTRAINT "Transaksi_pkey" PRIMARY KEY (id_transaksi);


--
-- Name: User User_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (id);


--
-- Name: Detail_Transaksi_id_produk_idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "Detail_Transaksi_id_produk_idx" ON public."Detail_Transaksi" USING btree (id_produk);


--
-- Name: Detail_Transaksi_id_transaksi_idx; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX "Detail_Transaksi_id_transaksi_idx" ON public."Detail_Transaksi" USING btree (id_transaksi);


--
-- Name: Pembayaran_id_transaksi_key; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX "Pembayaran_id_transaksi_key" ON public."Pembayaran" USING btree (id_transaksi);


--
-- Name: Toko_id_user_key; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX "Toko_id_user_key" ON public."Toko" USING btree (id_user);


--
-- Name: User_email_key; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX "User_email_key" ON public."User" USING btree (email);


--
-- Name: Wishlist_id_user_id_produk_key; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX "Wishlist_id_user_id_produk_key" ON public."Keranjang" USING btree (id_user, id_produk);


--
-- Name: Alamat Alamat_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Alamat"
    ADD CONSTRAINT "Alamat_id_user_fkey" FOREIGN KEY (id_user) REFERENCES public."User"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Detail_Transaksi Detail_Transaksi_id_produk_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Detail_Transaksi"
    ADD CONSTRAINT "Detail_Transaksi_id_produk_fkey" FOREIGN KEY (id_produk) REFERENCES public."Produk"(id_produk) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: Detail_Transaksi Detail_Transaksi_id_transaksi_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Detail_Transaksi"
    ADD CONSTRAINT "Detail_Transaksi_id_transaksi_fkey" FOREIGN KEY (id_transaksi) REFERENCES public."Transaksi"(id_transaksi) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Keranjang Keranjang_id_produk_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Keranjang"
    ADD CONSTRAINT "Keranjang_id_produk_fkey" FOREIGN KEY (id_produk) REFERENCES public."Produk"(id_produk) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Keranjang Keranjang_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Keranjang"
    ADD CONSTRAINT "Keranjang_id_user_fkey" FOREIGN KEY (id_user) REFERENCES public."User"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Pembayaran Pembayaran_id_transaksi_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Pembayaran"
    ADD CONSTRAINT "Pembayaran_id_transaksi_fkey" FOREIGN KEY (id_transaksi) REFERENCES public."Transaksi"(id_transaksi) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Produk Produk_id_kategori_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Produk"
    ADD CONSTRAINT "Produk_id_kategori_fkey" FOREIGN KEY (id_kategori) REFERENCES public."Kategori_Produk"(id_kategori) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: Produk Produk_id_user_seller_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Produk"
    ADD CONSTRAINT "Produk_id_user_seller_fkey" FOREIGN KEY (id_user_seller) REFERENCES public."User"(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: Toko Toko_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Toko"
    ADD CONSTRAINT "Toko_id_user_fkey" FOREIGN KEY (id_user) REFERENCES public."User"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: TrackingLog TrackingLog_id_transaksi_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."TrackingLog"
    ADD CONSTRAINT "TrackingLog_id_transaksi_fkey" FOREIGN KEY (id_transaksi) REFERENCES public."Transaksi"(id_transaksi) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Transaksi Transaksi_id_alamat_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Transaksi"
    ADD CONSTRAINT "Transaksi_id_alamat_fkey" FOREIGN KEY (id_alamat) REFERENCES public."Alamat"(id_alamat) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: Transaksi Transaksi_id_jasa_kirim_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Transaksi"
    ADD CONSTRAINT "Transaksi_id_jasa_kirim_fkey" FOREIGN KEY (id_jasa_kirim) REFERENCES public."Jasa_Kirim"(id_jasa) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: Transaksi Transaksi_id_metode_pembayaran_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Transaksi"
    ADD CONSTRAINT "Transaksi_id_metode_pembayaran_fkey" FOREIGN KEY (id_metode_pembayaran) REFERENCES public."Metode_Pembayaran"(id_metode) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: Transaksi Transaksi_id_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."Transaksi"
    ADD CONSTRAINT "Transaksi_id_user_fkey" FOREIGN KEY (id_user) REFERENCES public."User"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

\unrestrict S0b6A4dM1pS6dlSaEHT6xwa3hdQsz0yj2cXupV8sxSo0VSfpRiCb0L4RzUElV7R

