package com.c99.mock_project.configuration;

import com.c99.mock_project.entities.Vehicle;
import com.c99.mock_project.repositories.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(VehicleRepository vehicleRepository) {
        return args -> {
            Optional<Vehicle> existingVehicle1 = vehicleRepository.findByVin("1HGCM82633A123456");
            Optional<Vehicle> existingVehicle2 = vehicleRepository.findByVin("1HGCM82633A654321");

            Vehicle vehicle1 = existingVehicle1.orElseGet(() -> Vehicle.builder()
                    .vin("1HGCM82633A123456")
                    .wmi("1HG")
                    .vds("CM826")
                    .vis("33A123456")
                    .manufacturer("Honda")
                    .brand("Accord")
                    .model("EX")
                    .engine("2.4L I4")
                    .year(2020)
                    .trim("Sedan")
                    .mileage(15000)
                    .build());

            Vehicle vehicle2 = existingVehicle2.orElseGet(() -> Vehicle.builder()
                    .vin("1HGCM82633A654321")
                    .wmi("1HG")
                    .vds("CM826")
                    .vis("33A654321")
                    .manufacturer("Honda")
                    .brand("Civic")
                    .model("LX")
                    .engine("1.8L I4")
                    .year(2019)
                    .trim("Coupe")
                    .mileage(20000)
                    .build());

            vehicleRepository.save(vehicle1);
            vehicleRepository.save(vehicle2);

        };
    }
}
