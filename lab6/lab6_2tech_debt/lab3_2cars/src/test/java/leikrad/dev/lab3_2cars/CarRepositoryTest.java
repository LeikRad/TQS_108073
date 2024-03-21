package leikrad.dev.lab3_2cars;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import leikrad.dev.lab3_2cars.data.Car;
import leikrad.dev.lab3_2cars.data.CarRepository;

@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindById_thenReturnCar() {
        // given
        Car car = new Car("Toyota", "Corolla");
        entityManager.persistAndFlush(car);

        // when
        Car found = carRepository.findByCarId(car.getCarId()).orElse(null);

        // then
        assertThat(found).isEqualTo(car);
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        // when
        Car fromDb = carRepository.findByCarId(-99L).orElse(null);

        // then
        assertThat(fromDb).isNull();
    }

    @Test
    void whenFindAll_thenReturnAllCars() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        Car car3 = new Car("Toyota", "Camry");

        entityManager.persist(car1);
        entityManager.persist(car2);
        entityManager.persist(car3);
        entityManager.flush();

        // when
        List<Car> allCars = carRepository.findAll();

        // then
        assertThat(allCars).hasSize(3).contains(car1, car2, car3);
    }
}
