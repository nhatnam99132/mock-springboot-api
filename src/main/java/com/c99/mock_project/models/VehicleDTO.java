package com.c99.mock_project.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;


@Data
public class VehicleDTO {
    private Long id;

    @NotNull(message = "VIN cannot be null")
    @Size(min = 17, max = 17, message = "VIN must be exactly 17 characters")
    private String vin;

    @NotNull(message = "WMI cannot be null")
    @Size(min = 3, max = 3, message = "WMI must be exactly 3 characters")
    private String wmi;

    @Size(min = 4, max = 5, message = "VDS must be between 4 and 5 characters")
    private String vds;

    @NotNull(message = "VIS cannot be null")
    private String vis;

    @NotNull(message = "Manufacturer cannot be null")
    private String manufacturer;

    @NotNull(message = "Brand cannot be null")
    private String brand;

    @NotNull(message = "Model cannot be null")
    private String model;

    @NotNull(message = "Engine cannot be null")
    private String engine;

    @Min(value = 1900, message = "Year must be greater than or equal to 1900")
    private int year;

    private String trim;

    @Min(value = 0, message = "Mileage cannot be negative")
    private int mileage;

    private String urlLink;
}
