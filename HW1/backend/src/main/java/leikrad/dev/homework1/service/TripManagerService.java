package leikrad.dev.homework1.service;

import java.util.List;
import java.util.Optional;

import leikrad.dev.homework1.data.trip.*;

public class TripManagerService {
    
    private TripRepository tripRepository;

    public TripManagerService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Optional<Trip> getTripDetails(Long tripId) {
        return tripRepository.findByTripId(tripId);
    }

    public void deleteTrip(Long tripId) {
        Optional<Trip> trip = tripRepository.findByTripId(tripId);
        if (trip.isPresent()) {
            tripRepository.deleteByTripId(tripId);
        }
    }

    public List<Trip> getTripsByOriginCity(String cityName) {
        return tripRepository.findByOriginCityCityName(cityName);
    }

    public List<Trip> getTripsByDestinationCity(String cityName) {
        return tripRepository.findByDestinationCityCityName(cityName);
    }

    public List<Trip> getTripsByOriginAndDestinationCity(String originCityName, String destinationCityName) {
        return tripRepository.findByOriginCityCityNameAndDestinationCityCityName(originCityName, destinationCityName);
    }


}
