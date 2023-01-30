--liquibase formatted sql
--changeset <postgres>:<create-orders-services-table>
CREATE TABLE IF NOT EXISTS public.orders_services
(
    order_id bigint NOT NULL,
    service_id bigint NOT NULL,
    CONSTRAINT uk_6stku4m0dy2cj3phyilue8998 UNIQUE (service_id),
    CONSTRAINT fkmlxtixo7scj7qi4p35vltpsg2 FOREIGN KEY (service_id)
        REFERENCES public.services (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkq863ndc65lt9lgj0jg1h8ravg FOREIGN KEY (order_id)
        REFERENCES public.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE movie_characters;