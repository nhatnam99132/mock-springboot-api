package com.c99.mock_project.services;

import com.c99.mock_project.entities.AccidentHistory;
import com.c99.mock_project.repositories.AccidentHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccidentHistoryService {

    @Autowired
    private AccidentHistoryRepository accidentHistoryRepository;
    @Autowired
    private ModelMapper modelMapper;

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
            modelMapper.map(accidentHistoryDetails, accidentHistory);
            return accidentHistoryRepository.save(accidentHistory);
        }).orElse(null);
    }
}
