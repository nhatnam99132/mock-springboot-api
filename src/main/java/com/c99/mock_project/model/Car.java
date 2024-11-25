package com.c99.mock_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vin;
    private String wmi; // World Manufacturer Identifier
    private String vds; // Vehicle Descriptor Section
    private String vis; // Vehicle Identifier Section
    private String manufacturer;
    private String brand;
    private String model;
    private String engine;

    @Column(name = "\"year\"")
    private int year;
    private String trim;
    private int mileage;
    private String urlLink;
}


