package com.c99.mock_project.controllers;

import com.c99.mock_project.entities.Vehicle;
import com.c99.mock_project.models.ErrorResponse;
import com.c99.mock_project.models.VehicleDTO;
import com.c99.mock_project.services.VehicleService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAlls() {
        logger.info("Fetching all vehicles");
        List<Vehicle> vehicles = vehicleService.getAll();
        List<VehicleDTO> vehicleDTOs = vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
        logger.info("Successfully fetched {} vehicles", vehicleDTOs.size());
        return ResponseEntity.ok(vehicleDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, WebRequest request) {
        logger.info("Fetching vehicle with ID: {}", id);
        Vehicle vehicle = vehicleService.getById(id);

        if (vehicle != null) {
            VehicleDTO vehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);
            logger.info("Vehicle found with ID: {}", id);
            return ResponseEntity.ok(vehicleDTO);
        } else {
            logger.warn("Vehicle not found with ID: {}", id);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setMessage("Vehicle not found for ID: " + id);
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setError("No vehicle with the given ID was found in the system.");
            errorResponse.setPath(request.getDescription(false));

            return ResponseEntity.ok(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<VehicleDTO> create(@Valid @RequestBody VehicleDTO vehicleDTO) {
        logger.info("Creating new vehicle: {}", vehicleDTO);
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        Vehicle createdVehicle = vehicleService.save(vehicle);
        VehicleDTO createdVehicleDTO = modelMapper.map(createdVehicle, VehicleDTO.class);
        logger.info("Vehicle created with ID: {}", createdVehicleDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicleDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody VehicleDTO vehicleDTO, WebRequest request) {
        logger.info("Updating vehicle with ID: {}", id);
        vehicleDTO.setId(id);
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        Vehicle updatedVehicle = vehicleService.update(id, vehicle);

        if (updatedVehicle != null) {
            logger.info("Vehicle updated successfully with ID: {}", id);
            return ResponseEntity.ok(modelMapper.map(updatedVehicle, VehicleDTO.class));
        } else {
            logger.warn("Vehicle not found with ID: {}", id);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setMessage("Vehicle not found for ID: " + id);
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setError("No vehicle with the given ID was found to update.");
            errorResponse.setPath(request.getDescription(false));

            return ResponseEntity.ok(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, WebRequest request) {
        logger.info("Deleting vehicle with ID: {}", id);
        Vehicle vehicleToDelete = vehicleService.getById(id);

        if (vehicleToDelete != null) {
            vehicleService.delete(id);
            logger.info("Vehicle deleted successfully with ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Vehicle not found with ID: {}", id);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setMessage("Vehicle not found for ID: " + id);
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setError("No vehicle with the given ID was found to delete.");
            errorResponse.setPath(request.getDescription(false));

            return ResponseEntity.ok(errorResponse);
        }
    }
}
