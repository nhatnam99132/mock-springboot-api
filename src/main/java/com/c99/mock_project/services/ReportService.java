package com.c99.mock_project.services;

import com.c99.mock_project.entities.Report;
import com.c99.mock_project.repositories.ReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    public Report updateReport(Long id, Report reportDetails) {
        return reportRepository.findById(id).map(report -> {
            modelMapper.map(reportDetails, report);
            return reportRepository.save(report);
        }).orElse(null);
    }
}
