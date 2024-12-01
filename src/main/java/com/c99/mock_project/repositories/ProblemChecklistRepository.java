package com.c99.mock_project.repositories;

import com.c99.mock_project.entities.ProblemChecklist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProblemChecklistRepository extends JpaRepository<ProblemChecklist, Long> {
}
