package com.c99.mock_project.services;

import com.c99.mock_project.entities.ProblemChecklist;
import com.c99.mock_project.repositories.ProblemChecklistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemChecklistService {

    @Autowired
    private ProblemChecklistRepository problemChecklistRepository;
    @Autowired
    private ModelMapper modelMapper;

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
                    modelMapper.map(problemChecklistDetails, problemChecklist);
                    return problemChecklistRepository.save(problemChecklist);
                }).orElse(null);
    }
}
