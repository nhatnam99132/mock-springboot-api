package com.c99.mock_project.repositories;

import com.c99.mock_project.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByVin(@Param("vin") String vin);
}
