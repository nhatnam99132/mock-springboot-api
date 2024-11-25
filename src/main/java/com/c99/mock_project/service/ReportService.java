package com.c99.mock_project.service;

import com.c99.mock_project.model.Report;
import com.c99.mock_project.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

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
            report.setSummary(reportDetails.getSummary());
            report.setVin(reportDetails.getVin());
            report.setVehicleSpecifications(reportDetails.getVehicleSpecifications());
            report.setReportedIncidents(reportDetails.getReportedIncidents());
            report.setProblemChecklist(reportDetails.getProblemChecklist());
            return reportRepository.save(report);
        }).orElse(null);
    }
}
