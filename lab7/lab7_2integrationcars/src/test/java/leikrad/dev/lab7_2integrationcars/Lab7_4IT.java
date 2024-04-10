package leikrad.dev.lab7_2integrationcars;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Container;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


import leikrad.dev.lab7_2integrationcars.data.Car;
import leikrad.dev.lab7_2integrationcars.data.CarRepository;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Lab7_4IT {

    @LocalServerPort private int port;

    @Autowired private CarRepository carRepository;

    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("test")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQL8Dialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create"); // Add this line
    }
        
    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }
    
    @Test
    void whenValidInput_thenCreateCar() {
        Car car = new Car("Toyota", "Corolla");
        
        given().
            port(port).
            contentType("application/json").
            body(car).
        when().
            post("/api/cars").
        then().
            statusCode(201);

        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getMaker).containsOnly("Toyota");
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        createTestCar("Toyota", "Corolla");
        createTestCar("Honda", "Civic");
        
        given().
            port(port).
        when().
            get("/api/cars").
        then().
            statusCode(200).
            body("maker", hasItems("Toyota", "Honda"))
            .body("model", hasItems("Corolla", "Civic"));
    }

    private void createTestCar(String maker, String model) {
        Car car = new Car(maker, model);
        carRepository.saveAndFlush(car);
    }
}
