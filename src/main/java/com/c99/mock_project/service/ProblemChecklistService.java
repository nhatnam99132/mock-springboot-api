package com.c99.mock_project.service;

import com.c99.mock_project.model.ProblemChecklist;
import com.c99.mock_project.repository.ProblemChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemChecklistService {
    private final ProblemChecklistRepository problemChecklistRepository;

    @Autowired
    public ProblemChecklistService(ProblemChecklistRepository problemChecklistRepository) {
        this.problemChecklistRepository = problemChecklistRepository;
    }

    public List<ProblemChecklist> getAllProblemChecklists() {
        return problemChecklistRepository.findAll();
    }

    public ProblemChecklist getProblemChecklistById(Long id) {
        return problemChecklistRepository.findById(id).orElse(null);
    }

    public ProblemChecklist saveProblemChecklist(ProblemChecklist problemChecklist) {
        return problemChecklistRepository.save(problemChecklist);
    }

    public void deleteProblemChecklist(Long id) {
        problemChecklistRepository.deleteById(id);
    }

    public ProblemChecklist updateProblemChecklist(Long id, ProblemChecklist problemChecklistDetails) {
        return problemChecklistRepository
                .findById(id)
                .map(problemChecklist -> {
                    problemChecklist.setProblems(problemChecklistDetails.getProblems());
                    problemChecklist.setDate(problemChecklistDetails.getDate());
                    return problemChecklistRepository.save(problemChecklist);
                }).orElse(null);
    }
}
