--liquibase formatted sql
--changeset <postgres>:<create-services-table>
CREATE TABLE IF NOT EXISTS public.services
(
    id bigint NOT NULL,
    price numeric(38,2),
    status character varying(255) COLLATE pg_catalog."default",
    master_id bigint,
    order_id bigint,
    CONSTRAINT services_pkey PRIMARY KEY (id),
    CONSTRAINT fk565hd47u11qajksyfi4gqrfu0 FOREIGN KEY (master_id)
        REFERENCES public.masters (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fknmykpsxcf4bgaecn9g3vdbc1s FOREIGN KEY (order_id)
        REFERENCES public.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE movie_characters;