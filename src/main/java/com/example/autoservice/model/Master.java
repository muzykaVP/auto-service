package com.example.autoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "masters")
public class Master {
    @Id
    @GeneratedValue(generator = "master_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "master_id_seq", sequenceName = "master_id_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany
    @JoinTable(name = "masters_orders",
            joinColumns = @JoinColumn(name = "master_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;
}
