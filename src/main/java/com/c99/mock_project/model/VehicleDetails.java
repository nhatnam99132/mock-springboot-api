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
public class VehicleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @Column(name = "\"year\"")
    private int year;
    private String make;
    private String model;
    private String trim;
    private String engine;
    private String madeIn;
    private String style;
    private String steeringType;
    private String antiBrakeSystem;
    private String fuelType;
    private String bodyStyle;
    private String driveLine;
    private double overallHeight;
    private double overallLength;
    private double overallWidth;
    private int standardSeating;
    private String tires;
    private double highwayMileage;
}
