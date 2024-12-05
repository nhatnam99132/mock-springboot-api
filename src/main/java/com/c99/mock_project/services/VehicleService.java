package com.c99.mock_project.services;

import com.c99.mock_project.entities.Vehicle;
import com.c99.mock_project.repositories.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Page<Vehicle> getAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable); // Return paginated Vehicle entities
    }

    public long vehiclesCount() {
        return vehicleRepository.count();
    }

    public Page<Vehicle> searchVehicles(String search, PageRequest pageRequest) {
        return vehicleRepository.findByBrandContainingOrModelContaining(search, search, pageRequest);
    }


    public Vehicle getById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void delete(Long id) {
        vehicleRepository.deleteById(id);
    }

    public void deleteAll() {
        vehicleRepository.deleteAll();
    }

    public Vehicle update(Long id, Vehicle vehicleDetails) {
        return vehicleRepository.findById(id).map(vehicle -> {
            modelMapper.map(vehicleDetails, vehicle);
            return vehicleRepository.save(vehicle);
        }).orElse(null);
    }

}
