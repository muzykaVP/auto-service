package com.example.autoservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = "order_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq",
            allocationSize = 1)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
    private String description;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @OneToMany
    @JoinTable(name = "orders_services",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Service> services;
    @OneToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "final_price",columnDefinition = "numeric(38,2) default 0")
    private BigDecimal finalPrice;
    private LocalDateTime completionDate;

    public enum Status {
        RECEIVED("Received"),
        IN_PROGRESS("In progress"),
        SUCCESSFULLY_COMPLETED("Successfully completed"),
        UNSUCCESSFULLY_COMPLETED("Unsuccessfully completed"),
        PAID("Paid");
        private String status;

        Status(String status) {
            this.status = status;
        }
    }
}
