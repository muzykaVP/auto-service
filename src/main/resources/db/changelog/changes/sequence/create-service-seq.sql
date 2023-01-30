--liquibase formatted sql
--changeset <postgres>:<create-service-sequence-id>
    CREATE SEQUENCE IF NOT EXISTS public.service_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.service_id_seq;