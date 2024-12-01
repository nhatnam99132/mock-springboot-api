package com.c99.mock_project.repositories;

import com.c99.mock_project.entities.VehicleDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehicleDetailsRepository extends JpaRepository<VehicleDetails, Long> {
}
