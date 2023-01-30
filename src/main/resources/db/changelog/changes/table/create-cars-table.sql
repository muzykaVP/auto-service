--liquibase formatted sql
--changeset <postgres>:<create-cars-table>
CREATE TABLE IF NOT EXISTS public.cars
(
    id bigint NOT NULL,
    brand character varying(255) COLLATE pg_catalog."default",
    manufacture_year integer,
    model character varying(255) COLLATE pg_catalog."default",
    "number" character varying(255) COLLATE pg_catalog."default",
    owner_id bigint,
    CONSTRAINT cars_pkey PRIMARY KEY (id),
    CONSTRAINT fk3bo7py913bmgscqhthnhpxuau FOREIGN KEY (owner_id)
        REFERENCES public.owners (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE movie_characters;