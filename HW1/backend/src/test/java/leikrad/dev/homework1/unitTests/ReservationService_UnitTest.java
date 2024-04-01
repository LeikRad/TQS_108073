package leikrad.dev.homework1.unitTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import leikrad.dev.homework1.service.ReservationManagerService;
import leikrad.dev.homework1.data.city.City;
import leikrad.dev.homework1.data.reservation.*;
import leikrad.dev.homework1.data.trip.Trip;

@ExtendWith(MockitoExtension.class)
class ReservationService_UnitTest {
    
    @Mock(lenient = true)
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationManagerService reservationManagerService;

    @BeforeEach
    public void setUp() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);

        trip1.setTripId(1L);

        Reservation reservation1 = new Reservation(trip1, "John Doe", "123456789", UUID.randomUUID().toString());
        Reservation reservation2 = new Reservation(trip1, "Jane Doe", "987654321", UUID.randomUUID().toString());
        Reservation reservation3 = new Reservation(trip1, "John Smith", "123456789", UUID.randomUUID().toString());

        reservation1.setReservationId(1L);
        reservation2.setReservationId(2L);
        reservation3.setReservationId(3L);

        List<Reservation> allReservations = List.of(reservation1, reservation2, reservation3);

        Mockito.when(reservationRepository.findAll()).thenReturn(allReservations);
        Mockito.when(reservationRepository.findByReservationId(reservation1.getReservationId())).thenReturn(Optional.of(reservation1));
        Mockito.when(reservationRepository.findByReservationId(reservation2.getReservationId())).thenReturn(Optional.of(reservation2));
        Mockito.when(reservationRepository.findByReservationId(reservation3.getReservationId())).thenReturn(Optional.of(reservation3));
    }

    @Test
    @DisplayName("Test get all reservations")
    void testGetAllReservations() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);

        trip1.setTripId(1L);

        Reservation reservation1 = new Reservation(trip1, "John Doe", "123456789", UUID.randomUUID().toString());
        Reservation reservation2 = new Reservation(trip1, "Jane Doe", "987654321", UUID.randomUUID().toString());
        Reservation reservation3 = new Reservation(trip1, "John Smith", "123456789", UUID.randomUUID().toString());

        reservation1.setReservationId(1L);
        reservation2.setReservationId(2L);
        reservation3.setReservationId(3L);

        List<Reservation> allReservations = reservationManagerService.getAllReservations();

        verifyFindAllReservationsIsCalledOnce();
        assertThat(allReservations).hasSize(3).extracting(Reservation::getPersonName).contains(reservation1.getPersonName(), reservation2.getPersonName(), reservation3.getPersonName());
    }

    @Test
    @DisplayName("Test get reservation by id")
    void testGetReservationDetails() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);
        

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);

        trip1.setTripId(1L);
        
        Reservation reservation1 = new Reservation(trip1, "John Doe", "123456789", UUID.randomUUID().toString());

        reservation1.setReservationId(1L);

        Reservation reservation = reservationManagerService.getReservationDetails(reservation1.getReservationId()).orElse(null);

        verifyFindReservationByIdIsCalledOnce();
        assertThat(reservation).isNotNull();
        assertThat(reservation.getPersonName()).isEqualTo(reservation1.getPersonName());
        assertThat(reservation.getPhoneNumber()).isEqualTo(reservation1.getPhoneNumber());
        assertThat(reservation.getReservationId()).isEqualTo(reservation1.getReservationId());
    }

    @Test
    @DisplayName("Test get reservation by id with invalid id")
    void testGetReservationDetailsWithInvalidId() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);

        trip1.setTripId(1L);

        Reservation reservation = new Reservation(trip1, "John Doe", "123456789", UUID.randomUUID().toString());

        reservation.setReservationId(1L);

        Reservation found = reservationManagerService.getReservationDetails(-1L).orElse(null);

        verifyFindReservationByIdIsCalledOnce();
        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Test delete reservation")
    void whenValidId_thenReservationShouldBeDeleted() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);

        trip1.setTripId(1L);

        Reservation reservation1 = new Reservation(trip1, "John Doe", "123456789", UUID.randomUUID().toString());

        reservation1.setReservationId(1L);

        reservationManagerService.deleteReservation(reservation1.getReservationId());

        verifyDeleteReservationByIdIsCalledOnce();
    }

    @Test
    @DisplayName("Test delete reservation with invalid id")
    void whenInvalidId_thenReservationShouldNotBeDeleted() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);

        trip1.setTripId(1L);

        Reservation reservation = new Reservation(trip1, "John Doe", "123456789", UUID.randomUUID().toString());

        reservation.setReservationId(1L);

        reservationManagerService.deleteReservation(-1L);

        verifyDeleteReservationByIdWasntCalled();
    }
    
    private void verifyFindAllReservationsIsCalledOnce() {
        Mockito.verify(reservationRepository, Mockito.times(1)).findAll();
    }

    private void verifyFindReservationByIdIsCalledOnce() {
        Mockito.verify(reservationRepository, Mockito.times(1)).findByReservationId(Mockito.anyLong());
    }

    private void verifyDeleteReservationByIdIsCalledOnce() {
        Mockito.verify(reservationRepository, Mockito.times(1)).deleteByReservationId(Mockito.anyLong());
    }

    private void verifyDeleteReservationByIdWasntCalled() {
        Mockito.verify(reservationRepository, Mockito.times(0)).deleteByReservationId(Mockito.anyLong());
    }
}
