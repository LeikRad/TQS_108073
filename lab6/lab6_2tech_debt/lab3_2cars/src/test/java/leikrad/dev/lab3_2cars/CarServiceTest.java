package leikrad.dev.lab3_2cars;

import java.util.List;
// Junit
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// Mockito
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import leikrad.dev.lab3_2cars.data.Car;
import leikrad.dev.lab3_2cars.data.CarRepository;
import leikrad.dev.lab3_2cars.service.CarManagerService;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {
        Car car = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        Car car3 = new Car("Toyota", "Camry");

        car.setCarId(1L);
        car2.setCarId(2L);
        car3.setCarId(3L);
        List<Car> allCars = List.of(car, car2, car3);

        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findByCarId(car.getCarId())).thenReturn(Optional.of(car));
        Mockito.when(carRepository.findByCarId(car2.getCarId())).thenReturn(Optional.of(car2));
        Mockito.when(carRepository.findByCarId(car3.getCarId())).thenReturn(Optional.of(car3));
        Mockito.when(carRepository.findByCarId(-99L)).thenReturn(Optional.empty());
    }

    @Test
    public void testGetAllCars() {
        Car car = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        Car car3 = new Car("Toyota", "Camry");

        car.setCarId(1L);
        car2.setCarId(2L);
        car3.setCarId(3L);

        List<Car> allCars = carManagerService.getAllCars();

        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).contains(car.getMaker(), car2.getMaker(),
                car3.getMaker());

    }

    @Test
    public void testGetCarDetails() {
        Car car = new Car("Toyota", "Corolla");
        car.setCarId(1L);

        Car found = carManagerService.getCarDetails(1L).orElse(null);

        verifyFindByIdIsCalledOnce();
        assertThat(found.getMaker()).isEqualTo(car.getMaker());
    }

    @Test
    public void testGetCarDetails_withWrongID() {
        Car car = new Car("Toyota", "Corolla");
        car.setCarId(1L);

        Car found = carManagerService.getCarDetails(-99L).orElse(null);

        verifyFindByIdIsCalledOnce();
        assertThat(found).isNull();
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(Mockito.anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
