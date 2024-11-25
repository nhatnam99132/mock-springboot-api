package com.c99.mock_project.controller;

import com.c99.mock_project.model.Car;
import com.c99.mock_project.service.CarService;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.is;

import java.util.Base64;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CarAPITests {
    @Autowired
    MockMvc mockMvc;


    @Autowired
    private CarService carService;

    private Car savedCar;

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
        System.out.println(username);
        Car car = Car.builder()
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
        savedCar = carService.saveCar(car);
        System.out.println("Saved Car ID: " + savedCar.getVin());
    }

    @AfterEach
    public void tearDown() {
        carService.deleteAllCars();
    }

    private String createBasicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }


    @Test
    public void CarController_GetAllCars_ReturnOk () throws Exception {
        mockMvc.perform(get("/api/cars")
                    .header("Authorization", basicAuthHeader))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).isNotEmpty();
                });
    }

    @Test
    public void CarController_GetAllCars_ReturnEmptyListOk () throws Exception {
        Car checkedCar = carService.getCarById(savedCar.getId());
        if (checkedCar != null) {
            carService.deleteCar(checkedCar.getId());
        }

        mockMvc.perform(get("/api/cars")
                        .header("Authorization", basicAuthHeader))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).isEqualTo("[]");
                });
    }

    @Test
    public void CarController_GetCarById_ReturnOk () throws Exception {
        Long id = savedCar.getId();
        mockMvc.perform(get("/api/cars/{id}", id)
                        .header("Authorization", basicAuthHeader))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    System.out.println("Response JSON: " + json);
                })
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.vin", is(savedCar.getVin())))
                .andExpect(jsonPath("$.brand", is(savedCar.getBrand())))
                .andExpect(jsonPath("$.model", is(savedCar.getModel())))
                .andExpect(jsonPath("$.year", is(savedCar.getYear())));
    }

    @Test
    public void CarController_GetCarById_ReturnNotFound () throws Exception {
        mockMvc.perform(get("/api/cars/{id}", 0)
                        .header("Authorization", basicAuthHeader))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    System.out.println("Response JSON: " + json);
                })
                .andExpect(status().isNotFound());
    }

    @Test
    public void CarController_CreateCar_ReturnCreated () throws Exception {
        Car car = Car.builder()
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

        //        // Perform the POST request
        mockMvc.perform(post("/api/cars")
                        .header("Authorization", basicAuthHeader)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vin").value(car.getVin()))
                .andExpect(jsonPath("$.manufacturer").value(car.getManufacturer()))
                .andExpect(jsonPath("$.brand").value(car.getBrand()))
                .andExpect(jsonPath("$.model").value(car.getModel()))
                .andExpect(jsonPath("$.engine").value(car.getEngine()))
                .andExpect(jsonPath("$.year").value(car.getYear()))
                .andExpect(jsonPath("$.trim").value(car.getTrim()))
                .andExpect(jsonPath("$.mileage").value(car.getMileage()));
    }

    @Test
    public void CarController_UpdateCar_ReturnUpdated () throws Exception {
        Car updatedCar = carService.getCarById(savedCar.getId());
//        updatedCar.setVin("1HGCM82633A123456");
//        updatedCar.setWmi("1HG");
//        updatedCar.setVds("CM826");
//        updatedCar.setVis("33A123456");
//        updatedCar.setManufacturer("Honda");
//        updatedCar.setBrand("Accord");
//        updatedCar.setModel("EX");
//        updatedCar.setEngine("2.4L I4");
        updatedCar.setYear(2021);
//        updatedCar.setTrim("Sedan");
        updatedCar.setMileage(22000);

        //        // Perform the POST request
        mockMvc.perform(put("/api/cars/{id}", updatedCar.getId())
                        .header("Authorization", basicAuthHeader)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedCar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin").value(updatedCar.getVin()))
                .andExpect(jsonPath("$.manufacturer").value(updatedCar.getManufacturer()))
                .andExpect(jsonPath("$.brand").value(updatedCar.getBrand()))
                .andExpect(jsonPath("$.model").value(updatedCar.getModel()))
                .andExpect(jsonPath("$.engine").value(updatedCar.getEngine()))
                .andExpect(jsonPath("$.year").value(updatedCar.getYear()))
                .andExpect(jsonPath("$.trim").value(updatedCar.getTrim()))
                .andExpect(jsonPath("$.mileage").value(updatedCar.getMileage()));
    }

    @Test
    public void CarController_DeleteCarById_ReturnNoContent () throws Exception {
        mockMvc.perform(delete("/api/cars/{id}", savedCar.getId())
                        .header("Authorization", basicAuthHeader))
                .andExpect(status().isNoContent());
    }

    @Test
    public void CarController_DeleteCarById_ReturnNotFound () throws Exception {
        mockMvc.perform(delete("/api/cars/{id}", 0)
                        .header("Authorization", basicAuthHeader))
                .andExpect(status().isNotFound());
    }

}
