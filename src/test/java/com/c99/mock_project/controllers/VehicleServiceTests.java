package com.c99.mock_project.controllers;

import com.c99.mock_project.entities.Vehicle;
import com.c99.mock_project.repositories.VehicleRepository;
import com.c99.mock_project.services.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VehicleServiceTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;


    @Test
    public void testGetAllVehicles()
    {
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle = new Vehicle();
        vehicles.add(vehicle);

        // Create Pageable with pagination info (page 0, 10 items per page)
        Pageable pageable = PageRequest.of(0, 10);

        // Create a Page object with mock data
        Page<Vehicle> vehiclePage = new PageImpl<>(vehicles, pageable, vehicles.size());

        // Mock the repository to return the Page object
        when(vehicleRepository.findAll(pageable)).thenReturn(vehiclePage);

        // Call the service method (which should return a list of vehicles with pagination)
        Page<Vehicle> result = vehicleService.getAll(pageable);

        // Assertions to verify the behavior
        assertNotNull(result);  // Ensure the result is not null
        assertEquals(1, result.getContent().size());  // Verify the size of the list inside the Page
        verify(vehicleRepository).findAll(pageable);  // Verify that findAll() was called with pageable
    }

    @Test
    public void testFindVehicle()
    {
        Vehicle vehicle = new Vehicle();

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        Vehicle result = vehicleService.getById(1L);

        assertNotNull(result);
        verify(vehicleRepository).findById(1L);
    }

    @Test
    public void testCreateVehicle()
    {
        Vehicle vehicle = new Vehicle();
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        Vehicle result = vehicleService.save(vehicle);
        assertNotNull(result);
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    public void testUpdateVehicle()
    {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        Vehicle result = vehicleService.update(vehicle.getId(), vehicle);
        assertNotNull(result);
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    public void testDeleteVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        doNothing().when(vehicleRepository).deleteById(vehicle.getId());
        vehicleService.delete(vehicle.getId());
        verify(vehicleRepository).deleteById(vehicle.getId());
    }

    @Test
    public void testDeleteAllVehicles() {
        doNothing().when(vehicleRepository).deleteAll();
        vehicleService.deleteAll();
        verify(vehicleRepository).deleteAll();
    }

}
