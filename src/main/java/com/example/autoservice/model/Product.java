package com.example.autoservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(generator = "product_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    @Column(columnDefinition = "numeric(38,2) default 0")
    private BigDecimal price;
}
