package leikrad.dev.homework1.unitTests;

import static org.assertj.core.api.Assertions.assertThat;

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
        Mockito.when(tripRepository.findByTripId(-1L)).thenReturn(Optional.empty());

        Mockito.when(tripRepository.findByOriginCityCityName(city1.getCityName())).thenReturn(List.of(trip1, trip2));
        Mockito.when(tripRepository.findByDestinationCityCityName(city2.getCityName())).thenReturn(List.of(trip1, trip2));
        Mockito.when(tripRepository.findByOriginCityCityNameAndDestinationCityCityName(city1.getCityName(), city2.getCityName())).thenReturn(List.of(trip1, trip2));
    
        Mockito.when(tripRepository.findByOriginCityCityName(city2.getCityName())).thenReturn(List.of(trip3));
        Mockito.when(tripRepository.findByDestinationCityCityName(city1.getCityName())).thenReturn(List.of(trip3));
        Mockito.when(tripRepository.findByOriginCityCityNameAndDestinationCityCityName(city2.getCityName(), city1.getCityName())).thenReturn(List.of(trip3));
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

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        trip1.setTripId(1L);

        Trip trip = tripManagerService.getTripDetails(trip1.getTripId()).orElse(null);

        verifyFindTripByIdIsCalledOnce();
        assertThat(trip.getTripId()).isEqualTo(trip1.getTripId());
        assertThat(trip.getOriginCity().getCityName()).isEqualTo(trip1.getOriginCity().getCityName());
        assertThat(trip.getDestinationCity().getCityName()).isEqualTo(trip1.getDestinationCity().getCityName());
    }

    @Test
    @DisplayName("Test get trip by id with invalid id")
    void testGetTripDetailsWithInvalidId() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        trip.setTripId(1L);

        Trip found = tripManagerService.getTripDetails(-1L).orElse(null);

        verifyFindTripByIdIsCalledOnce();
        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Test delete trip")
    void whenValidId_thenTripShouldBeDeleted() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        trip1.setTripId(1L);

        tripManagerService.deleteTrip(trip1.getTripId());

        verifyDeleteTripByIdIsCalledOnce();
    }

    @Test
    @DisplayName("Test delete trip with invalid id")
    void whenInvalidId_thenTripShouldNotBeDeleted() {
        City city1 = new City("Lisbon");
        City city2 = new City("Porto");

        city1.setCityId(1L);
        city2.setCityId(2L);

        Trip trip = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        trip.setTripId(1L);

        tripManagerService.deleteTrip(-1L);

        verifyDeleteTripByIdWasntCalled();
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

        List<Trip> trips = tripManagerService.getTripsByOriginCity(city1.getCityName());

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

        List<Trip> trips = tripManagerService.getTripsByDestinationCity(city2.getCityName());

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

        Trip trip1 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 100.0);
        Trip trip2 = new Trip(city1, city2, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 200.0);

        trip1.setTripId(1L);
        trip2.setTripId(2L);

        List<Trip> trips = tripManagerService.getTripsByOriginAndDestinationCity(city1.getCityName(), city2.getCityName());

        verifyFindTripsByOriginAndDestinationCityIsCalledOnce();
        assertThat(trips).hasSize(2).extracting(Trip::getTripId).contains(trip1.getTripId(), trip2.getTripId());
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

    private void verifyDeleteTripByIdWasntCalled() {
        Mockito.verify(tripRepository, Mockito.never()).deleteByTripId(Mockito.anyLong());
    }

    private void verifyFindTripsByOriginCityIsCalledOnce() {
        Mockito.verify(tripRepository, Mockito.times(1)).findByOriginCityCityName(Mockito.anyString());
    }

    private void verifyFindTripsByDestinationCityIsCalledOnce() {
        Mockito.verify(tripRepository, Mockito.times(1)).findByDestinationCityCityName(Mockito.anyString());
    }

    private void verifyFindTripsByOriginAndDestinationCityIsCalledOnce() {
        Mockito.verify(tripRepository, Mockito.times(1)).findByOriginCityCityNameAndDestinationCityCityName(Mockito.anyString(), Mockito.anyString());
    }
}
