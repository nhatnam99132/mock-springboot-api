package com.c99.mock_project.repository;

import com.c99.mock_project.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(value = "SELECT * FROM report WHERE vin = :vin", nativeQuery = true)
    Optional<Report> findByVin(@Param("vin") String vin);
}
