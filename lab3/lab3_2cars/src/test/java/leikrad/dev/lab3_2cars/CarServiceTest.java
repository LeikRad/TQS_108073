package leikrad.dev.lab3_2cars;

import org.hibernate.mapping.List;
// Junit
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// Mockito
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import leikrad.dev.lab3_2cars.data.Car;
import leikrad.dev.lab3_2cars.repository.CarRepository;
import leikrad.dev.lab3_2cars.service.CarManagerService;

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

        car.setCarID(1);
        car2.setCarID(2);
        car3.setCarID(3);
        List<Car> allCars = List.of(car, car2, car3);

        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findByCarID(car.getCarID())).thenReturn(car);
        Mockito.when(carRepository.findByCarID(car2.getCarID())).thenReturn(car2);
        Mockito.when(carRepository.findByCarID(car3.getCarID())).thenReturn(car3);
        Mockito.when(carRepository.findByCarID(-99L)).thenReturn(Optional.empty());
    }

    @Test
    public void testGetAllCars() {
        Car car = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        Car car3 = new Car("Toyota", "Camry");

        car.setCarID(1);
        car2.setCarID(2);
        car3.setCarID(3);

        List<Car> allCars = carManagerService.getAllCars();
        
        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).contains(car.getMaker(), car2.getMaker(), car3.getMaker()
    }

    @Test
    public void testGetCarDetails() {
        Car car = new Car("Toyota", "Corolla");
        car.setCarID(1);

        Car found = carManagerService.getCarDetails(1);

        verifyFindByIdIsCalledOnce();
        assertThat(found.getMaker()).isEqualTo(car.getMaker());
    }

    @Test
    public void testGetCarDetails_withWrongID() {
        Car car = new Car("Toyota", "Corolla");
        car.setCarID(1);

        Car found = carManagerService.getCarDetails(-99);

        verifyFindByIdIsCalledOnce();
        assertThat(found).isNull();
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarID(Mockito.anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
