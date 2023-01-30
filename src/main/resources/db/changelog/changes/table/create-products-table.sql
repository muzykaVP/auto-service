--liquibase formatted sql
--changeset <postgres>:<create-products-table>
CREATE TABLE IF NOT EXISTS public.products
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    price numeric(38,2) DEFAULT 0,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

--rollback DROP TABLE movie_characters;