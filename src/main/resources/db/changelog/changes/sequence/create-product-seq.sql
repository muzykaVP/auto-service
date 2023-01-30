--liquibase formatted sql
--changeset <postgres>:<create-product-sequence-id>
    CREATE SEQUENCE IF NOT EXISTS public.product_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.product_id_seq;