package com.c99.mock_project.repositories;

import com.c99.mock_project.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByVin(@Param("vin") String vin);
}
