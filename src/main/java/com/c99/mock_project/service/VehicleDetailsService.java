package com.c99.mock_project.service;

import com.c99.mock_project.model.VehicleDetails;
import com.c99.mock_project.repository.VehicleDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleDetailsService {
    private final VehicleDetailsRepository vehicleDetailsRepository;

    @Autowired
    public VehicleDetailsService(VehicleDetailsRepository vehicleDetailsRepository) {
        this.vehicleDetailsRepository = vehicleDetailsRepository;
    }

    public List<VehicleDetails> getAllVehicleDetails() {
        return vehicleDetailsRepository.findAll();
    }

    public VehicleDetails getVehicleDetailsById(Long id) {
        return vehicleDetailsRepository.findById(id).orElse(null);
    }

    public VehicleDetails saveVehicleDetails(VehicleDetails vehicleDetails) {
        return vehicleDetailsRepository.save(vehicleDetails);
    }

    public void deleteVehicleDetails(Long id) {
        vehicleDetailsRepository.deleteById(id);
    }

    public VehicleDetails updateVehicleDetails(Long id, VehicleDetails vehicleDetailsDetails) {
        return vehicleDetailsRepository.findById(id).map(vehicleDetails -> {
            vehicleDetails.setYear(vehicleDetailsDetails.getYear());
            vehicleDetails.setMake(vehicleDetailsDetails.getMake());
            vehicleDetails.setModel(vehicleDetailsDetails.getModel());
            vehicleDetails.setTrim(vehicleDetailsDetails.getTrim());
            vehicleDetails.setEngine(vehicleDetailsDetails.getEngine());
            vehicleDetails.setMadeIn(vehicleDetailsDetails.getMadeIn());
            vehicleDetails.setStyle(vehicleDetailsDetails.getStyle());
            vehicleDetails.setSteeringType(vehicleDetailsDetails.getSteeringType());
            vehicleDetails.setAntiBrakeSystem(vehicleDetailsDetails.getAntiBrakeSystem());
            vehicleDetails.setFuelType(vehicleDetailsDetails.getFuelType());
            vehicleDetails.setBodyStyle(vehicleDetailsDetails.getBodyStyle());
            vehicleDetails.setDriveLine(vehicleDetailsDetails.getDriveLine());
            vehicleDetails.setOverallHeight(vehicleDetailsDetails.getOverallHeight());
            vehicleDetails.setOverallLength(vehicleDetailsDetails.getOverallLength());
            vehicleDetails.setOverallWidth(vehicleDetailsDetails.getOverallWidth());
            vehicleDetails.setStandardSeating(vehicleDetailsDetails.getStandardSeating());
            vehicleDetails.setTires(vehicleDetailsDetails.getTires());
            vehicleDetails.setHighwayMileage(vehicleDetailsDetails.getHighwayMileage());
            return vehicleDetailsRepository.save(vehicleDetails);
        }).orElse(null);
    }

}
