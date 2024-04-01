package leikrad.dev.homework1.service;

import java.util.List;
import java.util.Optional;

import leikrad.dev.homework1.data.trip.*;

public class TripManagerService {
    
    private TripRepository tripRepository;

    public TripManagerService(TripRepository tripRepository) {}

    public List<Trip> getAllTrips() {
    }

    public Optional<Trip> getTripDetails(Long tripId) {
    }

    public void deleteTrip(Long tripId) {
    }

    public List<Trip> getTripsByOriginCity(String cityName) {
    }

    public List<Trip> getTripsByDestinationCity(String cityName) {
    }

    public List<Trip> getTripsByOriginAndDestinationCity(String originCityName, String destinationCityName) {
    }


}
