--
-- PostgreSQL database dump
--

-- Dumped from database version 10.1
-- Dumped by pg_dump version 10.1

-- Started on 2018-01-18 12:17:08

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2821 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 16575)
-- Name: cv; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE cv (
    id bigint NOT NULL,
    name text,
    placeofbirth text,
    dateofbirth date,
    nationality text,
    email text,
    phone text,
    dateofcreation date
);


ALTER TABLE cv OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16573)
-- Name: cv_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cv_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cv_id_seq OWNER TO postgres;

--
-- TOC entry 2822 (class 0 OID 0)
-- Dependencies: 196
-- Name: cv_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cv_id_seq OWNED BY cv.id;


--
-- TOC entry 199 (class 1259 OID 16598)
-- Name: education; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE education (
    id bigint NOT NULL,
    name text,
    beginning date,
    ending date,
    belongsto bigint,
    achievement text
);


ALTER TABLE education OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16596)
-- Name: education_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE education_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE education_id_seq OWNER TO postgres;

--
-- TOC entry 2823 (class 0 OID 0)
-- Dependencies: 198
-- Name: education_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE education_id_seq OWNED BY education.id;


--
-- TOC entry 201 (class 1259 OID 16609)
-- Name: job; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE job (
    id bigint NOT NULL,
    name text,
    beginning date,
    ending date,
    belongsto bigint,
    "position" text,
    responsibilities text
);


ALTER TABLE job OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16607)
-- Name: job_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE job_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE job_id_seq OWNER TO postgres;

--
-- TOC entry 2824 (class 0 OID 0)
-- Dependencies: 200
-- Name: job_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE job_id_seq OWNED BY job.id;


--
-- TOC entry 2685 (class 2604 OID 16582)
-- Name: cv id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cv ALTER COLUMN id SET DEFAULT nextval('cv_id_seq'::regclass);


--
-- TOC entry 2686 (class 2604 OID 16601)
-- Name: education id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY education ALTER COLUMN id SET DEFAULT nextval('education_id_seq'::regclass);


--
-- TOC entry 2687 (class 2604 OID 16612)
-- Name: job id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY job ALTER COLUMN id SET DEFAULT nextval('job_id_seq'::regclass);


--
-- TOC entry 2691 (class 2606 OID 16606)
-- Name: education Education_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY education
    ADD CONSTRAINT "Education_pkey" PRIMARY KEY (id);


--
-- TOC entry 2693 (class 2606 OID 16617)
-- Name: job Job_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY job
    ADD CONSTRAINT "Job_pkey" PRIMARY KEY (id);


--
-- TOC entry 2689 (class 2606 OID 16584)
-- Name: cv cv_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cv
    ADD CONSTRAINT cv_pkey PRIMARY KEY (id);


-- Completed on 2018-01-18 12:17:08

--
-- PostgreSQL database dump complete
--

