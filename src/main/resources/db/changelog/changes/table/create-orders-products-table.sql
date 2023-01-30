--liquibase formatted sql
--changeset <postgres>:<create-orders-products-table>
CREATE TABLE IF NOT EXISTS public.orders_products
(
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    CONSTRAINT uk_2g3y176wcughtr1ag76m3mnli UNIQUE (product_id),
    CONSTRAINT fk43vke5jd6eyasd92t3k24kdxq FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fke4y1sseio787e4o5hrml7omt5 FOREIGN KEY (order_id)
        REFERENCES public.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE movie_characters;