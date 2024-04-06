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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "IT-config.properties")
class TripTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CityRepository cityRepository;

    @AfterEach
    void tearDown() {
        tripRepository.deleteAll();
    }

    @Test
    @DisplayName("Create Trip")
    void testCreateTrip() {
        
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = new Trip(origin, destination, dDate, aDate, price);
        

        ResponseEntity<Trip> response = restTemplate.postForEntity("/api/trip", trip,
                Trip.class);

        List<Trip> trips = tripRepository.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(trips).hasSize(1).extracting(Trip::getOriginCity).extracting(City::getCityName).containsOnly("Lisbon");
        assertThat(trips).hasSize(1).extracting(Trip::getDestinationCity).extracting(City::getCityName).containsOnly("Porto");
        assertThat(trips).hasSize(1).extracting(Trip::getPrice).containsOnly(price);
    }

    @Test
    @DisplayName("Create trip with invalid id")
    void testCreateTripWithInvalidId() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = new Trip(origin, destination, dDate, aDate, price);
        trip.setTripId(1L);

        ResponseEntity<Trip> response = restTemplate.postForEntity("/api/trip", trip,
                Trip.class);
        
        List<Trip> trips = tripRepository.findAll();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(trips).isEmpty();
    }

    @Test
    @DisplayName("Get all trips")
    void testGetAllTrips() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);
        Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        ResponseEntity<List<Trip>> response = restTemplate.exchange("/api/trip", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Trip>>() {
                });

        List<Trip> trips = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(trips).hasSize(2).extracting(Trip::getOriginCity).extracting(City::getCityName).contains("Lisbon");
        assertThat(trips).hasSize(2).extracting(Trip::getDestinationCity).extracting(City::getCityName).contains("Porto");
        assertThat(trips).hasSize(2).extracting(Trip::getPrice).contains(price);
    }

    @Test
    @DisplayName("Get trip by id")
    void testGetTripById() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip createdTrip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Long id = createdTrip.getTripId();

        String url = "/api/trip/" + id;

        ResponseEntity<Trip> response = restTemplate.getForEntity(url, Trip.class);

        Trip found = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(found).isNotNull();
        assertThat(found.getOriginCity().getCityName()).isEqualTo("Lisbon");
        assertThat(found.getDestinationCity().getCityName()).isEqualTo("Porto");
        assertThat(found.getPrice()).isEqualTo(price);
    }

    @Test
    @DisplayName("Get trip by id not found")
    void testGetTripByIdNotFound() {
        ResponseEntity<Trip> response = restTemplate.getForEntity("/api/trip/1", Trip.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Update trip")
    void testUpdateTrip() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip createdTrip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Trip trip = new Trip(origin, destination, dDate, aDate, 400.0);

        Long id = createdTrip.getTripId();
        String url = "/api/trip/" + id;

        restTemplate.put(url, trip);

        Trip updated = tripRepository.findById(id).get();

        assertThat(updated.getPrice()).isEqualTo(400);
    }

    @Test
    @DisplayName("Update trip not found")
    void testUpdateTripNotFound() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip trip = new Trip(origin, destination, dDate, aDate, price);

        HttpEntity<Trip> requestEntity = new HttpEntity<>(trip);
        
        ResponseEntity<Trip> response = restTemplate.exchange("/api/trip/-1", HttpMethod.PUT, requestEntity, Trip.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete trip")
    void testDeleteTrip() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Trip createdTrip = Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        Long id = createdTrip.getTripId();
        String url = "/api/trip/" + id;

        restTemplate.delete(url);

        assertThat(tripRepository.findById(id)).isEmpty();
    }

    @Test
    @DisplayName("Delete trip not found")
    void testDeleteTripNotFound() {
        Utils.createTestTrip(tripRepository, Utils.createTestCity(cityRepository, "Lisbon"), Utils.createTestCity(cityRepository, "Porto"), LocalDateTime.now(), LocalDateTime.now().plusDays(1), 300);

        ResponseEntity<Trip> response = restTemplate.exchange("/api/trip/-1", HttpMethod.DELETE, null,
                Trip.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Get trips by origin and destination city")
    void testGetTripsByOriginAndDestinationCity() {
        City origin = Utils.createTestCity(cityRepository, "Lisbon");
        City destination = Utils.createTestCity(cityRepository, "Porto");
        LocalDateTime dDate = LocalDateTime.now();
        LocalDateTime aDate = LocalDateTime.now().plusDays(1);
        double price = 300;
        
        Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);
        Utils.createTestTrip(tripRepository, origin, destination, dDate, aDate, price);

        ResponseEntity<List<Trip>> response = restTemplate.exchange("/api/trip?originCity=Lisbon&destinationCity=Porto", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Trip>>() {
                });

        List<Trip> trips = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(trips).hasSize(2).extracting(Trip::getOriginCity).extracting(City::getCityName).contains("Lisbon");
        assertThat(trips).hasSize(2).extracting(Trip::getDestinationCity).extracting(City::getCityName).contains("Porto");
        assertThat(trips).hasSize(2).extracting(Trip::getPrice).contains(price);
    }
}
