package leikrad.dev.homework1.unitTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import leikrad.dev.homework1.data.city.City;
import leikrad.dev.homework1.data.trip.*;
import leikrad.dev.homework1.service.TripManagerService;

@ExtendWith(MockitoExtension.class)
class TripService_UnitTest {

    @Mock(lenient = true)
    private TripRepository tripRepository;

    @InjectMocks
    private TripManagerService tripManagerService;

    @BeforeEach
    public void setUp() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        Trip trip2 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 200.0);
        Trip trip3 = new Trip(city2, city1, LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300.0);

        trip1.setTripId(1L);
        trip2.setTripId(2L);
        trip3.setTripId(3L);
        
        List<Trip> allTrips = List.of(trip1, trip2, trip3);

        Mockito.when(tripRepository.findAll()).thenReturn(allTrips);
        Mockito.when(tripRepository.findByTripId(trip1.getTripId())).thenReturn(Optional.of(trip1));
        Mockito.when(tripRepository.findByTripId(trip2.getTripId())).thenReturn(Optional.of(trip2));
        Mockito.when(tripRepository.findByTripId(trip3.getTripId())).thenReturn(Optional.of(trip3));

        Mockito.when(tripRepository.findTripsByCities("Lisbon", null)).thenReturn(List.of(trip1, trip2));
        Mockito.when(tripRepository.findTripsByCities(null, "Porto")).thenReturn(List.of(trip1, trip2));
        Mockito.when(tripRepository.findTripsByCities("Porto", "Lisbon")).thenReturn(List.of(trip3));
    }

    @Test
    @DisplayName("Test get all trips")
    void testGetAllTrips() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        Trip trip2 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 200.0);
        Trip trip3 = new Trip(city2, city1, LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300.0);

        trip1.setTripId(1L);
        trip2.setTripId(2L);
        trip3.setTripId(3L);

        List<Trip> allTrips = tripManagerService.getAllTrips();

        verifyFindAllTripsIsCalledOnce();
        assertThat(allTrips).hasSize(3).extracting(Trip::getTripId).contains(trip1.getTripId(), trip2.getTripId(), trip3.getTripId());
    }

    @Test
    @DisplayName("Test get trip by id")
    void testGetTripDetails() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        trip.setTripId(1L);

        Trip found = tripManagerService.getTripDetails(trip.getTripId()).orElse(null);

        verifyFindTripByIdIsCalledOnce();
        assertThat(found.getTripId()).isEqualTo(trip.getTripId());
        assertThat(found.getOriginCity().getCityName()).isEqualTo(trip.getOriginCity().getCityName());
        assertThat(found.getDestinationCity().getCityName()).isEqualTo(trip.getDestinationCity().getCityName());
    }

    @Test
    @DisplayName("Test get trip by id with invalid id")
    void testGetTripDetailsWithInvalidId() {
        Trip found = tripManagerService.getTripDetails(-1L).orElse(null);

        verifyFindTripByIdIsCalledOnce();
        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Test delete trip")
    void testDeleteTrip() {
        Long tripId = 1L;

        tripManagerService.deleteTrip(tripId);

        verifyDeleteTripByIdIsCalledOnce();
    }


    @Test
    @DisplayName("Test get trips by origin city")
    void testGetTripsByOriginCity() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        Trip trip2 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 200.0);

        trip1.setTripId(1L);
        trip2.setTripId(2L);

        List<Trip> trips = tripManagerService.getTripsByOriginAndDestinationCity(city1.getCityName(), null);

        verifyFindTripsByOriginCityIsCalledOnce();
        assertThat(trips).hasSize(2).extracting(Trip::getTripId).contains(trip1.getTripId(), trip2.getTripId());
    }

    @Test
    @DisplayName("Test get trips by destination city")
    void testGetTripsByDestinationCity() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        Trip trip2 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 200.0);

        trip1.setTripId(1L);
        trip2.setTripId(2L);

        List<Trip> trips = tripManagerService.getTripsByOriginAndDestinationCity(null, city2.getCityName());

        verifyFindTripsByDestinationCityIsCalledOnce();
        assertThat(trips).hasSize(2).extracting(Trip::getTripId).contains(trip1.getTripId(), trip2.getTripId());
    }

    @Test
    @DisplayName("Test get trips by origin and destination city")
    void testGetTripsByOriginAndDestinationCity() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip = new Trip(city2, city1, LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300.0);

        trip.setTripId(3L);


        List<Trip> trips = tripManagerService.getTripsByOriginAndDestinationCity(city2.getCityName(), city1.getCityName());

        verifyFindTripsByOriginAndDestinationCityIsCalledOnce();
        assertThat(trips).hasSize(1).extracting(Trip::getTripId).contains(trip.getTripId());
    }

    @Test
    @DisplayName("Test create trip")
    void testCreateTrip() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip actualTrip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        Trip actualCreatedTrip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        
        actualCreatedTrip.setTripId(1L);
    
        Mockito.when(tripRepository.save(actualTrip)).thenReturn(actualCreatedTrip);

        
        Trip createdTrip = tripManagerService.createTrip(actualTrip);

        verifyCreateTripIsCalledOnce();
        assertThat(createdTrip).isNotNull().isEqualTo(actualCreatedTrip);
    }

    @Test
    @DisplayName("Update trip")
    void testUpdateTrip() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip actualTrip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        Trip actualCreatedTrip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        Trip actualUpdatedTrip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 200.0);
        
        actualCreatedTrip.setTripId(1L);
        actualUpdatedTrip.setTripId(1L);

        Mockito.when(tripRepository.save(actualTrip)).thenReturn(actualCreatedTrip);
        Mockito.when(tripRepository.save(actualUpdatedTrip)).thenReturn(actualUpdatedTrip);

        tripManagerService.createTrip(actualTrip);
        Trip updatedTrip = tripManagerService.updateTrip(actualUpdatedTrip);

        verifyCreateTripIsCalledTwice();
        assertThat(updatedTrip).isNotNull().isEqualTo(actualUpdatedTrip);
    }

    @Test
    @DisplayName("Update trip with invalid id")
    void testUpdateTripWithInvalidId() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip actualTrip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        Trip actualCreatedTrip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        Trip actualUpdatedTrip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 200.0);
        
        actualCreatedTrip.setTripId(1L);
        actualUpdatedTrip.setTripId(-1L);

        Mockito.when(tripRepository.save(actualTrip)).thenReturn(actualCreatedTrip);
        Mockito.when(tripRepository.save(actualUpdatedTrip)).thenReturn(actualUpdatedTrip);

        tripManagerService.createTrip(actualTrip);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            tripManagerService.updateTrip(actualUpdatedTrip);
        });

        verifyCreateTripIsCalledOnce();
        assertThat(exception).isInstanceOf(EntityNotFoundException.class).hasMessage("Trip not found");
    }

    @Test
    @DisplayName("Create with invalid Trip")
    void testCreateTripWithInvalidTrip() {
        Trip actualTrip = new Trip(null, null, null, null, 100.0);

        actualTrip.setTripId(1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tripManagerService.createTrip(actualTrip);
        });

        verifyCreateTripIsntCalled();
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessage("Trip ID must be null");
    }

    @Test
    @DisplayName("Delete trip with invalid id")
    void testDeleteTripWithInvalidId() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            tripManagerService.deleteTrip(-1L);
        });

        verifyDeleteTripByIdIsntCalled();
        assertThat(exception).isInstanceOf(EntityNotFoundException.class).hasMessage("Trip not found");
    }

    private void verifyFindAllTripsIsCalledOnce() {
        Mockito.verify(tripRepository, Mockito.times(1)).findAll();
    }

    private void verifyFindTripByIdIsCalledOnce() {
        Mockito.verify(tripRepository, Mockito.times(1)).findByTripId(Mockito.anyLong());
    }

    private void verifyDeleteTripByIdIsCalledOnce() {
        Mockito.verify(tripRepository, Mockito.times(1)).deleteByTripId(Mockito.anyLong());
    }

    private void verifyFindTripsByOriginCityIsCalledOnce() {
        Mockito.verify(tripRepository, Mockito.times(1)).findTripsByCities(Mockito.anyString(), Mockito.isNull());
    }

    private void verifyFindTripsByDestinationCityIsCalledOnce() {
        Mockito.verify(tripRepository, Mockito.times(1)).findTripsByCities(Mockito.isNull(), Mockito.anyString());
    }

    private void verifyFindTripsByOriginAndDestinationCityIsCalledOnce() {
        Mockito.verify(tripRepository, Mockito.times(1)).findTripsByCities(Mockito.anyString(), Mockito.anyString());
    }

    private void verifyCreateTripIsCalledOnce() {
        Mockito.verify(tripRepository, Mockito.times(1)).save(Mockito.any(Trip.class));
    }

    private void verifyCreateTripIsCalledTwice() {
        Mockito.verify(tripRepository, Mockito.times(2)).save(Mockito.any(Trip.class));
    }

    private void verifyCreateTripIsntCalled() {
        Mockito.verify(tripRepository, Mockito.times(0)).save(Mockito.any(Trip.class));
    }

    private void verifyDeleteTripByIdIsntCalled() {
        Mockito.verify(tripRepository, Mockito.times(0)).deleteByTripId(Mockito.anyLong());
    }

}
