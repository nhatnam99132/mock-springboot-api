package com.c99.mock_project.controller;

import com.c99.mock_project.model.Car;
import com.c99.mock_project.repository.CarRepository;
import com.c99.mock_project.service.CarService;
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
public class CarServiceTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private CarService carService;


    @Test
    public void testGetAllCars()
    {
        List<Car> cars = new ArrayList<>();
        Car car = new Car();
        cars.add(car);

        when(carRepository.findAll()).thenReturn(cars);
        List<Car> result = carService.getAllCars();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(carRepository).findAll();
    }

    @Test
    public void testFindCar()
    {
        Car car = new Car();

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        Car result = carService.getCarById(1L);

        assertNotNull(result);
        verify(carRepository).findById(1L);
    }

    @Test
    public void testCreateCar()
    {
        Car car = new Car();
        when(carRepository.save(car)).thenReturn(car);
        Car result = carService.saveCar(car);
        assertNotNull(result);
        verify(carRepository).save(car);
    }

    @Test
    public void testUpdateCar()
    {
        Car car = new Car();
        car.setId(1L);

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);

        Car result = carService.updateCar(car.getId(), car);
        assertNotNull(result);
        verify(carRepository).save(car);
    }

    @Test
    public void testDeleteCar() {
        Car car = new Car();
        car.setId(1L);

        when(carRepository.save(car)).thenReturn(car);
        doNothing().when(carRepository).deleteById(car.getId());
        carService.deleteCar(car.getId());
        verify(carRepository).deleteById(car.getId());
    }

    @Test
    public void testDeleteAllCars() {
        doNothing().when(carRepository).deleteAll();
        carService.deleteAllCars();
        verify(carRepository).deleteAll();
    }

}
