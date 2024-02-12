--
-- PostgreSQL database dump
--

-- Dumped from database version 11.14
-- Dumped by pg_dump version 11.14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS iuin;
--
-- Name: iuin; Type: DATABASE; Schema: -; Owner: aaa
--

CREATE DATABASE iuin WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';


ALTER DATABASE iuin OWNER TO aaa;

\connect iuin

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: t_student; Type: TABLE; Schema: public; Owner: aaa
--

CREATE TABLE public.t_student (
                                  id bigint NOT NULL,
                                  name integer,
                                  age integer
);


ALTER TABLE public.t_student OWNER TO aaa;

--
-- Name: t_student_id_seq; Type: SEQUENCE; Schema: public; Owner: aaa
--

CREATE SEQUENCE public.t_student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.t_student_id_seq OWNER TO aaa;

--
-- Name: t_student_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aaa
--

ALTER SEQUENCE public.t_student_id_seq OWNED BY public.t_student.id;


--
-- Name: t_student id; Type: DEFAULT; Schema: public; Owner: aaa
--

ALTER TABLE ONLY public.t_student ALTER COLUMN id SET DEFAULT nextval('public.t_student_id_seq'::regclass);


--
-- Data for Name: t_student; Type: TABLE DATA; Schema: public; Owner: aaa
--

COPY public.t_student (id, name, age) FROM stdin;
\.


--
-- Name: t_student_id_seq; Type: SEQUENCE SET; Schema: public; Owner: aaa
--

SELECT pg_catalog.setval('public.t_student_id_seq', 1, false);


--
-- Name: t_student t_student_pk; Type: CONSTRAINT; Schema: public; Owner: aaa
--

ALTER TABLE ONLY public.t_student
    ADD CONSTRAINT t_student_pk PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--
