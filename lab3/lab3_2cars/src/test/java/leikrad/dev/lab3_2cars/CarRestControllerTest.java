package leikrad.dev.lab3_2cars;

import leikrad.dev.lab3_2cars.data.Car;
import leikrad.dev.lab3_2cars.service.CarManagerService;

// Junit
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

// Mockito
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

@WebMvcTest(CarRestController.class)
public class CarRestControllerTest {
    // Create a test to verify the Car [Rest]Controller (and mock the CarService
    // bean), as “resource efficient” as possible.

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService carManagerService;

    @Test
    public void testCreateCar() {
        Car car = new Car("Toyota", "Corolla");

        when(carManagerService.save(car)).thenReturn(car);

        mvc.perform(
                post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Toyota")));

        verify(carManagerService, times(1)).save(Mockito.any());
    }

    @Test
    public void getAllCars() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");

        List<Car> cars = List.of(car1, car2);

        when(carManagerService.getAllCars()).thenReturn(cars);

        mvc.perform(
            get("/api/cars")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
        ).andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].maker", is("Toyota")))
        .andExpect(jsonPath("$[1].maker", is("Honda")));

        verify(carManagerService, times(1)).getAllCars();
    }

    @Test
    public void getCarById() {
        Car car = new Car("Toyota", "Corolla");

        when(carManagerService.getCarById(1)).thenReturn(car);

        mvc.perform(
            get("/api/cars/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
        ).andExpect(jsonPath("$.maker", is("Toyota")))
        .andExpect(jsonPath("$.model", is("Corolla")));

        verify(carManagerService, times(1)).getCarById(1);
    }
}
