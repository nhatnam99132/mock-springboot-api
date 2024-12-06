package com.c99.mock_project.controllers;

import com.c99.mock_project.entities.Vehicle;
import com.c99.mock_project.models.ApiResponse;
import com.c99.mock_project.models.ApiResponsePagination;
import com.c99.mock_project.models.ErrorResponse;
import com.c99.mock_project.models.VehicleDTO;
import com.c99.mock_project.services.VehicleService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<ApiResponsePagination<List<VehicleDTO>>> getAlls(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search) {

        logger.info("Fetching vehicles with page: {} and size: {}", page, size);

        // Create PageRequest for pagination
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Vehicle> vehiclePage;

        // If search query is provided, use the search method
        if (search != null && !search.isEmpty()) {
            vehiclePage = vehicleService.searchVehicles(search, pageRequest);  // Assuming search method exists in service
        } else {
            vehiclePage = vehicleService.getAll(pageRequest);  // Default method for getting all vehicles
        }

        // Convert the Page<Vehicle> to a List of VehicleDTOs
        List<VehicleDTO> vehicleDTOs = vehiclePage.getContent().stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))  // Map each Vehicle to VehicleDTO
                .collect(Collectors.toList());
        long totalVehicles = vehicleService.vehiclesCount();
        logger.info("Successfully fetched {} vehicles", vehicleDTOs.size());

        // Construct the paginated ApiResponsePagination
        ApiResponsePagination<List<VehicleDTO>> response = new ApiResponsePagination<>(
                vehicleDTOs,  // Data: List of VehicleDTOs
                null,  // Error: Assuming no error, else set the error response here
                vehiclePage.getTotalElements(),  // Total elements (for pagination)
                vehiclePage.getTotalPages(),  // Total pages (for pagination)
                totalVehicles
        );

        // Return the response wrapped in ResponseEntity
        return ResponseEntity.ok(response);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getById(@PathVariable Long id, WebRequest request) {
        logger.info("Fetching vehicle with ID: {}", id);
        Vehicle vehicle = vehicleService.getById(id);

        if (vehicle != null) {
            VehicleDTO vehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);
            logger.info("Vehicle found with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>(vehicleDTO, null));
        } else {
            logger.warn("Vehicle not found with ID: {}", id);

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setCode(HttpStatus.NOT_FOUND.value());
            errorResponse.setError("Vehicle Not Found");
            errorResponse.setMessage("Vehicle not found for ID: " + id);
            errorResponse.setPath(request.getDescription(false));

            return ResponseEntity.ok(new ApiResponse<>(null, errorResponse));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleDTO>> create(@Valid @RequestBody VehicleDTO vehicleDTO) {
        logger.info("Creating new vehicle: {}", vehicleDTO);
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        Vehicle createdVehicle = vehicleService.save(vehicle);
        VehicleDTO createdVehicleDTO = modelMapper.map(createdVehicle, VehicleDTO.class);
        logger.info("Vehicle created with ID: {}", createdVehicleDTO.getId());

        // Trả về ApiResponse với data là createdVehicleDTO và error là null
        ApiResponse<VehicleDTO> response = new ApiResponse<>(createdVehicleDTO, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleDTO>> update(@PathVariable Long id, @Valid @RequestBody VehicleDTO vehicleDTO, WebRequest request) {
        logger.info("Updating vehicle with ID: {}", id);
        vehicleDTO.setId(id);
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        Vehicle updatedVehicle = vehicleService.update(id, vehicle);

        if (updatedVehicle != null) {
            logger.info("Vehicle updated successfully with ID: {}", id);
            VehicleDTO updatedVehicleDTO = modelMapper.map(updatedVehicle, VehicleDTO.class);
            ApiResponse<VehicleDTO> response = new ApiResponse<>(updatedVehicleDTO, null);
            return ResponseEntity.ok(response);
        } else {
            logger.warn("Vehicle not found with ID: {}", id);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setMessage("Vehicle not found for ID: " + id);
            errorResponse.setCode(HttpStatus.NOT_FOUND.value());
            errorResponse.setError("No vehicle with the given ID was found to update.");
            errorResponse.setPath(request.getDescription(false));

            ApiResponse<VehicleDTO> response = new ApiResponse<>(null, errorResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, WebRequest request) {
        logger.info("Deleting vehicle with ID: {}", id);
        Vehicle vehicleToDelete = vehicleService.getById(id);

        if (vehicleToDelete != null) {
            vehicleService.delete(id);
            logger.info("Vehicle deleted successfully with ID: {}", id);
            ApiResponse<Void> response = new ApiResponse<>(null, null);  // No data to return, error is null
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } else {
            logger.warn("Vehicle not found with ID: {}", id);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setMessage("Vehicle not found for ID: " + id);
            errorResponse.setCode(HttpStatus.NOT_FOUND.value());
            errorResponse.setError("No vehicle with the given ID was found to delete.");
            errorResponse.setPath(request.getDescription(false));

            ApiResponse<Void> response = new ApiResponse<>(null, errorResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
