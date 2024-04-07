package leikrad.dev.homework1.repositoryTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import leikrad.dev.homework1.data.city.City;
import leikrad.dev.homework1.data.trip.*;

@DataJpaTest
class TripRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TripRepository tripRepository;

    @Test
    @DisplayName("Valid ID should return trip")
    void whenFindByTripId_thenReturnCity() {
        // given
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.flush();

        LocalDateTime originDate = LocalDateTime.now();
        Trip trip = new Trip(city1, city2, originDate, null, 100.0);

        entityManager.persistAndFlush(trip);

        // when
        Trip found = tripRepository.findByTripId(trip.getTripId()).orElse(null);

        // then
        assertThat(found).isEqualTo(trip);
    }

    @Test
    @DisplayName("Invalid ID should return null")
    void whenInvalidId_thenReturnNull() {
        // when
        Trip fromDb = tripRepository.findByTripId(-99L).orElse(null);

        // then
        assertThat(fromDb).isNull();
    }

    @Test
    @DisplayName("Find all should return all trips")
    void whenFindAll_thenReturnAllCities() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        City city3 = new City("Faro");

        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.persist(city3);
        entityManager.flush();

        LocalDateTime originDate = LocalDateTime.now();
        LocalDateTime destinationDate = LocalDateTime.now().plusDays(1);

        Trip trip1 = new Trip(city1, city2, originDate, destinationDate, 100.0);
        Trip trip2 = new Trip(city2, city3, originDate, destinationDate, 100.0);
        Trip trip3 = new Trip(city3, city1, originDate, destinationDate, 100.0);

        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.persist(trip3);
        entityManager.flush();

        // when
        List<Trip> allTrips = tripRepository.findAll();

        // then
        
        assertThat(allTrips).hasSize(3).extracting(Trip::getOriginCity).containsOnly(trip1.getOriginCity(), trip2.getOriginCity(), trip3.getOriginCity());
        assertThat(allTrips).hasSize(3).extracting(Trip::getDestinationCity).containsOnly(trip1.getDestinationCity(), trip2.getDestinationCity(), trip3.getDestinationCity());
    }

    @Test
    @DisplayName("Find by start city should return all trips")
    void whenFindByStartCity_thenReturnAllCities() {
        
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        City city3 = new City("Faro");

        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.persist(city3);
        entityManager.flush();

        LocalDateTime originDate = LocalDateTime.now();
        LocalDateTime destinationDate = LocalDateTime.now().plusDays(1);
        
        Trip trip1 = new Trip(city1, city2, originDate, destinationDate, 100.0);
        Trip trip2 = new Trip(city1, city3, originDate, destinationDate, 100.0);
        Trip trip3 = new Trip(city2, city3, originDate, destinationDate, 100.0);

        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.persist(trip3);
        entityManager.flush();

        // when
        List<Trip> allTrips = tripRepository.findTripsByCities("Lisbon", null);
        // then
        
        assertThat(allTrips).hasSize(2).extracting(Trip::getOriginCity).containsOnly(trip1.getOriginCity(), trip2.getOriginCity());
        assertThat(allTrips).hasSize(2).extracting(Trip::getDestinationCity).containsOnly(trip1.getDestinationCity(), trip2.getDestinationCity());
    }

    @Test
    @DisplayName("Find by end city should return all trips")
    void whenFindByEndCity_thenReturnAllCities() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        City city3 = new City("Faro");
        
        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.persist(city3);
        entityManager.flush();

        LocalDateTime originDate = LocalDateTime.now();
        LocalDateTime destinationDate = LocalDateTime.now().plusDays(1);

        Trip trip1 = new Trip(city1, city2, originDate, destinationDate, 100.0);
        Trip trip2 = new Trip(city2, city1, originDate, destinationDate, 100.0);
        Trip trip3 = new Trip(city3, city1, originDate, destinationDate, 100.0);

        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.persist(trip3);
        entityManager.flush();

        // when
        List<Trip> allTrips = tripRepository.findTripsByCities(null, "Lisbon");
        // then
        
        assertThat(allTrips).hasSize(2).extracting(Trip::getOriginCity).containsOnly(trip2.getOriginCity(), trip3.getOriginCity());
        assertThat(allTrips).hasSize(2).extracting(Trip::getDestinationCity).containsOnly(trip2.getDestinationCity(), trip3.getDestinationCity());
    }

    @Test
    @DisplayName("Find by start and end city should return all trips")
    void whenFindByStartAndEndCity_thenReturnAllCities() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        City city3 = new City("Faro");
        
        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.persist(city3);
        entityManager.flush();

        LocalDateTime originDate = LocalDateTime.now();
        LocalDateTime destinationDate = LocalDateTime.now().plusDays(1);

        Trip trip1 = new Trip(city1, city2, originDate, destinationDate, 100.0);
        Trip trip2 = new Trip(city2, city1, originDate, destinationDate, 100.0);
        Trip trip3 = new Trip(city3, city1, originDate, destinationDate, 100.0);

        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.persist(trip3);
        entityManager.flush();

        // when
        List<Trip> allTrips = tripRepository.findTripsByCities("Lisbon", "Porto");

        // then
        
        assertThat(allTrips).hasSize(1).extracting(Trip::getOriginCity).containsOnly(trip1.getOriginCity());
        assertThat(allTrips).hasSize(1).extracting(Trip::getDestinationCity).containsOnly(trip1.getDestinationCity());
    }

    @Test
    @DisplayName("Delete by ID should remove trip")
    void whenDeleteByTripId_thenRemoveCity() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        
        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.flush();

        LocalDateTime originDate = LocalDateTime.now();
        LocalDateTime destinationDate = LocalDateTime.now().plusDays(1);

        Trip trip = new Trip(city1, city2, originDate, destinationDate, 100.0);
        entityManager.persistAndFlush(trip);

        // when

        tripRepository.deleteByTripId(trip.getTripId());

        // then
        List<Trip> allTrips = tripRepository.findAll();
        assertThat(allTrips).isEmpty();
    }

    @Test
    @DisplayName("Create Trip")
    void whenCreateTrip_thenShouldCreateTrip() {
        // given
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.flush();

        LocalDateTime originDate = LocalDateTime.now();
        LocalDateTime destinationDate = LocalDateTime.now().plusDays(1);

        Trip trip = new Trip(city1, city2, originDate, destinationDate, 100.0);

        // when
        Trip savedTrip = tripRepository.save(trip);

        // then
        Trip found = tripRepository.findByTripId(savedTrip.getTripId()).orElse(null);

        assertThat(found).isNotNull().isEqualTo(savedTrip);
    }

    @Test
    @DisplayName("Update Trip")
    void whenUpdateTrip_thenShouldUpdateTrip() {
        // given
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.flush();
    
        LocalDateTime originDate = LocalDateTime.now();
        LocalDateTime destinationDate = LocalDateTime.now().plusDays(1);
    
        Trip trip = new Trip(city1, city2, originDate, destinationDate, 100.0);
        trip = tripRepository.save(trip);
    
        // when
        trip.setPrice(200.0);
        tripRepository.save(trip);
    
        // then
        Trip found = tripRepository.findByTripId(trip.getTripId()).orElse(null);

        assertThat(found).isNotNull().isEqualTo(trip);
    }
}

