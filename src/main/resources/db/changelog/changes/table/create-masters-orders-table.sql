--liquibase formatted sql
--changeset <postgres>:<create-masters-orders-table>
CREATE TABLE IF NOT EXISTS public.masters_orders
(
    master_id bigint NOT NULL,
    order_id bigint NOT NULL,
    CONSTRAINT uk_hjyrk2w0ydcbun0a2ky1yb09u UNIQUE (order_id),
    CONSTRAINT fklehmwwquvu45bn07mp6ow8mqi FOREIGN KEY (master_id)
        REFERENCES public.masters (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkr1v8bwcjbn9om2kl47alwabi3 FOREIGN KEY (order_id)
        REFERENCES public.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE movie_characters;