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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(generator = "service_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "service_id_seq", sequenceName = "service_id_seq",
            allocationSize = 1)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    private Master master;
    @Column(columnDefinition = "numeric(38,2) default 0")
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PAID("Paid"),
        FREE("Free"),
        UNPAID("Unpaid");
        private String status;

        Status(String status) {
            this.status = status;
        }
    }
}
