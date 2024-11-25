package com.c99.mock_project.service;

import com.c99.mock_project.model.Car;
import com.c99.mock_project.model.ProblemChecklist;
import com.c99.mock_project.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public void deleteAllCars() {
        carRepository.deleteAll();
    }

    public Car updateCar(Long id, Car carDetails) {
        return carRepository.findById(id).map(car -> {
            car.setVin(carDetails.getVin());
            car.setWmi(carDetails.getWmi());
            car.setVds(carDetails.getVds());
            car.setVis(carDetails.getVis());
            car.setManufacturer(carDetails.getManufacturer());
            car.setBrand(carDetails.getBrand());
            car.setModel(carDetails.getModel());
            car.setEngine(carDetails.getEngine());
            car.setYear(carDetails.getYear());
            car.setTrim(carDetails.getTrim());
            car.setMileage(carDetails.getMileage());
            return carRepository.save(car);
        }).orElse(null);
    }

}
