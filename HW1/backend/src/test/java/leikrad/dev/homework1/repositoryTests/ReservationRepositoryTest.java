package leikrad.dev.homework1.repositoryTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import leikrad.dev.homework1.data.city.City;
import leikrad.dev.homework1.data.reservation.*;
import leikrad.dev.homework1.data.trip.Trip;

@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Valid ID should return reservation")
    void whenFindById_thenReturnCity() {
        // given
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.flush();
        LocalDateTime originDate = LocalDateTime.now();
        LocalDateTime destinationDate = LocalDateTime.now().plusDays(1);

        Trip trip = new Trip(city1, city2, originDate, destinationDate, 100.0);
        entityManager.persist(trip);
        entityManager.flush();
        
        Reservation reservation = new Reservation(trip, "John Doe", "123456789", UUID.randomUUID().toString());
        entityManager.persistAndFlush(reservation);

        // when
        Reservation found = reservationRepository.findByReservationId(reservation.getReservationId()).orElse(null);

        // then
        assertThat(found).isEqualTo(reservation);
    }

    @Test
    @DisplayName("Invalid ID should return null")
    void whenInvalidId_thenReturnNull() {
        // when
        Reservation fromDb = reservationRepository.findByReservationId(-99L).orElse(null);

        // then
        assertThat(fromDb).isNull();
    }

    @Test
    @DisplayName("Find all should return all cities")
    void whenFindAll_thenReturnAllCities() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.flush();
        LocalDateTime originDate = LocalDateTime.now();
        LocalDateTime destinationDate = LocalDateTime.now().plusDays(1);

        Trip trip = new Trip(city1, city2, originDate, destinationDate, 100.0);
        entityManager.persist(trip);
        entityManager.flush();

        Reservation reservation1 = new Reservation(trip, "John Doe", "123456789", UUID.randomUUID().toString());
        Reservation reservation2 = new Reservation(trip, "Jane Doe", "987654321", UUID.randomUUID().toString());
        Reservation reservation3 = new Reservation(trip, "John Smith", "123456789", UUID.randomUUID().toString());
        
        entityManager.persist(reservation1);
        entityManager.persist(reservation2);
        entityManager.persist(reservation3);
        entityManager.flush();

        // when
        List<Reservation> allReservations = reservationRepository.findAll();

        // then
        
        assertThat(allReservations).hasSize(3).extracting(Reservation::getTrip).containsOnly(trip);
        assertThat(allReservations).hasSize(3).extracting(Reservation::getPersonName).containsOnly(reservation1.getPersonName(), reservation2.getPersonName(), reservation3.getPersonName());
        assertThat(allReservations).hasSize(3).extracting(Reservation::getPhoneNumber).containsOnly(reservation1.getPhoneNumber(), reservation2.getPhoneNumber(), reservation3.getPhoneNumber());
        assertThat(allReservations).hasSize(3).extracting(Reservation::getReservationId).containsOnly(reservation1.getReservationId(), reservation2.getReservationId(), reservation3.getReservationId());
        assertThat(allReservations).hasSize(3).extracting(Reservation::getReservationId).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Delete by ID should remove reservation")
    void whenDeleteById_thenRemoveReservation() {
        // given
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");
        entityManager.persist(city1);
        entityManager.persist(city2);
        entityManager.flush();
        LocalDateTime originDate = LocalDateTime.now();
        LocalDateTime destinationDate = LocalDateTime.now().plusDays(1);

        Trip trip = new Trip(city1, city2, originDate, destinationDate, 100.0);
        entityManager.persist(trip);
        entityManager.flush();
        
        Reservation reservation = new Reservation(trip, "John Doe", "123456789", UUID.randomUUID().toString());
        entityManager.persistAndFlush(reservation);

        // when
        reservationRepository.deleteByReservationId(reservation.getReservationId());

        // then
        assertThat(reservationRepository.findByReservationId(reservation.getReservationId())).isEmpty();
    }

    @Test
    @DisplayName("Create Reservation")
    public void testCreateReservation() {
        // given
        Reservation reservation = new Reservation();
        reservation.setPersonName("Another name");
        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        Reservation found = reservationRepository.findByReservationId(savedReservation.getReservationId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getPersonName()).isEqualTo(reservation.getPersonName());
    }

    @Test
    @DisplayName("Update Reservation")
    public void testUpdateReservation() {
        // given
        Reservation reservation = new Reservation();
        reservation.setPersonName("Test Reservation");
        reservation = reservationRepository.save(reservation);

        // when
        reservation.setPersonName("Another Name");
        reservationRepository.save(reservation);

        // then
        Reservation updatedReservation = reservationRepository.findByReservationId(reservation.getReservationId()).orElse(null);
        assertThat(updatedReservation).isNotNull();
        assertThat(updatedReservation.getPersonName()).isEqualTo(reservation.getPersonName());
    }
}