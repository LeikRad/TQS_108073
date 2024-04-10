package leikrad.dev.lab7_2integrationcars.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import leikrad.dev.lab7_2integrationcars.data.Car;
import leikrad.dev.lab7_2integrationcars.data.CarRepository;

@Service
public class CarManagerService {

    private final CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> getCarDetails(Long id) {
        return carRepository.findByCarId(id);
    }
}