package leikrad.dev.lab3_2cars;

import leikrad.dev.lab3_2cars.boundary.CarController;
import leikrad.dev.lab3_2cars.data.Car;
import leikrad.dev.lab3_2cars.service.CarManagerService;

// Junit
import org.junit.jupiter.api.Test;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

// Mockito
import org.mockito.Mockito;

// Static imports
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import java.util.Optional;

@WebMvcTest(CarController.class)
class CarControllerTest {
    // Create a test to verify the Car [Rest]Controller (and mock the CarService
    // bean), as “resource efficient” as possible.

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService carManagerService;

    @Test
    void testCreateCar() throws Exception {
        Car car = new Car("Toyota", "Corolla");

        when(carManagerService.save(Mockito.any())).thenReturn(car);

        mvc.perform(
                post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Toyota")));

        verify(carManagerService, times(1)).save(Mockito.any());
    }

    @Test
    void getAllCars() throws Exception {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");

        List<Car> cars = List.of(car1, car2);

        when(carManagerService.getAllCars()).thenReturn(cars);

        mvc.perform(
                get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].maker", is("Toyota")))
                .andExpect(jsonPath("$[1].maker", is("Honda")));

        verify(carManagerService, times(1)).getAllCars();
    }

    @Test
    void getCarById() throws Exception {
        Car car = new Car("Toyota", "Corolla");

        when(carManagerService.getCarDetails(1L)).thenReturn(Optional.of(car));

        mvc.perform(
                get("/api/cars/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Corolla")));

        verify(carManagerService, times(1)).getCarDetails(1L);
    }
}
