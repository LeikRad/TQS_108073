package leikrad.dev.homework1.unitTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
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

        Reservation reservation1 = new Reservation(trip1, "John Doe", "123456789", "a1b2c3d4e5f6g7h8i9");
        Reservation reservation2 = new Reservation(trip1, "Jane Doe", "987654321", "d2x3c4d5e6f7g8h9i0");
        Reservation reservation3 = new Reservation(trip1, "John Smith", "123456789", "xa2b3c4d5e6f7g8h9i0");

        reservation1.setReservationId(1L);
        reservation2.setReservationId(2L);
        reservation3.setReservationId(3L);

        List<Reservation> allReservations = List.of(reservation1, reservation2, reservation3);

        Mockito.when(reservationRepository.findAll()).thenReturn(allReservations);
        Mockito.when(reservationRepository.findByReservationId(reservation1.getReservationId())).thenReturn(Optional.of(reservation1));
        Mockito.when(reservationRepository.findByReservationId(reservation2.getReservationId())).thenReturn(Optional.of(reservation2));
        Mockito.when(reservationRepository.findByReservationId(reservation3.getReservationId())).thenReturn(Optional.of(reservation3));

        Mockito.when(reservationRepository.findByUuid(reservation1.getUuid())).thenReturn(Optional.of(reservation1));
        Mockito.when(reservationRepository.findByUuid(reservation2.getUuid())).thenReturn(Optional.of(reservation2));
        Mockito.when(reservationRepository.findByUuid(reservation3.getUuid())).thenReturn(Optional.of(reservation3));
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

        Reservation reservation1 = new Reservation(trip1, "John Doe", "123456789", "a1b2c3d4e5f6g7h8i9");
        Reservation reservation2 = new Reservation(trip1, "Jane Doe", "987654321", "d2x3c4d5e6f7g8h9i0");
        Reservation reservation3 = new Reservation(trip1, "John Smith", "123456789", "xa2b3c4d5e6f7g8h9i0"); 

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
        
        Reservation reservation = new Reservation(trip1, "John Doe", "123456789", "a1b2c3d4e5f6g7h8i9");

        reservation.setReservationId(1L);

        Reservation found = reservationManagerService.getReservationDetails(reservation.getReservationId()).orElse(null);

        verifyFindByReservationIdCalledOnce();
        assertThat(found).isNotNull();
        assertThat(found.getPersonName()).isEqualTo(reservation.getPersonName());
        assertThat(found.getPhoneNumber()).isEqualTo(reservation.getPhoneNumber());
        assertThat(found.getReservationId()).isEqualTo(reservation.getReservationId());
        assertThat(found.getUuid()).isEqualTo(reservation.getUuid());
    }

    @Test
    @DisplayName("Test get reservation by id with invalid id")
    void testGetReservationDetailsWithInvalidId() {
        Reservation found = reservationManagerService.getReservationDetails(-1L).orElse(null);

        verifyFindByReservationIdCalledOnce();
        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Test delete reservation")
    void whenValidId_thenReservationShouldBeDeleted() {
        Long reservationId = 1L;

        reservationManagerService.deleteReservation(reservationId);

        verifyDeleteByReservationIdIsCalledOnce();
    }

    @Test
    @DisplayName("Test create reservation")
    void testCreateReservation() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);

        trip1.setTripId(1L);

        Reservation actualRes = new Reservation(trip1, "John Doe", "123456789", UUID.randomUUID().toString());
        
        // copy of actualRes
        Reservation actualCreatedRes = new Reservation(trip1, "John Doe", "123456789", UUID.randomUUID().toString());
        actualCreatedRes.setReservationId(1L);

        Mockito.when(reservationRepository.save(actualRes)).thenReturn(actualCreatedRes);

        Reservation reservation = reservationManagerService.createReservation(actualRes);

        assertThat(reservation).isNotNull().isEqualTo(actualCreatedRes);
        verifySaveIsCalledOnce();
    }

    @Test
    @DisplayName("Test update reservation")
    void testUpdateReservation() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);

        trip1.setTripId(1L);

        String uuid = UUID.randomUUID().toString();
        Reservation actualReservation = new Reservation(trip1, "John Doe", "123456789", uuid);
        Reservation actualCreateddReservation = new Reservation(trip1, "John Doe", "123456789", uuid);
        Reservation actualReservationUpdate = new Reservation(trip1, "Jane Doe", "987654321", uuid);
        
        actualCreateddReservation.setReservationId(1L);
        actualReservationUpdate.setReservationId(1L);

        Mockito.when(reservationRepository.save(actualReservation)).thenReturn(actualCreateddReservation);
        Mockito.when(reservationRepository.save(actualReservationUpdate)).thenReturn(actualReservationUpdate);

        reservationManagerService.createReservation(actualReservation);
        Reservation updatedReservation = reservationManagerService.updateReservation(actualReservationUpdate);

        assertThat(updatedReservation).isNotNull().isEqualTo(actualReservationUpdate);
        verifySaveIsCalledTwice();
    }

    @Test
    @DisplayName("Test update reservation with invalid id")
    void testUpdateReservationWithInvalidId() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);

        trip1.setTripId(1L);

        String uuid = UUID.randomUUID().toString();
        Reservation actualReservation = new Reservation(trip1, "John Doe", "123456789", uuid);
        Reservation actualCreatedReservation = new Reservation(trip1, "John Doe", "123456789", uuid);
        Reservation actualReservationUpdate = new Reservation(trip1, "Jane Doe", "987654321", uuid);
        
        actualCreatedReservation.setReservationId(1L);
        actualReservationUpdate.setReservationId(-1L);

        Mockito.when(reservationRepository.save(actualReservation)).thenReturn(actualCreatedReservation);
        Mockito.when(reservationRepository.save(actualReservationUpdate)).thenReturn(actualReservationUpdate);

        reservationManagerService.createReservation(actualReservation);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationManagerService.updateReservation(actualReservationUpdate);
        });

        verifySaveIsCalledOnce();
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessage("Reservation not found");
    }

    @Test
    @DisplayName("Test Create with invalid reservation")
    void testCreateWithInvalidReservation() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);

        trip1.setTripId(1L);

        Reservation actualRes = new Reservation(trip1, "John Doe", "123456789", UUID.randomUUID().toString());
        actualRes.setReservationId(1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationManagerService.createReservation(actualRes);
        });

        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessage("Reservation ID must be null");
        verifySaveIsntCalled();
    }
    
    private void verifyFindAllReservationsIsCalledOnce() {
        Mockito.verify(reservationRepository, Mockito.times(1)).findAll();
    }

    private void verifyFindByReservationIdCalledOnce() {
        Mockito.verify(reservationRepository, Mockito.times(1)).findByReservationId(Mockito.anyLong());
    }

    private void verifyDeleteByReservationIdIsCalledOnce() {
        Mockito.verify(reservationRepository, Mockito.times(1)).deleteByReservationId(Mockito.anyLong());
    }

    private void verifySaveIsCalledOnce() {
        Mockito.verify(reservationRepository, Mockito.times(1)).save(Mockito.any(Reservation.class));
    }

    private void verifySaveIsCalledTwice() {
        Mockito.verify(reservationRepository, Mockito.times(2)).save(Mockito.any(Reservation.class));
    }

    private void verifySaveIsntCalled() {
        Mockito.verify(reservationRepository, Mockito.never()).save(Mockito.any(Reservation.class));
    }
}
