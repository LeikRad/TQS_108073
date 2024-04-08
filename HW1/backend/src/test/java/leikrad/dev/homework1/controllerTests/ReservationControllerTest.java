package leikrad.dev.homework1.controllerTests;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.util.UUID;

import leikrad.dev.homework1.boundary.ReservationController;
import leikrad.dev.homework1.service.CurrencyManagerService;
import leikrad.dev.homework1.service.ReservationManagerService;
import leikrad.dev.homework1.data.trip.*;
import leikrad.dev.homework1.data.city.*;
import leikrad.dev.homework1.data.currency.Currency;
import leikrad.dev.homework1.data.reservation.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationManagerService reservationManagerService;
    
    @MockBean
    private CurrencyManagerService currencyManagerService;

    @MockBean
    private Reservation reservation;

    @Test
    @DisplayName("Create Reservation")
    void testCreateReservation() throws Exception{
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        String uuid = UUID.randomUUID().toString();
        
        Reservation reservation = new Reservation(trip, "John Doe", "123456789", 300.0, "EUR");
        Reservation createdReservation = new Reservation(trip, "John Doe", "123456789", 300.0, "EUR");
        createdReservation.setReservationId(1L);
        createdReservation.setUuid(uuid);
        
        when(reservationManagerService.createReservation(Mockito.any())).thenReturn(createdReservation);
        when(currencyManagerService.getCurrencyById(Mockito.anyString())).thenReturn(Optional.of(new Currency("EUR", 1.0)));
        
        mvc.perform(post("/api/reservation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.personName", is("John Doe")))
            .andExpect(jsonPath("$.uuid", is(uuid)));

        verify(reservationManagerService, times(1)).createReservation(Mockito.any());
    }

    @Test
    @DisplayName("Create Reservation with invalid data")
    void testCreateReservationWithInvalidData() throws Exception{
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        Reservation reservation = new Reservation(trip, "John Doe", "123456789", 300.0, "EUR");
        reservation.setReservationId(1L);

        when(reservationManagerService.createReservation(Mockito.any())).thenThrow(new IllegalArgumentException("Reservation id must be null"));
        when(currencyManagerService.getCurrencyById(Mockito.anyString())).thenReturn(Optional.of(new Currency("EUR", 1.0)));

        mvc.perform(post("/api/reservation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)))
            .andExpect(status().isBadRequest());
        
        verify(reservationManagerService, times(1)).createReservation(Mockito.any());
    }

    @Test
    @DisplayName("Get all reservations")
    void testGetAllReservations() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        Reservation reservation1 = new Reservation(trip, "John Doe", "123456789", 300.0, "EUR");
        Reservation reservation2 = new Reservation(trip, "Jane Doe", "987654321", 300.0, "EUR");
        Reservation reservation3 = new Reservation(trip, "John Doe", "123456789", 300.0, "EUR");

        List<Reservation> allReservations = List.of(reservation1, reservation2, reservation3);

        when(reservationManagerService.getAllReservations()).thenReturn(allReservations);

        mvc.perform(get("/api/reservation")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].personName", is(reservation1.getPersonName())))
            .andExpect(jsonPath("$[1].personName", is(reservation2.getPersonName())))
            .andExpect(jsonPath("$[2].personName", is(reservation3.getPersonName())));

        verify(reservationManagerService, times(1)).getAllReservations();
    }

    @Test
    @DisplayName("Get reservation by id")
    void testGetReservationById() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        Reservation reservation = new Reservation(trip, "John Doe", "123456789", 300.0, "EUR");
        reservation.setReservationId(1L);

        when(reservationManagerService.getReservationDetails(1L)).thenReturn(Optional.of(reservation));

        mvc.perform(get("/api/reservation/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.personName", is(reservation.getPersonName())));

        verify(reservationManagerService, times(1)).getReservationDetails(1L);
    }

    @Test
    @DisplayName("Get reservation by id with invalid id")
    void testGetReservationByIdWithInvalidId() throws Exception {
        when(reservationManagerService.getReservationDetails(-1L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/reservation/-1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(reservationManagerService, times(1)).getReservationDetails(-1L);
    }

    @Test
    @DisplayName("Delete reservation")
    void testDeleteReservation() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        Reservation reservation = new Reservation(trip, "John Doe", "123456789", 300.0, "EUR");
        reservation.setReservationId(1L);

        
        doNothing().when(reservationManagerService).deleteReservation(1L);

        mvc.perform(delete("/api/reservation/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
        
        verify(reservationManagerService, times(1)).deleteReservation(1L);
    }

    @Test
    @DisplayName("Delete reservation with invalid id")
    void testDeleteReservationWithInvalidId() throws Exception {
        
        doThrow(new EntityNotFoundException("Reservation not found")).when(reservationManagerService).deleteReservation(-1L);

        mvc.perform(delete("/api/reservation/-1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        
        verify(reservationManagerService, times(1)).deleteReservation(-1L);
    }

    @Test
    @DisplayName("Update reservation")
    void testUpdateReservation() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        Reservation reservation = new Reservation(trip, "John Doe", "123456789", 300.0, "EUR");
        reservation.setReservationId(1L);

        when(reservationManagerService.updateReservation(Mockito.any())).thenReturn(reservation);

        mvc.perform(put("/api/reservation/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.personName", is(reservation.getPersonName())));

        verify(reservationManagerService, times(1)).updateReservation(Mockito.any());
    }

    @Test
    @DisplayName("Update reservation with invalid id")
    void testUpdateReservationWithInvalidId() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        Reservation reservation = new Reservation(trip, "John Doe", "123456789", 300.0, "EUR");
        reservation.setReservationId(-1L);

        when(reservationManagerService.updateReservation(Mockito.any())).thenThrow(new EntityNotFoundException("Reservation not found"));

        mvc.perform(put("/api/reservation/-1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(reservation)))
            .andExpect(status().isNotFound());

        verify(reservationManagerService, times(1)).updateReservation(Mockito.any());
    }

    @Test
    @DisplayName("Get reservation by uuid")
    void testGetReservationByUuid() throws Exception {
        City origin = new City("Lisbon");
        City destination = new City("Madrid");

        LocalDateTime departureDate = LocalDateTime.now();
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(1);
        
        Trip trip = new Trip(origin, destination, departureDate, arrivalDate, 200.0);

        String uuid = UUID.randomUUID().toString();
        
        Reservation reservation = new Reservation(trip, "John Doe", "123456789", 300.0, "EUR");
        reservation.setReservationId(1L);

        when(reservationManagerService.getReservationByUuid(uuid)).thenReturn(Optional.of(reservation));

        mvc.perform(get("/api/reservation/uuid/" + uuid)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.personName", is(reservation.getPersonName())));

        verify(reservationManagerService, times(1)).getReservationByUuid(uuid);
    }

    @Test
    @DisplayName("Get reservation by uuid with invalid uuid")
    void testGetReservationByUuidWithInvalidUuid() throws Exception {
        String uuid = UUID.randomUUID().toString();
        
        when(reservationManagerService.getReservationByUuid(uuid)).thenReturn(Optional.empty());

        mvc.perform(get("/api/reservation/uuid/" + uuid)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(reservationManagerService, times(1)).getReservationByUuid(uuid);
    }


}
