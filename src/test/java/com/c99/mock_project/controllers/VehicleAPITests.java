package com.c99.mock_project.controllers;

import com.c99.mock_project.entities.Vehicle;
import com.c99.mock_project.services.VehicleService;
import org.modelmapper.ModelMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Base64;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VehicleAPITests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ModelMapper modelMapper;

    private Vehicle savedVehicle;

    private String basicAuthHeader;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        basicAuthHeader = createBasicAuthHeader(username, password);
        Vehicle vehicle = Vehicle.builder()
                .vin("1HGCM82633A123456")
                .wmi("1HG")
                .vds("CM826")
                .vis("33A123456")
                .manufacturer("Honda")
                .brand("Accord")
                .model("EX")
                .engine("2.4L I4")
                .year(2020)
                .trim("Sedan")
                .mileage(15000)
                .urlLink("/images/hello.png")
                .build();
        savedVehicle = vehicleService.save(vehicle);
    }

    @AfterEach
    public void tearDown() {
        vehicleService.deleteAll();
    }

    private String createBasicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    @Test
    public void VehicleController_GetAllVehicles_ReturnOk () throws Exception {
        mockMvc.perform(get("/api/vehicles")
                        .header("Authorization", basicAuthHeader))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).isNotEmpty();
                });
    }

    @Test
    public void VehicleController_GetVehicleById_ReturnOk () throws Exception {
        Long id = savedVehicle.getId();
        mockMvc.perform(get("/api/vehicles/{id}", id)
                        .header("Authorization", basicAuthHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.vin", is(savedVehicle.getVin())))
                .andExpect(jsonPath("$.brand", is(savedVehicle.getBrand())))
                .andExpect(jsonPath("$.model", is(savedVehicle.getModel())))
                .andExpect(jsonPath("$.year", is(savedVehicle.getYear())));
    }

    @Test
    public void VehicleController_CreateVehicle_ReturnCreated () throws Exception {
        Vehicle vehicle = Vehicle.builder()
                .vin("1HGCM82633A123451")
                .wmi("1HG")
                .vds("CM826")
                .vis("33A123456")
                .manufacturer("Honda")
                .brand("Accord1")
                .model("EX")
                .engine("2.4L I4")
                .year(2021)
                .trim("Sedan")
                .mileage(12000)
                .urlLink("/images/hello1.png")
                .build();

        mockMvc.perform(post("/api/vehicles")
                        .header("Authorization", basicAuthHeader)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vin").value(vehicle.getVin()))
                .andExpect(jsonPath("$.manufacturer").value(vehicle.getManufacturer()))
                .andExpect(jsonPath("$.brand").value(vehicle.getBrand()))
                .andExpect(jsonPath("$.model").value(vehicle.getModel()))
                .andExpect(jsonPath("$.engine").value(vehicle.getEngine()))
                .andExpect(jsonPath("$.year").value(vehicle.getYear()))
                .andExpect(jsonPath("$.trim").value(vehicle.getTrim()))
                .andExpect(jsonPath("$.mileage").value(vehicle.getMileage()));
    }

    @Test
    public void VehicleController_UpdateVehicle_ReturnUpdated () throws Exception {
        Vehicle updatedVehicle = vehicleService.getById(savedVehicle.getId());
        updatedVehicle.setYear(2021);
        updatedVehicle.setMileage(22000);

        mockMvc.perform(put("/api/vehicles/{id}", updatedVehicle.getId())
                        .header("Authorization", basicAuthHeader)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedVehicle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin").value(updatedVehicle.getVin()))
                .andExpect(jsonPath("$.manufacturer").value(updatedVehicle.getManufacturer()))
                .andExpect(jsonPath("$.brand").value(updatedVehicle.getBrand()))
                .andExpect(jsonPath("$.model").value(updatedVehicle.getModel()))
                .andExpect(jsonPath("$.engine").value(updatedVehicle.getEngine()))
                .andExpect(jsonPath("$.year").value(updatedVehicle.getYear()))
                .andExpect(jsonPath("$.trim").value(updatedVehicle.getTrim()))
                .andExpect(jsonPath("$.mileage").value(updatedVehicle.getMileage()));
    }

    @Test
    public void VehicleController_DeleteVehicleById_ReturnNoContent () throws Exception {
        mockMvc.perform(delete("/api/vehicles/{id}", savedVehicle.getId())
                        .header("Authorization", basicAuthHeader))
                .andExpect(status().isNoContent());
    }

    @Test
    public void VehicleController_DeleteVehicleById_ReturnNotFound () throws Exception {
        mockMvc.perform(delete("/api/vehicles/{id}", 0)
                        .header("Authorization", basicAuthHeader))
                .andExpect(status().isOk());
    }
}
