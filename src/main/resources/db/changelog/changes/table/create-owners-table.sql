--liquibase formatted sql
--changeset <postgres>:<create-owners-table>
CREATE TABLE IF NOT EXISTS public.owners
(
    id bigint NOT NULL,
    CONSTRAINT owners_pkey PRIMARY KEY (id)
);

--rollback DROP TABLE movie_characters;