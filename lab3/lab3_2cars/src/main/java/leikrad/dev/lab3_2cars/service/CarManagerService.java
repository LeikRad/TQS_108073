package leikrad.dev.lab3_2cars.service;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import leikrad.dev.lab3_2cars.data.Car;
import leikrad.dev.lab3_2cars.data.CarRepository;

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