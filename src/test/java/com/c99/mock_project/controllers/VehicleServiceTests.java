package com.c99.mock_project.controllers;

import com.c99.mock_project.entities.Vehicle;
import com.c99.mock_project.repositories.VehicleRepository;
import com.c99.mock_project.services.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

        when(vehicleRepository.findAll()).thenReturn(vehicles);
        List<Vehicle> result = vehicleService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(vehicleRepository).findAll();
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
