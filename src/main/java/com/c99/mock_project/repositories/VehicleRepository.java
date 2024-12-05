package com.c99.mock_project.repositories;

import com.c99.mock_project.entities.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByVin(@Param("vin") String vin);
    Page<Vehicle> findByBrandContainingOrModelContaining(String brand, String model, Pageable pageable);
}
