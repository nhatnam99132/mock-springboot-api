package com.c99.mock_project.repository;

import com.c99.mock_project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(value = "SELECT * FROM car WHERE vin = :vin", nativeQuery = true)
    Optional<Car> findByVin(@Param("vin") String vin);
}
