--liquibase formatted sql
--changeset <postgres>:<create-owners-table>
CREATE TABLE IF NOT EXISTS public.owners_cars
(
    owner_id bigint NOT NULL,
    car_id bigint NOT NULL,
    CONSTRAINT uk_4s5l3sbsvheqehaat4rg8qn2g UNIQUE (car_id),
    CONSTRAINT fkl3bgvt7natjt1rydg5avnmhcd FOREIGN KEY (owner_id)
        REFERENCES public.owners (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkobosrw1pt1tmgeqeftq01ldae FOREIGN KEY (car_id)
        REFERENCES public.cars (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE movie_characters;