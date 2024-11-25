package com.c99.mock_project.service;

import com.c99.mock_project.model.AccidentHistory;
import com.c99.mock_project.repository.AccidentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccidentHistoryService {
    private final AccidentHistoryRepository accidentHistoryRepository;

    @Autowired
    public AccidentHistoryService(AccidentHistoryRepository accidentHistoryRepository) {
        this.accidentHistoryRepository = accidentHistoryRepository;
    }

    public List<AccidentHistory> getAllAccidentHistory() {
        return accidentHistoryRepository.findAll();
    }

    public AccidentHistory getAccidentHistoryById(Long id) {
        return accidentHistoryRepository.findById(id).orElse(null);
    }

    public AccidentHistory saveAccidentHistory(AccidentHistory accidentHistory) {
        return accidentHistoryRepository.save(accidentHistory);
    }

    public void deleteAccidentHistory(Long id) {
        accidentHistoryRepository.deleteById(id);
    }

    public AccidentHistory updateAccidentHistory(Long id, AccidentHistory accidentHistoryDetails) {
        return accidentHistoryRepository.findById(id).map(accidentHistory -> {
            accidentHistory.setAccidentDetails(accidentHistoryDetails.getAccidentDetails());
            accidentHistory.setDate(accidentHistoryDetails.getDate());
            return accidentHistoryRepository.save(accidentHistory);
        }).orElse(null);
    }
}
