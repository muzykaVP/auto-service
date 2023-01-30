--liquibase formatted sql
--changeset <postgres>:<create-orders-table>
CREATE TABLE IF NOT EXISTS public.orders
(
    id bigint NOT NULL,
    completion_date timestamp(6) without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    final_price numeric(38,2) DEFAULT 0,
    order_date timestamp(6) without time zone,
    status character varying(255) COLLATE pg_catalog."default",
    car_id bigint,
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT fkd2p23ixwrrt395glgi9nnbj23 FOREIGN KEY (car_id)
        REFERENCES public.cars (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE movie_characters;