package leikrad.dev.homework1.repositoryTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import leikrad.dev.homework1.data.trip.*;

@DataJpaTest
public class TripRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TripRepository tripRepository;

    @Test
    @DisplayName("Valid ID should return trip")
    void whenFindById_thenReturnCity() {
        // given
        Trip trip = new Trip("Lisbon", "Porto", "2021-01-01", "2021-01-02", 100);
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
        Trip trip1 = new Trip("Lisbon", "Porto", "2021-01-01", "2021-01-02", 100);
        Trip trip2 = new Trip("Porto", "Faro", "2021-01-01", "2021-01-02", 100);
        Trip trip3 = new Trip("Faro", "Lisbon", "2021-01-01", "2021-01-02", 100);

        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.persist(trip3);
        entityManager.flush();

        // when
        List<Trip> allTrips = tripRepository.findAll();

        // then
        
        assertThat(allTrips).hasSize(3).extracting(Trip::getStartCity).containsOnly(trip1.getStartCity(), trip2.getStartCity(), trip3.getStartCity());
        assertThat(allTrips).hasSize(3).extracting(Trip::getEndCity).containsOnly(trip1.getEndCity(), trip2.getEndCity(), trip3.getEndCity());
    }

    @Test
    @DisplayName("Find by start city should return all trips")
    void whenFindByStartCity_thenReturnAllCities() {
        Trip trip1 = new Trip("Lisbon", "Porto", "2021-01-01", "2021-01-02", 100);
        Trip trip2 = new Trip("Lisbon", "Faro", "2021-01-01", "2021-01-02", 100);
        Trip trip3 = new Trip("Faro", "Lisbon", "2021-01-01", "2021-01-02", 100);

        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.persist(trip3);
        entityManager.flush();

        // when
        List<Trip> allTrips = tripRepository.findByStartCity("Lisbon");

        // then
        
        assertThat(allTrips).hasSize(2).extracting(Trip::getStartCity).containsOnly(trip1.getStartCity(), trip2.getStartCity());
        assertThat(allTrips).hasSize(2).extracting(Trip::getEndCity).containsOnly(trip1.getEndCity(), trip2.getEndCity());
    }

    @Test
    @DisplayName("Find by end city should return all trips")
    void whenFindByEndCity_thenReturnAllCities() {
        Trip trip1 = new Trip("Lisbon", "Porto", "2021-01-01", "2021-01-02", 100);
        Trip trip2 = new Trip("Porto", "Lisbon", "2021-01-01", "2021-01-02", 100);
        Trip trip3 = new Trip("Faro", "Lisbon", "2021-01-01", "2021-01-02", 100);

        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.persist(trip3);
        entityManager.flush();

        // when
        List<Trip> allTrips = tripRepository.findByEndCity("Lisbon");

        // then
        
        assertThat(allTrips).hasSize(2).extracting(Trip::getStartCity).containsOnly(trip2.getStartCity(), trip3.getStartCity());
        assertThat(allTrips).hasSize(2).extracting(Trip::getEndCity).containsOnly(trip2.getEndCity(), trip3.getEndCity());
    }

    @Test
    @DisplayName("Find by start and end city should return all trips")
    void whenFindByStartAndEndCity_thenReturnAllCities() {
        Trip trip1 = new Trip("Lisbon", "Porto", "2021-01-01", "2021-01-02", 100);
        Trip trip2 = new Trip("Porto", "Lisbon", "2021-01-01", "2021-01-02", 100);
        Trip trip3 = new Trip("Faro", "Lisbon", "2021-01-01", "2021-01-02", 100);

        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.persist(trip3);
        entityManager.flush();

        // when
        List<Trip> allTrips = tripRepository.findByStartCityAndEndCity("Lisbon", "Porto");

        // then
        
        assertThat(allTrips).hasSize(1).extracting(Trip::getStartCity).containsOnly(trip1.getStartCity());
        assertThat(allTrips).hasSize(1).extracting(Trip::getEndCity).containsOnly(trip1.getEndCity());
    }
}

