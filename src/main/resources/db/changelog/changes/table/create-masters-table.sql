--liquibase formatted sql
--changeset <postgres>:<create-masters-table>
CREATE TABLE IF NOT EXISTS public.masters
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT masters_pkey PRIMARY KEY (id)
);

--rollback DROP TABLE movie_characters;