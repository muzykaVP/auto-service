--liquibase formatted sql
--changeset <postgres>:<create-owners-orders-table>
CREATE TABLE IF NOT EXISTS public.owners_orders
(
    owner_id bigint NOT NULL,
    order_id bigint NOT NULL,
    CONSTRAINT uk_3nyffnq542wils22wjbf779a7 UNIQUE (order_id),
    CONSTRAINT fk72iccnam7p59oel627kryqyv2 FOREIGN KEY (owner_id)
        REFERENCES public.owners (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhdu8v0nt2g2wtp40r58tvfy4b FOREIGN KEY (order_id)
        REFERENCES public.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE movie_characters;