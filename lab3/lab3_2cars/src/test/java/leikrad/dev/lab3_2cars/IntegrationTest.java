package leikrad.dev.lab3_2cars;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import leikrad.dev.lab3_2cars.data.Car;
import leikrad.dev.lab3_2cars.data.CarRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car car = new Car("Toyota", "Corolla");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", car, Car.class);

        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getMaker).containsOnly("Toyota");
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        createTestCar("Toyota", "Corolla");
        createTestCar("Honda", "Civic");

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Car>>() {
                        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).contains("Toyota", "Honda");
        assertThat(response.getBody()).extracting(Car::getModel).contains("Corolla", "Civic");
    }

    private void createTestCar(String maker, String model) {
        Car car = new Car(maker, model);
        carRepository.saveAndFlush(car);
    }
}
