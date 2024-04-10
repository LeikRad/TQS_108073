package leikrad.dev.lab7_2integrationcars;

import leikrad.dev.lab7_2integrationcars.boundary.CarController;
import leikrad.dev.lab7_2integrationcars.data.Car;
import leikrad.dev.lab7_2integrationcars.service.CarManagerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.mockito.Mock;
// Mockito
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@WebMvcTest(CarController.class)
class CarIntegrationTest {
    
    @Autowired private MockMvc mockMvc;
    
    @MockBean private CarManagerService carService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @DisplayName("GET /api/cars/1")
    void testGetCarDetails() {
        Car car = new Car("Toyota", "Corolla");
        car.setCarId(1L);
        
        Mockito.when(carService.getCarDetails(1L)).thenReturn(Optional.of(car));
        
        given()

        .when()
            .get("/api/cars/1")
        .then()
            .statusCode(200)
            .extract()
            .body()
            .as(Car.class)
            .equals(car);
        
        Mockito.verify(carService, Mockito.times(1)).getCarDetails(1L);
    }

    @Test
    @DisplayName("GET /api/cars")
    void whenGetCars_thenReturns200() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");

        List<Car> cars = List.of(car1, car2);

        Mockito.when(carService.getAllCars()).thenReturn(cars);

        RestAssuredMockMvc.given()

        .when()
            .get("/api/cars")
        .then()
            .statusCode(200)
            .extract() 
            .body()
            .jsonPath()
            .getList(".", Car.class)
            .containsAll(cars);

        Mockito.verify(carService, Mockito.times(1)).getAllCars();
    }

    @Test
    @DisplayName("POST /api/cars")
    void whenPostCar_thenReturns201() {
        Car car = new Car("Toyota", "Corolla");
        Mockito.when(carService.save(Mockito.any())).thenReturn(car);

        RestAssuredMockMvc.given()
            .contentType("application/json")
            .body(car)
        .when()
            .post("/api/cars")
        .then()
            .statusCode(201)
            .extract()
            .body()
            .as(Car.class)
            .equals(car);

        Mockito.verify(carService, Mockito.times(1)).save(Mockito.any());
    }
}
