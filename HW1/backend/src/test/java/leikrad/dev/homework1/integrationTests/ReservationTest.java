package leikrad.dev.homework1.integrationTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import leikrad.dev.homework1.data.trip.Trip;
import leikrad.dev.homework1.data.trip.TripRepository;
import leikrad.dev.homework1.data.city.City;
import leikrad.dev.homework1.data.city.CityRepository;
import leikrad.dev.homework1.data.reservation.Reservation;
import leikrad.dev.homework1.data.reservation.ReservationRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "IT-config.properties")
class ReservationTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
    }

    @Test
    @DisplayName("Create Reservation")
    void testCreateReservation() {
        
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Reservation reservation = new Reservation(trip, "John Doe", "123456789");

        ResponseEntity<Reservation> response = restTemplate.postForEntity("/api/reservation", reservation,
                Reservation.class);

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(reservations).hasSize(1);
        assertThat(reservations).hasSize(1).extracting(Reservation::getPersonName).containsOnly("John Doe");
        assertThat(reservations).hasSize(1).extracting(Reservation::getPhoneNumber).containsOnly("123456789");
        assertThat(reservations).hasSize(1).extracting(Reservation::getUuid).isNotNull();
    }

    @Test
    @DisplayName("Create reservation with invalid id")
    void testCreateReservationWithInvalidId() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Reservation reservation = new Reservation(trip, "John Doe", "123456789");

        reservation.setReservationId(1L);

        ResponseEntity<Reservation> response = restTemplate.postForEntity("/api/reservation", reservation,
                Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Get all reservations")
    void testGetAllReservations() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Reservation reservation1 = Utils.createTestReservation(reservationRepository, trip, "John Doe", "123456789");
        Reservation reservation2 = Utils.createTestReservation(reservationRepository, trip, "Jane Doe", "987654321");
        Reservation reservation3 = Utils.createTestReservation(reservationRepository, trip, "John Smith", "123456789");

        ResponseEntity<List<Reservation>> response = restTemplate.exchange("/api/reservation", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Reservation>>() {
                });

        List<Reservation> reservations = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservations).hasSize(3).extracting(Reservation::getPersonName).containsOnly(reservation1.getPersonName(), reservation2.getPersonName(), reservation3.getPersonName());
        assertThat(reservations).hasSize(3).extracting(Reservation::getPhoneNumber).containsOnly(reservation1.getPhoneNumber(), reservation2.getPhoneNumber(), reservation3.getPhoneNumber());
        assertThat(reservations).hasSize(3).extracting(Reservation::getReservationId).containsOnly(reservation1.getReservationId(), reservation2.getReservationId(), reservation3.getReservationId());
        assertThat(reservations).hasSize(3).extracting(Reservation::getUuid).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Get reservation by id")
    void testGetReservationById() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Reservation reservation = Utils.createTestReservation(reservationRepository, trip, "John Doe", "123456789");

        ResponseEntity<Reservation> response = restTemplate.getForEntity("/api/reservation/" + reservation.getReservationId(), Reservation.class);

        Reservation found = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(found).isNotNull();
        assertThat(found.getPersonName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Get reservation by id not found")
    void testGetReservationByIdNotFound() {
        ResponseEntity<Reservation> response = restTemplate.getForEntity("/api/reservation/1", Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete reservation")
    void testDeleteReservation() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Reservation reservation = Utils.createTestReservation(reservationRepository, trip, "John Doe", "123456789");

        restTemplate.delete("/api/reservation/" + reservation.getReservationId());

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).isEmpty();
    }

    @Test
    @DisplayName("Delete reservation not found")
    void testDeleteReservationNotFound() {
        ResponseEntity<Reservation> response = restTemplate.exchange("/api/reservation/-1", HttpMethod.DELETE, null, Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Update reservation")
    void testUpdateReservation() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Reservation reservation = Utils.createTestReservation(reservationRepository, trip, "John Doe", "123456789");

        reservation.setPersonName("Jane Doe");

        restTemplate.put("/api/reservation/" + reservation.getReservationId(), reservation);

        Reservation updated = reservationRepository.findById(reservation.getReservationId()).get();

        assertThat(updated.getPersonName()).isEqualTo("Jane Doe");
    }

    @Test
    @DisplayName("Update reservation not found")
    void testUpdateReservationNotFound() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Reservation reservation = new Reservation(trip, "John Doe", "123456789");

        reservation.setReservationId(-1L);

        ResponseEntity<Reservation> response = restTemplate.exchange("/api/reservation/-1", HttpMethod.PUT, new HttpEntity<>(reservation), Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Get reservation by uuid")
    void testGetReservationByUuid() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Reservation reservation = Utils.createTestReservation(reservationRepository, trip, "John Doe", "123456789");

        ResponseEntity<Reservation> response = restTemplate.getForEntity("/api/reservation/uuid/" + reservation.getUuid(), Reservation.class);

        Reservation found = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(found).isNotNull();
        assertThat(found.getPersonName()).isEqualTo("John Doe");
        assertThat(found.getUuid()).isEqualTo(reservation.getUuid());
    }

    @Test
    @DisplayName("Get reservation by uuid not found")
    void testGetReservationByUuidNotFound() {
        ResponseEntity<Reservation> response = restTemplate.getForEntity("/api/reservation/uuid/1", Reservation.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}