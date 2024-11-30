package com.c99.mock_project.services;

import com.c99.mock_project.entities.VehicleDetails;
import com.c99.mock_project.repositories.VehicleDetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleDetailsService {

    @Autowired
    private VehicleDetailsRepository vehicleDetailsRepository;
    @Autowired
    private ModelMapper modelMapper;


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
            modelMapper.map(vehicleDetailsDetails, vehicleDetails);
            return vehicleDetailsRepository.save(vehicleDetails);
        }).orElse(null);
    }

}
