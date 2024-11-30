package com.c99.mock_project.repositories;

import com.c99.mock_project.entities.AccidentHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccidentHistoryRepository extends JpaRepository<AccidentHistory, Long> {
}
