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
        if (tripId != null) {
            return tripRepository.findById(tripId);
        }
        return Optional.empty();
    }

    public void deleteTrip(Long tripId) {
        if (tripId != null) {
            tripRepository.deleteById(tripId);
        }
    }

    public List<Trip> getTripsByOriginCity(String cityName) {
        return tripRepository.findByOriginCityName(cityName);
    }

    public List<Trip> getTripsByDestinationCity(String cityName) {
        return tripRepository.findByDestinationCityName(cityName);
    }

    public List<Trip> getTripsByOriginAndDestinationCity(String originCityName, String destinationCityName) {
        return tripRepository.findByOriginCityNameAndDestinationCityName(originCityName, destinationCityName);
    }


}
