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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(generator = "car_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "car_id_seq", sequenceName = "car_id_seq",
            allocationSize = 1)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Brand brand;
    private String model;
    @Column(name = "manufacture_year")
    private Integer manufactureYear;
    private String number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public enum Brand {
        TOYOTA("Toyota"),
        BMW("BMW"),
        RENAULT("Renault"),
        FORD("Ford"),
        SUZUKI("Suzuki"),
        HONDA("Honda"),
        OPEL("Open"),
        FERRARI("Ferrari"),
        AUDI("Audi"),
        JEEP("Jeep"),
        ISUZU("Isuzu"),
        LADA("Lada"),
        LANOS("Lanos"),
        LEXUS("Lexus"),
        MAZDA("Mazda"),
        MITSUBISHI("Mitsubishi");
        private String brand;

        Brand(String brand) {
            this.brand = brand;
        }
    }
}
