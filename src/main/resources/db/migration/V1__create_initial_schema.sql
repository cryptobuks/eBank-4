--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.6
-- Dumped by pg_dump version 9.5.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: creditor; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE creditor (
    id integer NOT NULL,
    name text,
    address text,
    transaction_number character(16)
);


--
-- Name: creditor_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE creditor_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: creditor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE creditor_id_seq OWNED BY creditor.id;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE customer (
    id integer NOT NULL,
    firstname text,
    lastname text,
    password text,
    embg character varying(13) NOT NULL,
    address text NOT NULL,
    email text,
    transaction_number character varying(16) NOT NULL,
    balance real
);


--
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE customer_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE customer_id_seq OWNED BY customer.id;


--
-- Name: customer_role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE customer_role (
    id integer NOT NULL,
    customer_id integer,
    role_id integer
);


--
-- Name: customer_role_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE customer_role_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: customer_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE customer_role_id_seq OWNED BY customer_role.id;


--
-- Name: order; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "order" (
    id integer NOT NULL,
    customer_id integer,
    creditor_id integer,
    date date,
    amount real
);


--
-- Name: order_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE order_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE order_id_seq OWNED BY "order".id;


--
-- Name: role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE role (
    id integer NOT NULL,
    name text
);


--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE role_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE role_id_seq OWNED BY role.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY creditor ALTER COLUMN id SET DEFAULT nextval('creditor_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer ALTER COLUMN id SET DEFAULT nextval('customer_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_role ALTER COLUMN id SET DEFAULT nextval('customer_role_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "order" ALTER COLUMN id SET DEFAULT nextval('order_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY role ALTER COLUMN id SET DEFAULT nextval('role_id_seq'::regclass);


--
-- Name: creditor_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY creditor
    ADD CONSTRAINT creditor_pk PRIMARY KEY (id);


--
-- Name: customer_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pk PRIMARY KEY (id);


--
-- Name: customer_role_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_role
    ADD CONSTRAINT customer_role_pk PRIMARY KEY (id);


--
-- Name: customer_unique; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_unique UNIQUE (email);


--
-- Name: order_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT order_pk PRIMARY KEY (id);


--
-- Name: role_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pk PRIMARY KEY (id);


--
-- Name: fki_cr_customer_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_cr_customer_id ON customer_role USING btree (customer_id);


--
-- Name: fki_cr_role_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_cr_role_id ON customer_role USING btree (role_id);


--
-- Name: fki_order_creditor_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_order_creditor_id ON "order" USING btree (creditor_id);


--
-- Name: fki_order_customer_id; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_order_customer_id ON "order" USING btree (customer_id);


--
-- Name: customer_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_role
    ADD CONSTRAINT customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer(id);


--
-- Name: fk_order_creditor_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT fk_order_creditor_id FOREIGN KEY (creditor_id) REFERENCES creditor(id);


--
-- Name: fk_order_customer_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "order"
    ADD CONSTRAINT fk_order_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id);


--
-- Name: fk_role_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customer_role
    ADD CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES role(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

