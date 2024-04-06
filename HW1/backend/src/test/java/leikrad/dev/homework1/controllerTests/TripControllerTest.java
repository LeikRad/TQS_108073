package leikrad.dev.homework1.controllerTests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.DisplayName;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.MediaType;

// Static imports
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import leikrad.dev.homework1.boundary.TripController;
import leikrad.dev.homework1.service.TripManagerService;
import leikrad.dev.homework1.data.trip.*;
import leikrad.dev.homework1.data.city.*;

@WebMvcTest(TripController.class)
class TripControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TripManagerService tripManagerService;
    
    @Test
    @DisplayName("Create Trip")
    void testCreateTrip() throws Exception{
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        when(tripManagerService.createTrip(Mockito.any())).thenReturn(trip);

        mvc.perform(post("/api/trip")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(trip)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.originCity.cityName", is("Lisbon")))
            .andExpect(jsonPath("$.destinationCity.cityName", is("Madrid")));

        verify(tripManagerService, times(1)).createTrip(Mockito.any());
    }

    @Test
    @DisplayName("Create Trip with invalid data")
    void testCreateTripWithInvalidData() throws Exception{
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);
        trip.setTripId(1L);
        
        when(tripManagerService.createTrip(Mockito.any())).thenThrow(new IllegalArgumentException("Trip id must be null"));

        mvc.perform(post("/api/trip")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(trip)))
            .andExpect(status().isBadRequest());

        verify(tripManagerService, times(1)).createTrip(Mockito.any());
    }

    @Test
    @DisplayName("Get all trips")
    void testGetAllTrips() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip1 = new Trip(origin, destination, departureDate, arrivalDate, 200.0);
        Trip trip2 = new Trip(origin, destination, departureDate, arrivalDate, 200.0);
        Trip trip3 = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        List<Trip> allTrips = List.of(trip1, trip2, trip3);

        when(tripManagerService.getAllTrips()).thenReturn(allTrips);

        mvc.perform(get("/api/trip")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));

        verify(tripManagerService, times(1)).getAllTrips();
    }

    @Test
    @DisplayName("Get trip by id")
    void testGetTripById() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);
        trip.setTripId(1L);

        when(tripManagerService.getTripDetails(Mockito.any())).thenReturn(Optional.of(trip));

        mvc.perform(get("/api/trip/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.originCity.cityName", is("Lisbon")))
            .andExpect(jsonPath("$.destinationCity.cityName", is("Madrid")));

        verify(tripManagerService, times(1)).getTripDetails(Mockito.any());
    }

    @Test
    @DisplayName("Get trip by id not found")
    void testGetTripByIdNotFound() throws Exception {
        when(tripManagerService.getTripDetails(Mockito.any())).thenReturn(Optional.empty());

        mvc.perform(get("/api/trip/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(tripManagerService, times(1)).getTripDetails(Mockito.any());
    }

    @Test
    @DisplayName("Delete trip")
    void testDeleteTrip() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);
        trip.setTripId(1L);

        doNothing().when(tripManagerService).deleteTrip(Mockito.any());

        mvc.perform(delete("/api/trip/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(tripManagerService, times(1)).deleteTrip(Mockito.any());
    }

    @Test
    @DisplayName("Delete trip not found")
    void testDeleteTripNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Trip not found")).when(tripManagerService).deleteTrip(Mockito.any());

        mvc.perform(delete("/api/trip/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(tripManagerService, times(1)).deleteTrip(Mockito.any());
    }

    @Test
    @DisplayName("Update trip")
    void testUpdateTrip() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);
        trip.setTripId(1L);

        when(tripManagerService.updateTrip(Mockito.any())).thenReturn(trip);

        mvc.perform(put("/api/trip/{id}", trip.getTripId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(trip)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.originCity.cityName", is("Lisbon")))
            .andExpect(jsonPath("$.destinationCity.cityName", is("Madrid")));

        verify(tripManagerService, times(1)).updateTrip(Mockito.any());
    }

    @Test
    @DisplayName("Update trip not found")
    void testUpdateTripNotFound() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);
        trip.setTripId(-1L);

        when(tripManagerService.updateTrip(Mockito.any())).thenThrow(new EntityNotFoundException("Trip not found"));

        mvc.perform(put("/api/trip/{id}", trip.getTripId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(trip)))
            .andExpect(status().isNotFound());

        verify(tripManagerService, times(1)).updateTrip(Mockito.any());
    }

    @Test
    @DisplayName("Get trips by origin and destination city")
    void testGetTripsByOriginAndDestinationCity() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip1 = new Trip(origin, destination, departureDate, arrivalDate, 200.0);
        Trip trip2 = new Trip(origin, destination, departureDate, arrivalDate, 200.0);
        Trip trip3 = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        List<Trip> allTrips = List.of(trip1, trip2, trip3);

        when(tripManagerService.getTripsByOriginAndDestinationCity(Mockito.any(), Mockito.any())).thenReturn(allTrips);

        mvc.perform(get("/api/trip?originCity=Lisbon&destinationCity=Madrid")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));

        verify(tripManagerService, times(1)).getTripsByOriginAndDestinationCity(Mockito.any(), Mockito.any());
    }
}
