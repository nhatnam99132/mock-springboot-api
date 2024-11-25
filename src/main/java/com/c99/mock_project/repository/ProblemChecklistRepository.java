package com.c99.mock_project.repository;

import com.c99.mock_project.model.ProblemChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProblemChecklistRepository extends JpaRepository<ProblemChecklist, Long> {
}
