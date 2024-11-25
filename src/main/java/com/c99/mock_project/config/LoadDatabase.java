package com.c99.mock_project.config;

import com.c99.mock_project.model.Car;
import com.c99.mock_project.model.Report;
import com.c99.mock_project.repository.CarRepository;
import com.c99.mock_project.repository.ReportRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Optional;

@Configuration
public class LoadDatabase {
//    @Bean
//    CommandLineRunner initDatabase(CarRepository carRepository, ReportRepository reportRepository) {
//        return args -> {
//            Optional<Car> existingCar1 = carRepository.findByVin("1HGCM82633A123456");
//            Optional<Car> existingCar2 = carRepository.findByVin("1HGCM82633A654321");
//
//            Car car1 = existingCar1.orElseGet(() -> Car.builder()
//                    .vin("1HGCM82633A123456")
//                    .wmi("1HG")
//                    .vds("CM826")
//                    .vis("33A123456")
//                    .manufacturer("Honda")
//                    .brand("Accord")
//                    .model("EX")
//                    .engine("2.4L I4")
//                    .year(2020)
//                    .trim("Sedan")
//                    .mileage(15000)
//                    .build());
//
//            Car car2 = existingCar2.orElseGet(() -> Car.builder()
//                    .vin("1HGCM82633A654321")
//                    .wmi("1HG")
//                    .vds("CM826")
//                    .vis("33A654321")
//                    .manufacturer("Honda")
//                    .brand("Civic")
//                    .model("LX")
//                    .engine("1.8L I4")
//                    .year(2019)
//                    .trim("Coupe")
//                    .mileage(20000)
//                    .build());
//
//            carRepository.save(car1);
//            carRepository.save(car2);
//
//            Optional<Report> existingReport1 = reportRepository.findByVin("1HGCM82633A123456");
//            Optional<Report> existingReport2 = reportRepository.findByVin("1HGCM82633A654321");
//
//            Report report1 = existingReport1.orElseGet(() -> Report.builder()
//                    .car(car1)
//                    .summary("Annual maintenance")
//                    .vin("1HGCM82633A123456")
//                    .vehicleSpecifications("2020 Honda Accord EX")
//                    .reportedIncidents(false)
//                    .problemChecklist(false)
//                    .date(LocalDate.now())
//                    .build());
//
//            Report report2 = existingReport2.orElseGet(() -> Report.builder()
//                    .car(car2)
//                    .summary("Engine check")
//                    .vin("1HGCM82633A654321")
//                    .vehicleSpecifications("2019 Honda Civic LX")
//                    .reportedIncidents(true)
//                    .problemChecklist(true)
//                    .date(LocalDate.now())
//                    .build());
//
//            reportRepository.save(report1);
//            reportRepository.save(report2);
//        };
//    }
}
