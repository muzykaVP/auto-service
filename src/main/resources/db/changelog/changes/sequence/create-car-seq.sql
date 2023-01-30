--liquibase formatted sql
--changeset <postgres>:<create-car-sequence-id>
    CREATE SEQUENCE IF NOT EXISTS public.car_id_seq INCREMENT 1 START 1 MINVALUE 1;
--rollback DROP SEQUENCE public.car_id_seq;