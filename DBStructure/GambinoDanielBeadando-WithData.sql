--
-- PostgreSQL database dump
--

-- Dumped from database version 10.1
-- Dumped by pg_dump version 10.1

-- Started on 2018-01-18 12:15:40

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 2816 (class 0 OID 16575)
-- Dependencies: 197
-- Data for Name: cv; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cv (id, name, placeofbirth, dateofbirth, nationality, email, phone, dateofcreation) FROM stdin;
62	Gambino Daniel	San Dona di Piave	2018-01-17	magyar	gdani14@gmail.com	06306624350	2018-01-17
\.


--
-- TOC entry 2818 (class 0 OID 16598)
-- Dependencies: 199
-- Data for Name: education; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY education (id, name, beginning, ending, belongsto, achievement) FROM stdin;
5	Zipernowsky Károly Műszaki Szakközépiskola	2007-09-01	2013-08-31	62	Érettségi + OKJ (Webmester)
6	PTE TTK - Programtervező Informatikus	2014-09-01	2500-08-31	62	Diploma
\.


--
-- TOC entry 2820 (class 0 OID 16609)
-- Dependencies: 201
-- Data for Name: job; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY job (id, name, beginning, ending, belongsto, "position", responsibilities) FROM stdin;
8	Solymosi és Társa Bt	2013-07-01	2013-08-31	62	Webmester	Weboldal létrehozása
9	Grappa Pizzéria	2016-08-01	2017-03-31	62	Diszpécser	Rendelések felvétele
\.


--
-- TOC entry 2831 (class 0 OID 0)
-- Dependencies: 196
-- Name: cv_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cv_id_seq', 62, true);


--
-- TOC entry 2832 (class 0 OID 0)
-- Dependencies: 198
-- Name: education_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('education_id_seq', 6, true);


--
-- TOC entry 2833 (class 0 OID 0)
-- Dependencies: 200
-- Name: job_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('job_id_seq', 9, true);


-- Completed on 2018-01-18 12:15:43

--
-- PostgreSQL database dump complete
--

