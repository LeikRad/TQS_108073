package leikrad.dev.homework1.repositoryTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

import leikrad.dev.homework1.data.reservation.*;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Valid ID should return reservation")
    void whenFindById_thenReturnCity() {
        // given
        Reservation reservation = new Reservation("Lisbon", "John Doe", "123456789", UUID.randomUUID().toString());
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
        Reservation reservation1 = new Reservation("Lisbon", "John Doe", "123456789", UUID.randomUUID().toString());
        Reservation reservation2 = new Reservation("Porto", "Jane Doe", "987654321", UUID.randomUUID().toString());
        Reservation reservation3 = new Reservation("Faro", "John Smith", "123456789", UUID.randomUUID().toString());

        entityManager.persist(reservation1);
        entityManager.persist(reservation2);
        entityManager.persist(reservation3);
        entityManager.flush();

        // when
        List<Reservation> allReservations = reservationRepository.findAll();

        // then
        
        assertThat(allReservations).hasSize(3).extracting(Reservation::getCity).containsOnly(reservation1.getCity(), reservation2.getCity(), reservation3.getCity());
        assertThat(allReservations).hasSize(3).extracting(Reservation::getCustomerName).containsOnly(reservation1.getCustomerName(), reservation2.getCustomerName(), reservation3.getCustomerName());
        assertThat(allReservations).hasSize(3).extracting(Reservation::getCustomerPhone).containsOnly(reservation1.getCustomerPhone(), reservation2.getCustomerPhone(), reservation3.getCustomerPhone());
        assertThat(allReservations).hasSize(3).extracting(Reservation::getReservationId).containsOnly(reservation1.getReservationId(), reservation2.getReservationId(), reservation3.getReservationId());
        assertThat(allReservations).hasSize(3).extracting(Reservation::getReservationId).doesNotHaveDuplicates();
    }
}