package com.c99.mock_project.repository;

import com.c99.mock_project.model.AccidentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AccidentHistoryRepository extends JpaRepository<AccidentHistory, Long> {
}
